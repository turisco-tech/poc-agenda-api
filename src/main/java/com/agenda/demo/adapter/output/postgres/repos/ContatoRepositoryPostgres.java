package com.agenda.demo.adapter.output.postgres.repos;

import com.agenda.demo.adapter.output.postgres.entities.ContatoJpaEntity;
import com.agenda.demo.adapter.output.postgres.entities.OutboxEventJpaEntity;
import com.agenda.demo.core.domain.entities.Contato;
import com.agenda.demo.core.domain.repos.ContatoRepository;
import com.agenda.demo.core.domain.vos.Email;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class ContatoRepositoryPostgres implements ContatoRepository {

    private final SpringDataContatoRepository contatoRepo;
    private final SpringDataOutboxRepository outboxRepo;
    private final ObjectMapper objectMapper;

    public ContatoRepositoryPostgres(SpringDataContatoRepository contatoRepo,
                                     SpringDataOutboxRepository outboxRepo,
                                     ObjectMapper objectMapper) {
        this.contatoRepo = contatoRepo;
        this.outboxRepo = outboxRepo;
        this.objectMapper = objectMapper;
    }

    @Override
    @Transactional
    public Contato salvar(Contato contato) {
        Optional<ContatoJpaEntity> existingEntity = contatoRepo.findById(contato.getId());

        ContatoJpaEntity entity;
        String eventoTipo;
        boolean isUpdate = existingEntity.isPresent();
        String emailAntigo = "";

        if (isUpdate) {
            entity = existingEntity.get();
            emailAntigo = entity.getEmail(); // Captura a "foto" do passado antes de sobrescrever
            entity.setNome(contato.getNome());
            entity.setEmail(contato.getEmail().valor());
            eventoTipo = "ContactUpdated";
        } else {
            entity = new ContatoJpaEntity();
            entity.setId(contato.getId());
            entity.setNome(contato.getNome());
            entity.setEmail(contato.getEmail().valor());
            eventoTipo = "ContactCreated";
        }

        contatoRepo.save(entity);

        try {
            // Transforma o objeto Java num JSON mutável (ObjectNode)
            ObjectNode jsonNode = (ObjectNode) objectMapper.valueToTree(contato);

            // Injeta as flags de ação para o Dashboard rotear corretamente
            if (isUpdate) {
                jsonNode.put("acao", "ATUALIZAR");
                jsonNode.put("emailAntigo", emailAntigo);
            } else {
                jsonNode.put("acao", "CRIAR");
            }

            OutboxEventJpaEntity outboxEvent = new OutboxEventJpaEntity(
                    eventoTipo,
                    contato.getId().toString(),
                    jsonNode.toString()
            );
            outboxRepo.save(outboxEvent);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao serializar evento do Outbox", e);
        }

        return contato;
    }

    @Override
    public List<Contato> listarTodos() {
        return contatoRepo.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Contato> buscarPorId(UUID id) {
        return contatoRepo.findById(id).map(this::toDomain);
    }

    @Override
    @Transactional
    public void deletar(UUID id) {
        contatoRepo.findById(id).ifPresent(entity -> {
            contatoRepo.delete(entity);
            try {
                // 1. Agora enviamos o endereço eletrónico junto, e adicionamos uma ‘flag’ de "ação"
                String payloadJson = String.format(
                        "{\"id\":\"%s\", \"email\":\"%s\", \"acao\":\"DELETAR\"}",
                        id, entity.getEmail()
                );

                OutboxEventJpaEntity outboxEvent = new OutboxEventJpaEntity(
                        "ContactDeleted",
                        id.toString(),
                        payloadJson
                );
                outboxRepo.save(outboxEvent);
            } catch (Exception e) {
                throw new RuntimeException("Erro ao gerar evento de deleção no Outbox", e);
            }
        });
    }

    // --- Método Auxiliar de Mapeamento (JPA -> Domínio) ---
    private Contato toDomain(ContatoJpaEntity entity) {
        return new Contato(entity.getId(), entity.getNome(), new Email(entity.getEmail()));
    }
}