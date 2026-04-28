package com.agenda.demo.core.domain.entities;

import com.agenda.demo.core.domain.vos.Email;
import java.util.UUID;

public class Contato {

    private final UUID id;
    private String nome;
    private Email email;

    // Construtor para criar um NOVO contato (o ID é gerado aqui)
    public Contato(String nome, Email email) {
        validarNome(nome);
        this.id = UUID.randomUUID();
        this.nome = nome;
        this.email = email;
    }

    // Construtor para reconstituir um contato que já existe no Banco de Dados
    public Contato(UUID id, String nome, Email email) {
        validarNome(nome);
        this.id = id;
        this.nome = nome;
        this.email = email;
    }

    private void validarNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do contato é obrigatório");
        }
    }

    // Getters
    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Email getEmail() {
        return email;
    }

    // Regra de Negócio: Atualizar e-mail
    public void atualizarEmail(Email novoEmail) {
        if (novoEmail == null) {
            throw new IllegalArgumentException("O novo e-mail não pode ser nulo");
        }
        this.email = novoEmail;
    }

    // Regra de Negócio: Atualizar nome
    public void atualizarNome(String novoNome) {
        validarNome(novoNome);
        this.nome = novoNome;
    }
}