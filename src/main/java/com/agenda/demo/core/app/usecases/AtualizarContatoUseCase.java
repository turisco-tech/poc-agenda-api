package com.agenda.demo.core.app.usecases;

import com.agenda.demo.core.app.dtos.AtualizarContatoRequest;
import com.agenda.demo.core.app.dtos.ContatoDTO;
import com.agenda.demo.core.domain.entities.Contato;
import com.agenda.demo.core.domain.repos.ContatoRepository;
import com.agenda.demo.core.domain.vos.Email;

import java.util.UUID;

public class AtualizarContatoUseCase {

    private final ContatoRepository repository;

    public AtualizarContatoUseCase(ContatoRepository repository) {
        this.repository = repository;
    }

    public ContatoDTO executar(UUID id, AtualizarContatoRequest request) {
        Contato contatoExistente = repository.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Contato não encontrado para o ID: " + id));

        // Aplica as regras do domínio recriando a entidade com os novos dados
        Email novoEmail = new Email(request.email());
        Contato contatoAtualizado = new Contato(contatoExistente.getId(), request.nome(), novoEmail);

        Contato contatoSalvo = repository.salvar(contatoAtualizado);

        return new ContatoDTO(
                contatoSalvo.getId(),
                contatoSalvo.getNome(),
                contatoSalvo.getEmail().valor()
        );
    }
}