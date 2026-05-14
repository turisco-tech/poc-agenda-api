package com.agenda.demo.core.app.usecases;

import com.agenda.demo.core.app.dtos.ContatoDTO;
import com.agenda.demo.core.domain.repos.ContatoRepository;

import java.util.List;
import java.util.stream.Collectors;

public class ListarContatosUseCase {

    private final ContatoRepository repository;

    public ListarContatosUseCase(ContatoRepository repository) {
        this.repository = repository;
    }

    public List<ContatoDTO> executar() {
        return repository.listarTodos().stream()
                .map(contato -> new ContatoDTO(
                        contato.getId(),
                        contato.getNome(),
                        contato.getEmail().valor()
                ))
                .collect(Collectors.toList());
    }
}