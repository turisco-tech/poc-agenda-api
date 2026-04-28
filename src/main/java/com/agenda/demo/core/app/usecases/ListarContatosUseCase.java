package com.agenda.demo.core.app.usecases;

import com.agenda.demo.core.app.dtos.ContatoDTO;
import com.agenda.demo.core.domain.repos.ContatoRepository;
import java.util.List;

public class ListarContatosUseCase {

    private final ContatoRepository repository;

    public ListarContatosUseCase(ContatoRepository repository) {
        this.repository = repository;
    }

    public List<ContatoDTO> executar() {
        return repository.listarTodos().stream()
                .map(c -> new ContatoDTO(c.getId(), c.getNome(), c.getEmail().valor()))
                .toList();
    }
}