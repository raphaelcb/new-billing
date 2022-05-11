/* SET THE CURRENT USER AND DATABASE */
\c companydb billing_company_user;



/* 
===========================================================
CREATE TABLES
===========================================================
*/

/* ======================== COMPANY ======================= */
CREATE TABLE IF NOT EXISTS company (
    id                      BIGSERIAL NOT NULL,
    name                    VARCHAR ( 250 ) NOT NULL UNIQUE,
    cnpj                    VARCHAR ( 20 ) NOT NULL UNIQUE,
    CONSTRAINT company_pkey PRIMARY KEY (id)
);

-- Index
CREATE INDEX IF NOT EXISTS idx_company_01 ON company(id);
-- Comments
COMMENT ON TABLE company IS 'Empresa';
COMMENT ON COLUMN company.id IS 'Identificador interno da empresa';
COMMENT ON COLUMN company.name IS 'Nome da empresa';
COMMENT ON COLUMN company.cnpj IS 'CNPJ da empresa';



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

-- Company
INSERT INTO company (id, name, cnpj) VALUES 
    (nextval('company_id_seq'), 'CPqD', '02.641.663/0001-10')
;