package com.agenda.demo.adapter.output.postgres.repos;

import com.agenda.demo.adapter.output.postgres.entities.ContatoJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataContatoRepository extends JpaRepository<ContatoJpaEntity, UUID> {
}
