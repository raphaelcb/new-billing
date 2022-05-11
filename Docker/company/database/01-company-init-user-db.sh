#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE USER billing_company_user;
    ALTER USER billing_company_user with encrypted password 'billing_company_pwd';
    CREATE DATABASE companydb;
    GRANT ALL PRIVILEGES ON DATABASE companydb TO billing_company_user;
EOSQL