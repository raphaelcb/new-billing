#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE USER billing_auth_user;
    ALTER USER billing_auth_user with encrypted password 'billing_auth_pwd';
    CREATE DATABASE authdb;
    GRANT ALL PRIVILEGES ON DATABASE authdb TO billing_auth_user;
EOSQL