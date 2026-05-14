package com.agenda.demo.adapter.output.postgres.repos;

import com.agenda.demo.adapter.output.postgres.entities.OutboxEventJpaEntity;
import com.agenda.demo.adapter.output.postgres.entities.OutboxStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SpringDataOutboxRepository extends JpaRepository<OutboxEventJpaEntity, UUID> {
    List<OutboxEventJpaEntity> findByStatusOrderByCreatedAtAsc(OutboxStatus outboxStatus);
}
