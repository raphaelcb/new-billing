#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE USER billing_customer_user;
    ALTER USER billing_customer_user with encrypted password 'billing_customer_pwd';
    CREATE DATABASE customerdb;
    GRANT ALL PRIVILEGES ON DATABASE customerdb TO billing_customer_user;
EOSQL