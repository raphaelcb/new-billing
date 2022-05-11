/* SET THE CURRENT USER AND DATABASE */
\c authdb billing_auth_user;



/* 
===========================================================
CREATE TABLES
===========================================================
*/

/* ====================== PERMISSION ====================== */
CREATE TABLE IF NOT EXISTS permission (
    id                      BIGSERIAL NOT NULL,
    key                     VARCHAR ( 150 ) NOT NULL UNIQUE,
    description             VARCHAR ( 255 ) NOT NULL UNIQUE,
    CONSTRAINT permission_pkey PRIMARY KEY (id)
);

-- Index
CREATE INDEX IF NOT EXISTS idx_permission_01 ON permission(id);
-- Comments
COMMENT ON TABLE permission IS 'Permissão';
COMMENT ON COLUMN permission.id IS 'Identificador interno da permissão';
COMMENT ON COLUMN permission.key IS 'Chave única da permissão';
COMMENT ON COLUMN permission.description IS 'Descrição da permissão';

/* ======================== PROFILE ======================= */
CREATE TABLE IF NOT EXISTS profile (
    id                      BIGSERIAL NOT NULL,
    name                    VARCHAR ( 255 ) NOT NULL UNIQUE,
    status                  INTEGER NOT NULL,
    visible                 BOOLEAN NOT NULL,
    CONSTRAINT profile_pkey PRIMARY KEY (id)
);

-- Index
CREATE INDEX IF NOT EXISTS idx_profile_01 ON profile(id);
-- Comments
COMMENT ON TABLE profile IS 'Perfil de acesso';
COMMENT ON COLUMN profile.id IS 'Identificador interno do perfil de acesso';
COMMENT ON COLUMN profile.name IS 'Nome do perfil de acesso';
COMMENT ON COLUMN profile.status IS 'Situação do perfil de acesso. 0 está INATIVO. 1 está ATIVO.';
COMMENT ON COLUMN profile.visible IS 'Indicativo se o perfil de acesso será visível no frontend';

/* ================== PROFILE x PERMISSION ================ */
CREATE TABLE IF NOT EXISTS profile_permission (
    id_profile              BIGSERIAL NOT NULL,
    id_permission           BIGSERIAL NOT NULL,
    FOREIGN KEY (id_profile) REFERENCES profile (id),
    FOREIGN KEY (id_permission) REFERENCES permission (id)
);

-- Index
CREATE INDEX IF NOT EXISTS idx_profile_permission_01 ON profile_permission(id_profile);
CREATE INDEX IF NOT EXISTS idx_profile_permission_02 ON profile_permission(id_permission);
-- Comments
COMMENT ON TABLE profile_permission IS 'Associação de perfil de acesso x permissão';
COMMENT ON COLUMN profile_permission.id_profile IS 'Identificador interno do perfil de acesso';
COMMENT ON COLUMN profile_permission.id_permission IS 'Identificador interno da permissão';

/* ==================== ACTIVE PASSWORD =================== */
CREATE TABLE IF NOT EXISTS active_password (
    id                      BIGSERIAL NOT NULL,
    password                VARCHAR ( 100 ) NOT NULL,
    CONSTRAINT active_password_pkey PRIMARY KEY (id)
);

-- Index
CREATE INDEX IF NOT EXISTS idx_active_password_01 ON active_password(id);
-- Comments
COMMENT ON TABLE active_password IS 'Senha ativa';
COMMENT ON COLUMN active_password.id IS 'Identificador interno da senha ativa';
COMMENT ON COLUMN active_password.password IS 'Senha criptografa';

/* ==================== ATTEMPTS LOGIN ==================== */
CREATE TABLE IF NOT EXISTS attempts_login (
    id                      BIGSERIAL NOT NULL,
    timestamp               TIMESTAMPTZ NOT NULL,
    login                   VARCHAR ( 100) NOT NULL,
    ip                      VARCHAR ( 20 ) NOT NULL,
    successfully_login      BOOLEAN NOT NULL,
    CONSTRAINT attempts_login_pkey PRIMARY KEY (id)
);

