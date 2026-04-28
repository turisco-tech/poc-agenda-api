package com.agenda.demo.core.domain.repos;

import com.agenda.demo.core.domain.entities.Contato;
import java.util.List;

public interface ContatoRepository {
    Contato salvar(Contato contato);

    List<Contato> listarTodos();
}