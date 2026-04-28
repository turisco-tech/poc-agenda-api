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
        Contato contato = repository.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Contato não encontrado"));

        // Atualizamos a entidade (O domínio garante as regras de validação)
        contato.atualizarEmail(new Email(request.email()));
        contato.atualizarNome(request.nome());

        repository.salvar(contato);

        return new ContatoDTO(contato.getId(), contato.getNome(), contato.getEmail().valor());
    }
}