package com.agenda.demo.adapter.input;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Sempre que o nosso Domínio lançar um IllegalArgumentException, o Spring cai aqui!
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.NOT_FOUND.value()); // Muda de 500 para 404!
        body.put("error", "Not Found");
        body.put("message", ex.getMessage()); // Mostra "Contato não encontrado..."

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }
}