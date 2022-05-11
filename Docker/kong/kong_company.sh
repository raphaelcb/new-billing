#!/bin/bash

# #####################################################################
# Definitions for KONG - Billing Company - Services / Routes / Plugin
# @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
# @since 1.0
# #####################################################################

#
# DEFINITIONS
#
KONG_IP=192.168.0.11
KONG_PORT=8081
SERVICE_CONNECT_TIMEOUT=60000
SERVICE_WRITE_TIMEOUT=60000
SERVICE_READ_TIMEOUT=60000



# Define SERVICES

# SERVICES - LIST COMPANY

echo -e "\n\nDefine service COMPANY - FIND\n"
curl -i -X POST -H "Content-Type: application/json" \
	--url http://$KONG_IP:8001/services \
	--data '{"name":"company_find","protocol":"http","host":"'${KONG_IP}'","port":'${KONG_PORT}',"path":"/company/api/v0/protected/find","connect_timeout":'${SERVICE_CONNECT_TIMEOUT}',"write_timeout":'${SERVICE_WRITE_TIMEOUT}',"read_timeout":'${SERVICE_READ_TIMEOUT}'}'

sleep 3





# Define ROUTES

# ROUTES - LIST COMPANY

echo -e "\n\nDefine a route for service COMPANY - FIND\n"
curl -i -X POST -H "Content-Type: application/json" \
	--url http://$KONG_IP:8001/services/company_find/routes \
	--data '{"name":"companyFindRoute","paths":["/company_find"],"service":{"name":"company_find"},"preserve_host":false,"strip_path":true}'

sleep 3





# JWT PLUGIN

# JWT - SERVICE COMPANY - FIND

echo -e "\n\nAdd JWT plugin to service COMPANY - FIND\n"
curl -X POST http://$KONG_IP:8001/services/company_find/plugins \
	--data "name=jwt" \
	--data "config.secret_is_base64=false" \
	--data "config.run_on_preflight=true" \
	--data "config.claims_to_verify=exp"

sleep 3

# JWT - ROUTE COMPANY - FIND

echo -e "\n\nAdd JWT plugin to route COMPANY - FIND\n"
curl -X POST http://$KONG_IP:8001/routes/companyFindRoute/plugins \
	--data "name=jwt" \
	--data "config.secret_is_base64=false" \
	--data "config.run_on_preflight=true" \
        --data "config.claims_to_verify=exp"

sleep 2

echo -e "\n\nEnd of processing....\n\n"
