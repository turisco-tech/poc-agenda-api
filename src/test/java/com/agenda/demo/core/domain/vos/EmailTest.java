package com.agenda.demo.core.domain.vos;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EmailTest {

    @Test
    void deveCriarEmailValido() {
        Email email = new Email("turisco.tech@gmail.com");
        assertEquals("turisco.tech@gmail.com", email.valor());
    }

    @Test
    void naoDeveCriarEmailSemArroba() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Email("emailinvalido.com");
        });
        assertEquals("Formato de e-mail inválido", exception.getMessage());
    }

    @Test
    void naoDeveCriarEmailVazio() {
        assertThrows(IllegalArgumentException.class, () -> new Email(""));
        assertThrows(IllegalArgumentException.class, () -> new Email(null));
    }
}