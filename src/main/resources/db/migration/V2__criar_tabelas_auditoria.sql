-- 1. Cria a sequência que o Envers usa por padrão para gerar os IDs de Revisão
CREATE SEQUENCE revinfo_seq INCREMENT BY 50;

-- 2. Cria a Tabela Central de Revisões (Guarda o ID e o Timestamp da transação)
CREATE TABLE revinfo
(
    rev      INTEGER NOT NULL,
    revtstmp BIGINT,
    PRIMARY KEY (rev)
);

-- 3. Cria a Tabela de Auditoria do Contato (O sufixo padrão é _AUD)
-- Ela deve ter as mesmas colunas da tabela original + os campos de controle do Envers
CREATE TABLE tb_contatos_aud
(
    id      UUID    NOT NULL,
    rev     INTEGER NOT NULL,
    revtype SMALLINT, -- 0 para INSERT, 1 para UPDATE, 2 para DELETE
    nome    VARCHAR(255),
    email   VARCHAR(255),
    PRIMARY KEY (id, rev),
    CONSTRAINT fk_tb_contatos_aud_revinfo FOREIGN KEY (rev) REFERENCES revinfo (rev)
);