-- Index
CREATE INDEX IF NOT EXISTS idx_attempts_login_01 ON attempts_login(id);
-- Comments
COMMENT ON TABLE attempts_login IS 'Tentativas de autenticação';
COMMENT ON COLUMN attempts_login.id IS 'Identificador interno da tentativa de autenticação';
COMMENT ON COLUMN attempts_login.timestamp IS 'Timestamp da tentativa de autenticação';
COMMENT ON COLUMN attempts_login.login IS 'Login do usuário que tentou a autenticação';
COMMENT ON COLUMN attempts_login.ip IS 'IP do terminal do usuário que tentou a autenticação';
COMMENT ON COLUMN attempts_login.successfully_login IS 'Situação da tentativa de autenticação. False para tentativa INCORRETA. True para tentativa feita com SUCESSO.';

/* ========================= USER ========================= */
CREATE TABLE IF NOT EXISTS users (
    id                      BIGSERIAL NOT NULL,
    login                   VARCHAR ( 100 ) NOT NULL UNIQUE,
    name                    VARCHAR ( 255 ) NOT NULL UNIQUE,
    acronym                 VARCHAR ( 255 ) NOT NULL,
    occupation              VARCHAR ( 150 ),
    email                   VARCHAR ( 100 ) NOT NULL UNIQUE,
    attempts_login          INTEGER NOT NULL,
    first_access            BOOLEAN NOT NULL,
    status                  INTEGER NOT NULL,
    id_profile              BIGSERIAL NOT NULL,
    id_active_password      BIGSERIAL NOT NULL,
    CONSTRAINT users_pkey PRIMARY KEY (id),
    FOREIGN KEY (id_profile) REFERENCES profile (id),
    FOREIGN KEY (id_active_password) REFERENCES active_password (id)
);

-- Index
CREATE INDEX IF NOT EXISTS idx_users_01 ON users(id);
CREATE INDEX IF NOT EXISTS idx_users_02 ON users(login);
CREATE INDEX IF NOT EXISTS idx_users_03 ON users(name);
CREATE INDEX IF NOT EXISTS idx_users_04 ON users(email);
CREATE INDEX IF NOT EXISTS idx_users_05 ON users(status);
CREATE INDEX IF NOT EXISTS idx_users_06 ON users(id_profile);
-- Comments
COMMENT ON TABLE users IS 'Usuário';
COMMENT ON COLUMN users.id IS 'Identificador interno do usuário';
COMMENT ON COLUMN users.login IS 'Login do usuário';
COMMENT ON COLUMN users.name IS 'Nome do usuário';
COMMENT ON COLUMN users.acronym IS 'Acrônimo do usuário';
COMMENT ON COLUMN users.occupation IS 'Profissão do usuário';
COMMENT ON COLUMN users.email IS 'E-mail do usuário';
COMMENT ON COLUMN users.attempts_login IS 'Tentativas de autenticação do usuário';
COMMENT ON COLUMN users.first_access IS 'Indicativo se é ou não o primeiro acesso do usuário na aplicação';
COMMENT ON COLUMN users.status IS 'Situação do usuário. 0 está INATIVO. 1 está ATIVO. 2 está BLOQUEADO.';
COMMENT ON COLUMN users.id_profile IS 'Identificador interno do perfil de acesso';
COMMENT ON COLUMN users.id_active_password IS 'Identificador interno da senha ativa do usuário';

/* =================== INACTIVE PASSWORD ================== */
CREATE TABLE IF NOT EXISTS inactive_password (
    id                      BIGSERIAL NOT NULL,
    password                VARCHAR ( 100 ) NOT NULL,
    id_user                 BIGSERIAL NOT NULL,
    CONSTRAINT inactive_password_pkey PRIMARY KEY (id),
    FOREIGN KEY (id_user) REFERENCES users (id)
);

-- Index
CREATE INDEX IF NOT EXISTS idx_inactive_password_01 ON inactive_password(id);
CREATE INDEX IF NOT EXISTS idx_inactive_password_02 ON inactive_password(id_user);
-- Comments
COMMENT ON TABLE inactive_password IS 'Senha inativa';
COMMENT ON COLUMN inactive_password.id IS 'Identificador interno da senha inativa';
COMMENT ON COLUMN inactive_password.password IS 'Senha criptografa';
COMMENT ON COLUMN inactive_password.id_user IS 'Identificador interno do usuário associado a senha';



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

/* ===================== PROFILE AUDIT ==================== */
CREATE TABLE IF NOT EXISTS profile_aud (
    id                      BIGSERIAL NOT NULL,
    rev                     INTEGER NOT NULL,
    revtype                 SMALLINT,
    name                    VARCHAR ( 255 ),
    name_mod                BOOLEAN,
    status                  INTEGER,
    statusenum_mod          BOOLEAN,
    visible                 BOOLEAN,
    visible_mod             BOOLEAN,
    CONSTRAINT profile_aud_pkey PRIMARY KEY (id, rev)
);

