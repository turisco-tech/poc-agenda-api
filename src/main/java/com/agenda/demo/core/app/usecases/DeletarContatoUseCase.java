package com.agenda.demo.core.app.usecases;

import com.agenda.demo.core.domain.repos.ContatoRepository;

import java.util.UUID;

public class DeletarContatoUseCase {

    private final ContatoRepository repository;

    public DeletarContatoUseCase(ContatoRepository repository) {
        this.repository = repository;
    }

    public void executar(UUID id) {
        repository.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Contato não encontrado para o ID: " + id));

        repository.deletar(id);
    }
}