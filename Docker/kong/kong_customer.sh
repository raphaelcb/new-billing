#!/bin/bash

# #####################################################################
# Definitions for KONG - Billing Customer - Services / Routes / Plugin
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

# SERVICES - LIST CUSTOMER

echo -e "\n\nDefine service CUSTOMER - FIND\n"
curl -i -X POST -H "Content-Type: application/json" \
	--url http://$KONG_IP:8001/services \
	--data '{"name":"customer_find","protocol":"http","host":"'${KONG_IP}'","port":'${KONG_PORT}',"path":"/customer/api/v0/protected/find","connect_timeout":'${SERVICE_CONNECT_TIMEOUT}',"write_timeout":'${SERVICE_WRITE_TIMEOUT}',"read_timeout":'${SERVICE_READ_TIMEOUT}'}'

sleep 3





# Define ROUTES

# ROUTES - LIST CUSTOMER

echo -e "\n\nDefine a route for service CUSTOMER - FIND\n"
curl -i -X POST -H "Content-Type: application/json" \
	--url http://$KONG_IP:8001/services/customer_find/routes \
	--data '{"name":"customerFindRoute","paths":["/customer_find"],"service":{"name":"customer_find"},"preserve_host":false,"strip_path":true}'

sleep 3





# JWT PLUGIN

# JWT - SERVICE CUSTOMER - FIND

echo -e "\n\nAdd JWT plugin to service CUSTOMER - FIND\n"
curl -X POST http://$KONG_IP:8001/services/customer_find/plugins \
	--data "name=jwt" \
	--data "config.secret_is_base64=false" \
	--data "config.run_on_preflight=true" \
	--data "config.claims_to_verify=exp"

sleep 3

# JWT - ROUTE CUSTOMER - FIND

echo -e "\n\nAdd JWT plugin to route CUSTOMER - FIND\n"
curl -X POST http://$KONG_IP:8001/routes/customerFindRoute/plugins \
	--data "name=jwt" \
	--data "config.secret_is_base64=false" \
	--data "config.run_on_preflight=true" \
        --data "config.claims_to_verify=exp"

sleep 2

echo -e "\n\nEnd of processing....\n\n"
