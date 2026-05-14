package com.agenda.demo.adapter.output.postgres.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_outbox_events")
public class OutboxEventJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.UUID) // <-- A Correção da Tipagem
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;// Ex: "Contact" - Ajuda a saber de qual domínio é o evento
    private String aggregateType;

    // O ID do contato que foi criado/alterado
    private String aggregateId;

    // O payload que será efetivamente enviado ao Kafka (JSON)
    // Avisa o Hibernate que essa ‘String’ é um texto longo no banco
    @Column(columnDefinition = "TEXT")
    private String payload;

    @Enumerated(EnumType.STRING)
    private OutboxStatus status;

    private LocalDateTime createdAt;// Construtor vazio para o Hibernate

    public OutboxEventJpaEntity() {
    }

    // Construtor prático
    public OutboxEventJpaEntity(String aggregateType, String aggregateId, String payload) {
        this.aggregateType = aggregateType;
        this.aggregateId = aggregateId;
        this.payload = payload;
        this.status = OutboxStatus.PENDING; // Nasce sempre como pendente!
        this.createdAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getAggregateType() {
        return aggregateType;
    }

    public void setAggregateType(String aggregateType) {
        this.aggregateType = aggregateType;
    }

    public String getAggregateId() {
        return aggregateId;
    }

    public void setAggregateId(String aggregateId) {
        this.aggregateId = aggregateId;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public OutboxStatus getStatus() {
        return status;
    }

    public void setStatus(OutboxStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
