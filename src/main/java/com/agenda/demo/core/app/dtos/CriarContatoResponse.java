package com.agenda.demo.core.app.dtos;

import java.util.UUID;

public record CriarContatoResponse(UUID id, String nome, String email) {
}