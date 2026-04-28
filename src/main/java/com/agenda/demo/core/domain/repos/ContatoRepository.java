package com.agenda.demo.core.domain.repos;

import com.agenda.demo.core.domain.entities.Contato;

public interface ContatoRepository {
    Contato salvar(Contato contato);
}