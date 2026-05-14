-- Criação da tabela de Contatos
CREATE TABLE tb_contatos
(
    id    UUID PRIMARY KEY,
    nome  VARCHAR(255),
    email VARCHAR(255)
);

-- Criação da tabela do Outbox Pattern
CREATE TABLE tb_outbox_events
(
    id             UUID PRIMARY KEY      DEFAULT gen_random_uuid(),
    aggregate_type VARCHAR(255) NOT NULL,
    aggregate_id   VARCHAR(255) NOT NULL,
    payload        TEXT         NOT NULL,
    status         VARCHAR(50)  NOT NULL DEFAULT 'PENDING',
    created_at     TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW()
);