-- Index
CREATE INDEX IF NOT EXISTS idx_profile_aud_01 ON profile_aud(id);
CREATE INDEX IF NOT EXISTS idx_profile_aud_02 ON profile_aud(rev);
-- Comments
COMMENT ON TABLE profile_aud IS 'Auditoria de perfil de acesso';
COMMENT ON COLUMN profile_aud.id IS 'Identificador interno da auditoria do perfil de acesso';
COMMENT ON COLUMN profile_aud.rev IS 'Identificador da revisão de auditoria';
COMMENT ON COLUMN profile_aud.revtype IS 'Tipo da revisão. 0 é ADIÇÃO. 1 é ALTERAÇÃO. 2 é EXCLUSÃO.';
COMMENT ON COLUMN profile_aud.name IS 'Nome do perfil de acesso';
COMMENT ON COLUMN profile_aud.name_mod IS 'Indicativo se o atributo do nome do perfil de acesso foi modificado ou não';
COMMENT ON COLUMN profile_aud.status IS 'Situação do perfil de acesso. 0 está INATIVO. 1 está ATIVO.';
COMMENT ON COLUMN profile_aud.statusenum_mod IS 'Indicativo se o atributo da situação do perfil de acesso foi modificado ou não';
COMMENT ON COLUMN profile_aud.visible IS 'Indicativo se o perfil de acesso será visível no frontend';
COMMENT ON COLUMN profile_aud.visible_mod IS 'Indicativo se o atributo do indicativo se o perfil de acesso será visível no frontend foi modificado ou não';

/* ====================== USERS AUDIT ===================== */
CREATE TABLE IF NOT EXISTS users_aud (
    id                      BIGSERIAL NOT NULL,
    rev                     INTEGER NOT NULL,
    revtype                 SMALLINT,
    acronym                 VARCHAR ( 255 ),
    acronym_mod             BOOLEAN,
    attempts_login          INTEGER,
    attemptslogin_mod       BOOLEAN,
    email                   VARCHAR ( 100 ),
    email_mod               BOOLEAN,
    first_access            BOOLEAN,
    firstaccess_mod         BOOLEAN,
    login                   VARCHAR ( 100 ),
    login_mod               BOOLEAN,
    name                    VARCHAR ( 255 ),
    name_mod                BOOLEAN,
    occupation              VARCHAR ( 150 ),
    occupation_mod          BOOLEAN,
    status                  INTEGER,
    statususerenum_mod      BOOLEAN,
    CONSTRAINT users_aud_pkey PRIMARY KEY (id, rev)
);

-- Index
CREATE INDEX IF NOT EXISTS idx_users_aud_01 ON users_aud(id);
CREATE INDEX IF NOT EXISTS idx_users_aud_02 ON users_aud(rev);
-- Comments
COMMENT ON TABLE users_aud IS 'Auditoria de usuário';
COMMENT ON COLUMN users_aud.id IS 'Identificador interno da auditoria do perfil de acesso';
COMMENT ON COLUMN users_aud.rev IS 'Identificador da revisão de auditoria';
COMMENT ON COLUMN users_aud.revtype IS 'Tipo da revisão. 0 é ADIÇÃO. 1 é ALTERAÇÃO. 2 é EXCLUSÃO.';
COMMENT ON COLUMN users_aud.acronym IS 'Acrônimo do usuário';
COMMENT ON COLUMN users_aud.acronym_mod IS 'Indicativo se o atributo do acrônimo do usuário foi modificado ou não';
COMMENT ON COLUMN users_aud.attempts_login IS 'Tentativas de autenticação do usuário';
COMMENT ON COLUMN users_aud.attemptslogin_mod IS 'Indicativo se o atributo de tentativas de autenticação do usuário foi modificado ou não';
COMMENT ON COLUMN users_aud.email IS 'E-mail do usuário';
COMMENT ON COLUMN users_aud.email_mod IS 'Indicativo se o atributo de e-mail do usuário foi modificado ou não';
COMMENT ON COLUMN users_aud.first_access IS 'Indicativo se é ou não o primeiro acesso do usuário na aplicação';
COMMENT ON COLUMN users_aud.firstaccess_mod IS 'Indicativo se o atributo indicativo se é ou não o primeiro acesso do usuário na aplicação foi modificado ou não';
COMMENT ON COLUMN users_aud.login IS 'Login do usuário';
COMMENT ON COLUMN users_aud.login_mod IS 'Indicativo se o atributo de login do usuário foi modificado ou não';
COMMENT ON COLUMN users_aud.name IS 'Nome do usuário';
COMMENT ON COLUMN users_aud.name_mod IS 'Indicativo se o atributo de nome do usuário foi modificado ou não';
COMMENT ON COLUMN users_aud.occupation IS 'Profissão do usuário';
COMMENT ON COLUMN users_aud.occupation_mod IS 'Indicativo se o atributo de profissão do usuário foi modificado ou não';
COMMENT ON COLUMN users_aud.status IS 'Situação do usuário. 0 está INATIVO. 1 está ATIVO. 2 está BLOQUEADO.';
COMMENT ON COLUMN users_aud.statususerenum_mod IS 'Indicativo se o atributo de situação do usuário foi modificado ou não';



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

