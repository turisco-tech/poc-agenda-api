package com.agenda.demo.core.domain.repos;

import com.agenda.demo.core.domain.entities.Contato;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ContatoRepository {
    Contato salvar(Contato contato);

    List<Contato> listarTodos();

    Optional<Contato> buscarPorId(UUID id);

    void deletar(UUID id);
}