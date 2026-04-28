package com.agenda.demo.core.domain.vos;

public record Email(String valor) {

    public Email {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("O e-mail não pode ser nulo ou vazio");
        }
        if (!valor.contains("@")) {
            throw new IllegalArgumentException("Formato de e-mail inválido");
        }
    }
}