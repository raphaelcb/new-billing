/* SET THE CURRENT USER AND DATABASE */
\c customerdb billing_customer_user;



/* 
===========================================================
CREATE TABLES
===========================================================
*/

/* ======================= CUSTOMER ======================= */
CREATE TABLE IF NOT EXISTS customer (
    id                      BIGSERIAL NOT NULL,
    name                    VARCHAR ( 250 ) NOT NULL UNIQUE,
    phone                   VARCHAR ( 15 ) NOT NULL UNIQUE,
    CONSTRAINT customer_pkey PRIMARY KEY (id)
);

-- Index
CREATE INDEX IF NOT EXISTS idx_customer_01 ON customer(id);
-- Comments
COMMENT ON TABLE customer IS 'Cliente';
COMMENT ON COLUMN customer.id IS 'Identificador interno do cliente';
COMMENT ON COLUMN customer.name IS 'Nome do cliente';
COMMENT ON COLUMN customer.phone IS 'Telefone celular do cliente';



/* 
===========================================================
CREATE TABLES - AUDIT
===========================================================
*/

/* ===================== AUDIT REVISION =================== */
CREATE TABLE IF NOT EXISTS audit_revision (
    id                      BIGSERIAL NOT NULL,
    timestamp               BIGINT NOT NULL,
    ip_address              VARCHAR ( 255 ),
    username                VARCHAR ( 255 ) NOT NULL,
    CONSTRAINT audit_revision_pkey PRIMARY KEY (id)
);

-- Index
CREATE INDEX IF NOT EXISTS idx_audit_revision_01 ON audit_revision(id);
-- Comments
COMMENT ON TABLE audit_revision IS 'Auditoria';
COMMENT ON COLUMN audit_revision.id IS 'Identificador interno da auditoria';
COMMENT ON COLUMN audit_revision.timestamp IS 'Timestamp da ocorrência';
COMMENT ON COLUMN audit_revision.ip_address IS 'IP do terminal do usuário que realizou a operação';
COMMENT ON COLUMN audit_revision.username IS 'Login do usuário quem realizou a operação';



/* 
===========================================================
SEQUENCES
===========================================================
*/
CREATE SEQUENCE hibernate_sequence START 1;



/* 
===========================================================
LOAD TABLES
===========================================================
*/

-- Customer
INSERT INTO customer (id, name, phone) VALUES 
    (nextval('customer_id_seq'), 'Carla Midori Nakashima', '(19) 83333-3333'),
    (nextval('customer_id_seq'), 'André Luís Francisco Alarcon', '(19) 97777-7777'),
    (nextval('customer_id_seq'), 'Raphael de Carvalho Barbosa', '(19) 99999-9999'),
    (nextval('customer_id_seq'), 'Alexandre Rodrigues Dos Santos', '(19) 96666-6666')
;