package com.agenda.demo.adapter.output.postgres.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.envers.Audited;
import org.hibernate.type.SqlTypes;
import org.springframework.data.domain.Persistable;

import java.util.UUID;

@Entity
@Table(name = "tb_contatos")
@Audited // <-- Apenas essa palavra liga a auditoria completa para esta entidade!
// 1. Implementamos a interface Persistable dizendo que o nosso ID é UUID
public class ContatoJpaEntity implements Persistable<UUID> {

    @Id
    // 2. REMOVA o @GeneratedValue daqui! O nosso Domínio é quem gera o ID.
    @JdbcTypeCode(SqlTypes.UUID) // <-- A Correção da Tipagem
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    private String nome;
    private String email;

    // 3. Criamos uma flag invisível para o banco que avisa se é um registro novo
    @Transient
    private boolean isNewEntity = true;

    public ContatoJpaEntity() {
    }

    // Getters e Setters normais
    @Override
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // --- A MÁGICA DO SPRING DATA ACONTECE AQUI ---

    @Override
    public boolean isNew() {
        return isNewEntity; // O Spring pergunta aqui se deve fazer INSERT ou UPDATE
    }

    // O Hibernate roda esse método automaticamente logo ANTES de salvar (PrePersist)
    // ou logo DEPOIS de carregar uma busca do banco (PostLoad)
    @PrePersist
    @PostLoad
    public void markNotNew() {
        this.isNewEntity = false;
    }
}