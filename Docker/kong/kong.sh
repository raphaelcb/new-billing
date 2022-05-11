#!/bin/bash

# #####################################################################
# Definitions for KONG - Billing Project
# @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
# @since 1.0
# #####################################################################

#
# DEFINITIONS
#
KONG_IP=192.168.0.11



#
# EXECUTION
#
# Create Docker Network
echo -e "\nStep 1 - Create Docker Network\n"
docker network create kong-net

sleep 2



# Start a database
echo -e "\n\nStep 2 - Start a database\n"
 docker run -d --name kong-database \
  --network=kong-net \
  -p 5432:5432 \
  -e "POSTGRES_USER=kong" \
  -e "POSTGRES_DB=kong" \
  -e "POSTGRES_PASSWORD=kongpass" \
  postgres:9.6

sleep 5



# Prepare the Kong database
echo -e "\n\nStep 3 - Prepare the Kong database"
docker run --rm --network=kong-net \
  -e "KONG_DATABASE=postgres" \
  -e "KONG_PG_HOST=kong-database" \
  -e "KONG_PG_PASSWORD=kongpass" \
  -e "KONG_PASSWORD=test" \
 kong/kong-gateway:2.8.1.0-alpine kong migrations bootstrap

sleep 5



# Start the gateway with Kong Manager
echo -e "\n\nStep 4 - Start the gateway with Kong Manager"
docker run -d --name kong-gateway \
  --network=kong-net \
  -e "KONG_DATABASE=postgres" \
  -e "KONG_PG_HOST=kong-database" \
  -e "KONG_PG_USER=kong" \
  -e "KONG_PG_PASSWORD=kongpass" \
  -e "KONG_PROXY_ACCESS_LOG=/dev/stdout" \
  -e "KONG_ADMIN_ACCESS_LOG=/dev/stdout" \
  -e "KONG_PROXY_ERROR_LOG=/dev/stderr" \
  -e "KONG_ADMIN_ERROR_LOG=/dev/stderr" \
  -e "KONG_ADMIN_LISTEN=0.0.0.0:8001" \
  -e "KONG_ADMIN_GUI_URL=http://$KONG_IP:8002" \
  -e KONG_LICENSE_DATA \
  -p 8000:8000 \
  -p 8443:8443 \
  -p 8001:8001 \
  -p 8444:8444 \
  -p 8002:8002 \
  -p 8445:8445 \
  -p 8003:8003 \
  -p 8004:8004 \
  kong/kong-gateway:2.8.1.0-alpine

sleep 5



# Create a consumer
echo -e "\n\nStep 5 - Create SEM PARAR consumer\n"
curl -d "username=stp" http://$KONG_IP:8001/consumers/

sleep 3



# Create JWT credential to SEM PARAR consumer
echo -e "\n\nStep 6 - Create JWT credential to SEM PARAR consumer\n"
curl -X POST http://$KONG_IP:8001/consumers/stp/jwt -H "Content-Type: application/x-www-form-urlencoded"

sleep 2

echo -e "\n\nEnd of processing....\n\n"
