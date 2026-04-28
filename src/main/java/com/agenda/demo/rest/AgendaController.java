package com.agenda.demo.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AgendaController {

    @GetMapping("/api/status")
    public ResponseEntity<String> status() {
        return ResponseEntity.ok("API da Agenda Atualizada via CD Automático!");
    }

}