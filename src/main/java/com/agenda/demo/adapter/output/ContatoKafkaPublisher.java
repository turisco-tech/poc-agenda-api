package com.agenda.demo.adapter.output;

import com.agenda.demo.core.domain.entities.Contato;
import com.agenda.demo.core.domain.ports.ContatoEventPublisher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Component
public class ContatoKafkaPublisher implements ContatoEventPublisher {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper; // O conversor JSON nativo do Spring
    private static final String TOPICO = "contact-created";

    public ContatoKafkaPublisher(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public void publicarContatoCriado(Contato contato) {
        Map<String, Object> evento = new HashMap<>();
        evento.put("id", contato.getId().toString());
        evento.put("nome", contato.getNome());
        evento.put("email", contato.getEmail().valor());
        evento.put("timestamp", System.currentTimeMillis());

        try {
            // Converte o nosso mapa para uma String no formato JSON
            String payload = objectMapper.writeValueAsString(evento);

            // Envia a chave (ID) e o valor (JSON) para o Kafka
            kafkaTemplate.send(TOPICO, contato.getId().toString(), payload);

            System.out.println("🚀 Evento disparado para o Kafka: Contato " + contato.getNome() + " criado!");
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao serializar evento para o Kafka", e);
        }
    }
}