package com.agenda.demo.adapter.output;

import com.agenda.demo.core.domain.entities.Contato;
import com.agenda.demo.core.domain.repos.ContatoRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class ContatoRepositoryInMemory implements ContatoRepository {

    // Nosso banco de dados em memória
    private final Map<UUID, Contato> db = new HashMap<>();

    @Override
    public Contato salvar(Contato contato) {
        db.put(contato.getId(), contato);
        System.out.println("Contato salvo no banco em memória! ID: " + contato.getId());
        return contato;
    }

    @Override
    public List<Contato> listarTodos() {
        return new ArrayList<>(db.values());
    }
}