# #####################################################################
# Dockerfile for Billing Authentication and Authorization Database
# @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
# @since 1.0
# #####################################################################

# Image base
FROM postgres:14.2

# Variables definition
ENV POSTGRES=postgres
ENV POSTGRES_PASSWORD=postgres_pwd
ENV POSTGRES_DB=postgres

# Copy the script's for execution after image creation
COPY /Docker/auth/database/01-auth-init-user-db.sh /docker-entrypoint-initdb.d
COPY /Docker/auth/database/02-auth-database.sql /docker-entrypoint-initdb.d

# Set the locale to postgresql
RUN localedef -i pt_BR -c -f UTF-8 -A /usr/share/locale/locale.alias pt_BR.UTF-8
ENV LANG pt_BR.utf8
