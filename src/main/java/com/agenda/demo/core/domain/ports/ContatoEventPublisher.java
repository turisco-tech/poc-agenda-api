package com.agenda.demo.core.domain.ports;

import com.agenda.demo.core.domain.entities.Contato;

public interface ContatoEventPublisher {

    // O Domínio apenas diz: "Alguém precisa publicar isso".
    // Quem vai implementar e como (Kafka, RabbitMQ, etc) não é problema dele!
    void publicarContatoCriado(Contato contato);

}