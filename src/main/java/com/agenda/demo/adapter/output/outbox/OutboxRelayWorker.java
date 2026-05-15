package com.agenda.demo.adapter.output.outbox;

import com.agenda.demo.adapter.output.postgres.entities.OutboxEventJpaEntity;
import com.agenda.demo.adapter.output.postgres.entities.OutboxStatus;
import com.agenda.demo.adapter.output.postgres.repos.SpringDataOutboxRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OutboxRelayWorker {

    private static final Logger log = LoggerFactory.getLogger(OutboxRelayWorker.class);

    private final SpringDataOutboxRepository outboxRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${app.kafka.topic.contact-events}")
    private String topicoContatos;

    public OutboxRelayWorker(SpringDataOutboxRepository outboxRepository,
                             KafkaTemplate<String, String> kafkaTemplate) {
        this.outboxRepository = outboxRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    // Roda a cada 5000 milissegundos (5 segundos)
    @Scheduled(fixedDelay = 5000)
    public void processarEventosPendentes() {
        List<OutboxEventJpaEntity> pendingEvents = outboxRepository.findByStatusOrderByCreatedAtAsc(OutboxStatus.PENDING);

        if (pendingEvents.isEmpty()) {
            return; // O cofre está vazio, volta a dormir.
        }

        log.info("Encontrados {} eventos pendentes no Outbox. Iniciando envio...", pendingEvents.size());

        for (OutboxEventJpaEntity event : pendingEvents) {
            try {
                // Tenta enviar para o tópico do Kafka.
                // O ".get()" no final transforma a chamada assíncrona em síncrona.
                // O Worker FICA TRAVADO AQUI esperando o Kafka confirmar que recebeu.
                kafkaTemplate.send(topicoContatos, event.getAggregateId(), event.getPayload()).get();

                // Se o código chegou nesta linha, o Kafka salvou a mensagem com sucesso!
                event.setStatus(OutboxStatus.PROCESSED);
                outboxRepository.save(event);

                log.info("Evento {} ({}) enviado e marcado como PROCESSED!", event.getId(), event.getAggregateType());

            } catch (Exception e) {
                // Se o Kafka estiver fora do ar, o .get() vai explodir um erro e cair aqui.
                log.error("Kafka indisponível! O evento {} continuará PENDING. Causa: {}", event.getId(), e.getMessage());

                // Paramos o loop. Se o Kafka caiu, não adianta tentar os próximos agora.
                break;
            }
        }
    }
}