-- Permission
INSERT INTO permission (id, key, description) VALUES 
    (nextval('permission_id_seq'), 'MONITORING', 'Monitoramento da aplicação'),
    (nextval('permission_id_seq'), 'ADD_PROFILE', 'Inclusão de perfil de acesso'),
    (nextval('permission_id_seq'), 'UPDATE_PROFILE', 'Alteração de perfil de acesso'),
    (nextval('permission_id_seq'), 'DELETE_PROFILE', 'Exclusão de perfil de acesso'),
    (nextval('permission_id_seq'), 'SEARCH_PROFILE', 'Busca/Detalhamento de perfil de acesso'),
    (nextval('permission_id_seq'), 'VIEW_AUDIT_PROFILE', 'Visualização de auditoria de perfil de acesso'),
    (nextval('permission_id_seq'), 'ADD_USER', 'Inclusão de usuário'),
    (nextval('permission_id_seq'), 'UPDATE_USER', 'Alteração de usuário'),
    (nextval('permission_id_seq'), 'DELETE_USER', 'Exclusão de usuário'),
    (nextval('permission_id_seq'), 'SEARCH_USER', 'Busca/Detalhamento de usuário'),
    (nextval('permission_id_seq'), 'VIEW_AUDIT_USER', 'Visualização de auditoria de usuário'),
    (nextval('permission_id_seq'), 'SEARCH_PERMISSION', 'Busca/Detalhamento de permissões'),
    (nextval('permission_id_seq'), 'SEARCH_CUSTOMER', 'Busca/Detalhamento de clientes'),
    (nextval('permission_id_seq'), 'SEARCH_COMPANY', 'Busca/Detalhamento de empresas')
;

-- Profile
INSERT INTO profile (id, name, status, visible) VALUES 
    (nextval('profile_id_seq'), 'Super usúario', 1, false),
    (nextval('profile_id_seq'), 'Monitoramento', 1, false)
;

-- Association Profile x Permission
INSERT INTO profile_permission (id_profile, id_permission) VALUES 
    (1, 2),
    (1, 3),
    (1, 4),
    (1, 5),
    (1, 6),
    (1, 7),
    (1, 8),
    (1, 9),
    (1, 10),
    (1, 11),
    (1, 12),
    (1, 13),
    (1, 14),
    (2, 1);

-- Active password
INSERT INTO active_password (id, password) VALUES 
-- SuperUser@2021
    (nextval('active_password_id_seq'), '{bcrypt}$2a$10$gNebJ4.xk1EHglnDoYtmAeBm.Ql1G7eltsQdMr4uniLud3rxRieWK'),
-- Monitoring@2021
    (nextval('active_password_id_seq'), '{bcrypt}$2a$10$bkxnnqjqn5RDk9VQU0JyrONOw3nPUCAvig/a7yJwuQ55CML7XK0v6')
;

-- Users
INSERT INTO users (id, acronym, attempts_login, email, first_access, login, name, occupation, status, id_active_password, id_profile) VALUES 
    (nextval('users_id_seq'), 'root', 0, 'root.billing@cpqd.com.br', false, 'root', 'Super User', 'Root', 1, 1, 1),
    (nextval('users_id_seq'), 'monitoramento', 0, 'monitoramento@cpqd.com.br', false, 'monitoramento', 'Usuário de Monitoramento', 'Monitoring', 1, 2, 2)
;