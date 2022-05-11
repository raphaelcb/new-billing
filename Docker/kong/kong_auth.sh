#!/bin/bash

# #####################################################################
# Definitions for KONG - Billing Auth - Services / Routes / Plugin
# @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
# @since 1.0
# #####################################################################

#
# DEFINITIONS
#
KONG_IP=192.168.0.11
KONG_PORT=8080
SERVICE_CONNECT_TIMEOUT=60000
SERVICE_WRITE_TIMEOUT=60000
SERVICE_READ_TIMEOUT=60000



# Define SERVICES

# SERVICES - AUTHENTICATION

echo -e "\n\nDefine service AUTH\n"
curl -i -X POST -H "Content-Type: application/json" \
	--url http://$KONG_IP:8001/services \
	--data '{"name":"auth","protocol":"http","host":"'${KONG_IP}'","port":'${KONG_PORT}',"path":"/auth/api/v0/public/auth","connect_timeout":'${SERVICE_CONNECT_TIMEOUT}',"write_timeout":'${SERVICE_WRITE_TIMEOUT}',"read_timeout":'${SERVICE_READ_TIMEOUT}'}'

sleep 3

# SERVICES - PERMISSION

echo -e "\n\nDefine service PERMISSION - FIND\n"
curl -i -X POST -H "Content-Type: application/json" \
	--url http://$KONG_IP:8001/services \
	--data '{"name":"permission_find","protocol":"http","host":"'${KONG_IP}'","port":'${KONG_PORT}',"path":"/auth/api/v0/protected/permissions/find","connect_timeout":'${SERVICE_CONNECT_TIMEOUT}',"write_timeout":'${SERVICE_WRITE_TIMEOUT}',"read_timeout":'${SERVICE_READ_TIMEOUT}'}'

sleep 3

# SERVICES - PROFILES

echo -e "\n\nDefine service PROFILE - ADD\n"
curl -i -X POST -H "Content-Type: application/json" \
        --url http://$KONG_IP:8001/services \
	--data '{"name":"profile_add","protocol":"http","host":"'${KONG_IP}'","port":'${KONG_PORT}',"path":"/auth/api/v0/protected/profiles/add","connect_timeout":'${SERVICE_CONNECT_TIMEOUT}',"write_timeout":'${SERVICE_WRITE_TIMEOUT}',"read_timeout":'${SERVICE_READ_TIMEOUT}'}'

sleep 3

echo -e "\n\nDefine service PROFILE - UPDATE\n"
curl -i -X POST -H "Content-Type: application/json" \
        --url http://$KONG_IP:8001/services \
	--data '{"name":"profile_update","protocol":"http","host":"'${KONG_IP}'","port":'${KONG_PORT}',"path":"/auth/api/v0/protected/profiles/update","connect_timeout":'${SERVICE_CONNECT_TIMEOUT}',"write_timeout":'${SERVICE_WRITE_TIMEOUT}',"read_timeout":'${SERVICE_READ_TIMEOUT}'}'

sleep 3

echo -e "\n\nDefine service PROFILE - DELETE\n"
curl -i -X POST -H "Content-Type: application/json" \
        --url http://$KONG_IP:8001/services \
	--data '{"name":"profile_delete","protocol":"http","host":"'${KONG_IP}'","port":'${KONG_PORT}',"path":"/auth/api/v0/protected/profiles/delete","connect_timeout":'${SERVICE_CONNECT_TIMEOUT}',"write_timeout":'${SERVICE_WRITE_TIMEOUT}',"read_timeout":'${SERVICE_READ_TIMEOUT}'}'

sleep 3

echo -e "\n\nDefine service PROFILE - FIND\n"
curl -i -X POST -H "Content-Type: application/json" \
        --url http://$KONG_IP:8001/services \
	--data '{"name":"profile_find","protocol":"http","host":"'${KONG_IP}'","port":'${KONG_PORT}',"path":"/auth/api/v0/protected/profiles/find","connect_timeout":'${SERVICE_CONNECT_TIMEOUT}',"write_timeout":'${SERVICE_WRITE_TIMEOUT}',"read_timeout":'${SERVICE_READ_TIMEOUT}'}'

sleep 3

echo -e "\n\nDefine service PROFILE - FIND BY ID\n"
curl -i -X POST -H "Content-Type: application/json" \
        --url http://$KONG_IP:8001/services \
	--data '{"name":"profile_find_id","protocol":"http","host":"'${KONG_IP}'","port":'${KONG_PORT}',"path":"/auth/api/v0/protected/profiles/find/id","connect_timeout":'${SERVICE_CONNECT_TIMEOUT}',"write_timeout":'${SERVICE_WRITE_TIMEOUT}',"read_timeout":'${SERVICE_READ_TIMEOUT}'}'

sleep 3

echo -e "\n\nDefine service PROFILE - AUDIT - FULL\n"
curl -i -X POST -H "Content-Type: application/json" \
	--url http://$KONG_IP:8001/services \
	--data '{"name":"profile_audit_full","protocol":"http","host":"'${KONG_IP}'","port":'${KONG_PORT}',"path":"/auth/api/v0/protected/profiles/audit/full","connect_timeout":'${SERVICE_CONNECT_TIMEOUT}',"write_timeout":'${SERVICE_WRITE_TIMEOUT}',"read_timeout":'${SERVICE_READ_TIMEOUT}'}'

sleep 3

echo -e "\n\nDefine service PROFILE - AUDIT - ITEM\n"
curl -i -X POST -H "Content-Type: application/json" \
	--url http://$KONG_IP:8001/services \
	--data '{"name":"profile_audit_item","protocol":"http","host":"'${KONG_IP}'","port":'${KONG_PORT}',"path":"/auth/api/v0/protected/profiles/audit/item","connect_timeout":'${SERVICE_CONNECT_TIMEOUT}',"write_timeout":'${SERVICE_WRITE_TIMEOUT}',"read_timeout":'${SERVICE_READ_TIMEOUT}'}'

sleep 3

echo -e "\n\nDefine service PROFILE - AUDIT - DETAIL ITEM BY REVISION\n"
curl -i -X POST -H "Content-Type: application/json" \
	--url http://$KONG_IP:8001/services \
	--data '{"name":"profile_audit_item_detail","protocol":"http","host":"'${KONG_IP}'","port":'${KONG_PORT}',"path":"/auth/api/v0/protected/profiles/audit/item/detail","connect_timeout":'${SERVICE_CONNECT_TIMEOUT}',"write_timeout":'${SERVICE_WRITE_TIMEOUT}',"read_timeout":'${SERVICE_READ_TIMEOUT}'}'

sleep 3

# SERVICES - USER

echo -e "\n\nDefine service USER - ADD\n"
curl -i -X POST -H "Content-Type: application/json" \
        --url http://$KONG_IP:8001/services \
	--data '{"name":"user_add","protocol":"http","host":"'${KONG_IP}'","port":'${KONG_PORT}',"path":"/auth/api/v0/protected/users/add","connect_timeout":'${SERVICE_CONNECT_TIMEOUT}',"write_timeout":'${SERVICE_WRITE_TIMEOUT}',"read_timeout":'${SERVICE_READ_TIMEOUT}'}'

sleep 3

echo -e "\n\nDefine service USER - UPDATE\n"
curl -i -X POST -H "Content-Type: application/json" \
        --url http://$KONG_IP:8001/services \
	--data '{"name":"user_update","protocol":"http","host":"'${KONG_IP}'","port":'${KONG_PORT}',"path":"/auth/api/v0/protected/users/update","connect_timeout":'${SERVICE_CONNECT_TIMEOUT}',"write_timeout":'${SERVICE_WRITE_TIMEOUT}',"read_timeout":'${SERVICE_READ_TIMEOUT}'}'

sleep 3

echo -e "\n\nDefine service USER - DELETE\n"
curl -i -X POST -H "Content-Type: application/json" \
        --url http://$KONG_IP:8001/services \
	--data '{"name":"user_delete","protocol":"http","host":"'${KONG_IP}'","port":'${KONG_PORT}',"path":"/auth/api/v0/protected/users/delete","connect_timeout":'${SERVICE_CONNECT_TIMEOUT}',"write_timeout":'${SERVICE_WRITE_TIMEOUT}',"read_timeout":'${SERVICE_READ_TIMEOUT}'}'

sleep 3

echo -e "\n\nDefine service USER - FIND\n"
curl -i -X POST -H "Content-Type: application/json" \
        --url http://$KONG_IP:8001/services \
	--data '{"name":"user_find","protocol":"http","host":"'${KONG_IP}'","port":'${KONG_PORT}',"path":"/auth/api/v0/protected/users/find","connect_timeout":'${SERVICE_CONNECT_TIMEOUT}',"write_timeout":'${SERVICE_WRITE_TIMEOUT}',"read_timeout":'${SERVICE_READ_TIMEOUT}'}'

sleep 3

echo -e "\n\nDefine service USER - FIND BY ID\n"
curl -i -X POST -H "Content-Type: application/json" \
        --url http://$KONG_IP:8001/services \
	--data '{"name":"user_find_id","protocol":"http","host":"'${KONG_IP}'","port":'${KONG_PORT}',"path":"/auth/api/v0/protected/users/find/id","connect_timeout":'${SERVICE_CONNECT_TIMEOUT}',"write_timeout":'${SERVICE_WRITE_TIMEOUT}',"read_timeout":'${SERVICE_READ_TIMEOUT}'}'

sleep 3

echo -e "\n\nDefine service USER - AUDIT - FULL\n"
curl -i -X POST -H "Content-Type: application/json" \
	--url http://$KONG_IP:8001/services \
	--data '{"name":"user_audit_full","protocol":"http","host":"'${KONG_IP}'","port":'${KONG_PORT}',"path":"/auth/api/v0/protected/users/audit/full","connect_timeout":'${SERVICE_CONNECT_TIMEOUT}',"write_timeout":'${SERVICE_WRITE_TIMEOUT}',"read_timeout":'${SERVICE_READ_TIMEOUT}'}'

sleep 3

echo -e "\n\nDefine service USER - AUDIT - ITEM\n"
curl -i -X POST -H "Content-Type: application/json" \
	--url http://$KONG_IP:8001/services \
	--data '{"name":"user_audit_item","protocol":"http","host":"'${KONG_IP}'","port":'${KONG_PORT}',"path":"/auth/api/v0/protected/users/audit/item","connect_timeout":'${SERVICE_CONNECT_TIMEOUT}',"write_timeout":'${SERVICE_WRITE_TIMEOUT}',"read_timeout":'${SERVICE_READ_TIMEOUT}'}'

sleep 3

echo -e "\n\nDefine service USER - AUDIT - DETAIL ITEM BY REVISION\n"
curl -i -X POST -H "Content-Type: application/json" \
	--url http://$KONG_IP:8001/services \
	--data '{"name":"user_audit_item_detail","protocol":"http","host":"'${KONG_IP}'","port":'${KONG_PORT}',"path":"/auth/api/v0/protected/users/audit/item/detail","connect_timeout":'${SERVICE_CONNECT_TIMEOUT}',"write_timeout":'${SERVICE_WRITE_TIMEOUT}',"read_timeout":'${SERVICE_READ_TIMEOUT}'}'

sleep 3

# SERVICES - CHANGE PASSWORD

echo -e "\n\nDefine service CHANGE PASSWORD - UPDATE\n"
curl -i -X POST -H "Content-Type: application/json" \
        --url http://$KONG_IP:8001/services \
	--data '{"name":"change_password","protocol":"http","host":"'${KONG_IP}'","port":'${KONG_PORT}',"path":"/auth/api/v0/public/change_password/update","connect_timeout":'${SERVICE_CONNECT_TIMEOUT}',"write_timeout":'${SERVICE_WRITE_TIMEOUT}',"read_timeout":'${SERVICE_READ_TIMEOUT}'}'

sleep 3





# Define ROUTES

# ROUTES - AUTHENTICATION

echo -e "\n\nDefine a route for service AUTH\n"
curl -i -X POST -H "Content-Type: application/json" \
        --url http://$KONG_IP:8001/services/auth/routes \
        --data '{"name":"authRoute","paths":["/auth"],"service":{"name":"auth"},"preserve_host":false,"strip_path":true}'

sleep 3

# ROUTES - PERMISSION

echo -e "\n\nDefine a route for service PERMISSION - FIND\n"
curl -i -X POST -H "Content-Type: application/json" \
	--url http://$KONG_IP:8001/services/permission_find/routes \
	--data '{"name":"permissionFindRoute","paths":["/permission_find"],"service":{"name":"permission_find"},"preserve_host":false,"strip_path":true}'

sleep 3

# ROUTES - PROFILE

echo -e "\n\nDefine a route for service PROFILE - ADD\n"
curl -i -X POST -H "Content-Type: application/json" \
	--url http://$KONG_IP:8001/services/profile_add/routes \
	--data '{"name":"profileAddRoute","paths":["/profile_add"],"service":{"name":"profile_add"},"preserve_host":false,"strip_path":true}'

sleep 3

echo -e "\n\nDefine a route for service PROFILE - UPDATE\n"
curl -i -X POST -H "Content-Type: application/json" \
	--url http://$KONG_IP:8001/services/profile_update/routes \
	--data '{"name":"profileUpdateRoute","paths":["/profile_update"],"service":{"name":"profile_update"},"preserve_host":false,"strip_path":true}'

sleep 3

echo -e "\n\nDefine a route for service PROFILE - DELETE\n"
curl -i -X POST -H "Content-Type: application/json" \
	--url http://$KONG_IP:8001/services/profile_delete/routes \
	--data '{"name":"profileDeleteRoute","paths":["/profile_delete"],"service":{"name":"profile_delete"},"preserve_host":false,"strip_path":true}'

sleep 3

echo -e "\n\nDefine a route for service PROFILE - FIND\n"
curl -i -X POST -H "Content-Type: application/json" \
	--url http://$KONG_IP:8001/services/profile_find/routes \
	--data '{"name":"profileFindRoute","paths":["/profile_find"],"service":{"name":"profile_find"},"preserve_host":false,"strip_path":true}'

sleep 3

echo -e "\n\nDefine a route for service PROFILE - FIND BY ID\n"
curl -i -X POST -H "Content-Type: application/json" \
	--url http://$KONG_IP:8001/services/profile_find_id/routes \
	--data '{"name":"profileFindByIdRoute","paths":["/profile_find_id"],"service":{"name":"profile_find_id"},"preserve_host":false,"strip_path":true}'

sleep 3

echo -e "\n\nDefine a route for service PROFILE - AUDIT - FULL\n"
curl -i -X POST -H "Content-Type: application/json" \
	--url http://$KONG_IP:8001/services/profile_audit_full/routes \
	--data '{"name":"profileAuditFull","paths":["/profile_audit_full"],"service":{"name":"profile_audit_full"},"preserve_host":false,"strip_path":true}'

sleep 3

echo -e "\n\nDefine a route for service PROFILE - AUDIT - ITEM\n"
curl -i -X POST -H "Content-Type: application/json" \
	--url http://$KONG_IP:8001/services/profile_audit_item/routes \
	--data '{"name":"profileAuditItem","paths":["/profile_audit_item"],"service":{"name":"profile_audit_item"},"preserve_host":false,"strip_path":true}'

sleep 3

echo -e "\n\nDefine a route for service PROFILE - AUDIT - DETAIL ITEM BY REVISION\n"
curl -i -X POST -H "Content-Type: application/json" \
	--url http://$KONG_IP:8001/services/profile_audit_item_detail/routes \
	--data '{"name":"profileAuditItemDetail","paths":["/profile_audit_item_detail"],"service":{"name":"profile_audit_item_detail"},"preserve_host":false,"strip_path":true}'

sleep 3

# ROUTES - USER

echo -e "\n\nDefine a route for service USER - ADD\n"
curl -i -X POST -H "Content-Type: application/json" \
	--url http://$KONG_IP:8001/services/user_add/routes \
	--data '{"name":"userAddRoute","paths":["/user_add"],"service":{"name":"user_add"},"preserve_host":false,"strip_path":true}'

sleep 3

echo -e "\n\nDefine a route for service USER - UPDATE\n"
curl -i -X POST -H "Content-Type: application/json" \
	--url http://$KONG_IP:8001/services/user_update/routes \
	--data '{"name":"userUpdateRoute","paths":["/user_update"],"service":{"name":"user_update"},"preserve_host":false,"strip_path":true}'

sleep 3

echo -e "\n\nDefine a route for service USER - DELETE\n"
curl -i -X POST -H "Content-Type: application/json" \
	--url http://$KONG_IP:8001/services/user_delete/routes \
	--data '{"name":"userDeleteRoute","paths":["/user_delete"],"service":{"name":"user_delete"},"preserve_host":false,"strip_path":true}'

sleep 3

echo -e "\n\nDefine a route for service USER - FIND\n"
curl -i -X POST -H "Content-Type: application/json" \
	--url http://$KONG_IP:8001/services/user_find/routes \
	--data '{"name":"userFindRoute","paths":["/user_find"],"service":{"name":"user_find"},"preserve_host":false,"strip_path":true}'

sleep 3

echo -e "\n\nDefine a route for service USER - FIND BY ID\n"
curl -i -X POST -H "Content-Type: application/json" \
	--url http://$KONG_IP:8001/services/user_find_id/routes \
	--data '{"name":"userFindByIdRoute","paths":["/user_find_id"],"service":{"name":"user_find_id"},"preserve_host":false,"strip_path":true}'

sleep 3

echo -e "\n\nDefine a route for service USER - AUDIT - FULL\n"
curl -i -X POST -H "Content-Type: application/json" \
	--url http://$KONG_IP:8001/services/user_audit_full/routes \
	--data '{"name":"userAuditFull","paths":["/user_audit_full"],"service":{"name":"user_audit_full"},"preserve_host":false,"strip_path":true}'

sleep 3

echo -e "\n\nDefine a route for service USER - AUDIT - ITEM\n"
curl -i -X POST -H "Content-Type: application/json" \
	--url http://$KONG_IP:8001/services/user_audit_item/routes \
	--data '{"name":"userAuditItem","paths":["/user_audit_item"],"service":{"name":"user_audit_item"},"preserve_host":false,"strip_path":true}'

sleep 3

echo -e "\n\nDefine a route for service USER - AUDIT - DETAIL ITEM BY REVISION\n"
curl -i -X POST -H "Content-Type: application/json" \
	--url http://$KONG_IP:8001/services/user_audit_item_detail/routes \
	--data '{"name":"userAuditItemDetail","paths":["/user_audit_item_detail"],"service":{"name":"user_audit_item_detail"},"preserve_host":false,"strip_path":true}'

sleep 3

# ROUTES - CHANGE PASSWORD

echo -e "\n\nDefine a route for service CHANGE PASSWORD - UPDATE\n"
curl -i -X POST -H "Content-Type: application/json" \
	--url http://$KONG_IP:8001/services/change_password/routes \
	--data '{"name":"changePassword","paths":["/change_password"],"service":{"name":"change_password"},"preserve_host":false,"strip_path":true}'

sleep 3





# JWT PLUGIN

# JWT - SERVICE PERMISSION

echo -e "\n\nAdd JWT plugin to service PERMISSION - FIND\n"
curl -X POST http://$KONG_IP:8001/services/permission_find/plugins \
	--data "name=jwt" \
	--data "config.secret_is_base64=false" \
	--data "config.run_on_preflight=true" \
	--data "config.claims_to_verify=exp"

sleep 3

# JWT - SERVICE PROFILE

echo -e "\n\nAdd JWT plugin to service PROFILE - ADD\n"
curl -X POST http://$KONG_IP:8001/services/profile_add/plugins \
	--data "name=jwt" \
	--data "config.secret_is_base64=false" \
	--data "config.run_on_preflight=true" \
	--data "config.claims_to_verify=exp"

sleep 3

echo -e "\n\nAdd JWT plugin to service PROFILE - UPDATE\n"
curl -X POST http://$KONG_IP:8001/services/profile_update/plugins \
	--data "name=jwt" \
	--data "config.secret_is_base64=false" \
	--data "config.run_on_preflight=true" \
	--data "config.claims_to_verify=exp"

sleep 3

echo -e "\n\nAdd JWT plugin to service PROFILE - DELETE\n"
curl -X POST http://$KONG_IP:8001/services/profile_delete/plugins \
	--data "name=jwt" \
	--data "config.secret_is_base64=false" \
	--data "config.run_on_preflight=true" \
	--data "config.claims_to_verify=exp"

sleep 3

echo -e "\n\nAdd JWT plugin to service PROFILE - FIND\n"
curl -X POST http://$KONG_IP:8001/services/profile_find/plugins \
	--data "name=jwt" \
	--data "config.secret_is_base64=false" \
	--data "config.run_on_preflight=true" \
	--data "config.claims_to_verify=exp"

sleep 3

echo -e "\n\nAdd JWT plugin to service PROFILE - FIND BY ID\n"
curl -X POST http://$KONG_IP:8001/services/profile_find_id/plugins \
	--data "name=jwt" \
	--data "config.secret_is_base64=false" \
	--data "config.run_on_preflight=true" \
	--data "config.claims_to_verify=exp"

sleep 3

echo -e "\n\nAdd JWT plugin to service PROFILE - AUDIT - FULL\n"
curl -X POST http://$KONG_IP:8001/services/profile_audit_full/plugins \
	--data "name=jwt" \
	--data "config.secret_is_base64=false" \
	--data "config.run_on_preflight=true" \
	--data "config.claims_to_verify=exp"

sleep 3

echo -e "\n\nAdd JWT plugin to service PROFILE - AUDIT - ITEM\n"
curl -X POST http://$KONG_IP:8001/services/profile_audit_item/plugins \
	--data "name=jwt" \
	--data "config.secret_is_base64=false" \
	--data "config.run_on_preflight=true" \
	--data "config.claims_to_verify=exp"

sleep 3

echo -e "\n\nAdd JWT plugin to service PROFILE - AUDIT - DETAIL ITEM BY REVISION\n"
curl -X POST http://$KONG_IP:8001/services/profile_audit_item_detail/plugins \
	--data "name=jwt" \
	--data "config.secret_is_base64=false" \
	--data "config.run_on_preflight=true" \
	--data "config.claims_to_verify=exp"

sleep 3

# JWT - SERVICE USER

echo -e "\n\nAdd JWT plugin to service USER - ADD\n"
curl -X POST http://$KONG_IP:8001/services/user_add/plugins \
	--data "name=jwt" \
	--data "config.secret_is_base64=false" \
	--data "config.run_on_preflight=true" \
	--data "config.claims_to_verify=exp"

sleep 3

echo -e "\n\nAdd JWT plugin to service USER - UPDATE\n"
curl -X POST http://$KONG_IP:8001/services/user_update/plugins \
	--data "name=jwt" \
	--data "config.secret_is_base64=false" \
	--data "config.run_on_preflight=true" \
	--data "config.claims_to_verify=exp"

sleep 3

echo -e "\n\nAdd JWT plugin to service USER - DELETE\n"
curl -X POST http://$KONG_IP:8001/services/user_delete/plugins \
	--data "name=jwt" \
	--data "config.secret_is_base64=false" \
	--data "config.run_on_preflight=true" \
	--data "config.claims_to_verify=exp"

sleep 3

echo -e "\n\nAdd JWT plugin to service USER - FIND\n"
curl -X POST http://$KONG_IP:8001/services/user_find/plugins \
	--data "name=jwt" \
	--data "config.secret_is_base64=false" \
	--data "config.run_on_preflight=true" \
	--data "config.claims_to_verify=exp"

sleep 3

echo -e "\n\nAdd JWT plugin to service USER - FIND BY ID\n"
curl -X POST http://$KONG_IP:8001/services/user_find_id/plugins \
	--data "name=jwt" \
	--data "config.secret_is_base64=false" \
	--data "config.run_on_preflight=true" \
	--data "config.claims_to_verify=exp"

sleep 3

echo -e "\n\nAdd JWT plugin to service USER - AUDIT - FULL\n"
curl -X POST http://$KONG_IP:8001/services/user_audit_full/plugins \
	--data "name=jwt" \
	--data "config.secret_is_base64=false" \
	--data "config.run_on_preflight=true" \
	--data "config.claims_to_verify=exp"

sleep 3

echo -e "\n\nAdd JWT plugin to service USER - AUDIT - ITEM\n"
curl -X POST http://$KONG_IP:8001/services/user_audit_item/plugins \
	--data "name=jwt" \
	--data "config.secret_is_base64=false" \
	--data "config.run_on_preflight=true" \
	--data "config.claims_to_verify=exp"

sleep 3

echo -e "\n\nAdd JWT plugin to service USER - AUDIT - DETAIL ITEM BY REVISION\n"
curl -X POST http://$KONG_IP:8001/services/user_audit_item_detail/plugins \
	--data "name=jwt" \
	--data "config.secret_is_base64=false" \
	--data "config.run_on_preflight=true" \
	--data "config.claims_to_verify=exp"

sleep 3

# JWT - ROUTE PERMISSION

echo -e "\n\nAdd JWT plugin to route PERMISSION - FIND\n"
curl -X POST http://$KONG_IP:8001/routes/permissionFindRoute/plugins \
	--data "name=jwt" \
	--data "config.secret_is_base64=false" \
	--data "config.run_on_preflight=true" \
        --data "config.claims_to_verify=exp"

sleep 3

# JWT - ROUTE PROFILE

echo -e "\n\nAdd JWT plugin to route PROFILE - ADD\n"
curl -X POST http://$KONG_IP:8001/routes/profileAddRoute/plugins \
	--data "name=jwt" \
	--data "config.secret_is_base64=false" \
	--data "config.run_on_preflight=true" \
        --data "config.claims_to_verify=exp"

sleep 3

echo -e "\n\nAdd JWT plugin to route PROFILE - UPDATE\n"
curl -X POST http://$KONG_IP:8001/routes/profileUpdateRoute/plugins \
	--data "name=jwt" \
	--data "config.secret_is_base64=false" \
	--data "config.run_on_preflight=true" \
        --data "config.claims_to_verify=exp"

sleep 3

echo -e "\n\nAdd JWT plugin to route PROFILE - DELETE\n"
curl -X POST http://$KONG_IP:8001/routes/profileDeleteRoute/plugins \
	--data "name=jwt" \
	--data "config.secret_is_base64=false" \
	--data "config.run_on_preflight=true" \
        --data "config.claims_to_verify=exp"

sleep 3

echo -e "\n\nAdd JWT plugin to route PROFILE - FIND\n"
curl -X POST http://$KONG_IP:8001/routes/profileFindRoute/plugins \
	--data "name=jwt" \
	--data "config.secret_is_base64=false" \
	--data "config.run_on_preflight=true" \
        --data "config.claims_to_verify=exp"

sleep 3

echo -e "\n\nAdd JWT plugin to route PROFILE - FIND BY ID\n"
curl -X POST http://$KONG_IP:8001/routes/profileFindByIdRoute/plugins \
	--data "name=jwt" \
	--data "config.secret_is_base64=false" \
	--data "config.run_on_preflight=true" \
        --data "config.claims_to_verify=exp"

sleep 3

echo -e "\n\nAdd JWT plugin to route PROFILE - AUDIT - FULL\n"
curl -X POST http://$KONG_IP:8001/routes/profileAuditFull/plugins \
	--data "name=jwt" \
	--data "config.secret_is_base64=false" \
	--data "config.run_on_preflight=true" \
        --data "config.claims_to_verify=exp"

sleep 3

echo -e "\n\nAdd JWT plugin to route PROFILE - AUDIT - ITEM\n"
curl -X POST http://$KONG_IP:8001/routes/profileAuditItem/plugins \
	--data "name=jwt" \
	--data "config.secret_is_base64=false" \
	--data "config.run_on_preflight=true" \
        --data "config.claims_to_verify=exp"

sleep 3

echo -e "\n\nAdd JWT plugin to route PROFILE - AUDIT - DETAIL ITEM BY REVISION\n"
curl -X POST http://$KONG_IP:8001/routes/profileAuditItemDetail/plugins \
	--data "name=jwt" \
	--data "config.secret_is_base64=false" \
	--data "config.run_on_preflight=true" \
        --data "config.claims_to_verify=exp"

sleep 3

# JWT - ROUTE USER

echo -e "\n\nAdd JWT plugin to route USER - ADD\n"
curl -X POST http://$KONG_IP:8001/routes/userAddRoute/plugins \
	--data "name=jwt" \
	--data "config.secret_is_base64=false" \
	--data "config.run_on_preflight=true" \
        --data "config.claims_to_verify=exp"

sleep 3

echo -e "\n\nAdd JWT plugin to route USER - UPDATE\n"
curl -X POST http://$KONG_IP:8001/routes/userUpdateRoute/plugins \
	--data "name=jwt" \
	--data "config.secret_is_base64=false" \
	--data "config.run_on_preflight=true" \
        --data "config.claims_to_verify=exp"

sleep 3

echo -e "\n\nAdd JWT plugin to route USER - DELETE\n"
curl -X POST http://$KONG_IP:8001/routes/userDeleteRoute/plugins \
	--data "name=jwt" \
	--data "config.secret_is_base64=false" \
	--data "config.run_on_preflight=true" \
        --data "config.claims_to_verify=exp"

sleep 3

echo -e "\n\nAdd JWT plugin to route USER - FIND\n"
curl -X POST http://$KONG_IP:8001/routes/userFindRoute/plugins \
	--data "name=jwt" \
	--data "config.secret_is_base64=false" \
	--data "config.run_on_preflight=true" \
        --data "config.claims_to_verify=exp"

sleep 3

echo -e "\n\nAdd JWT plugin to route USER - FIND BY ID\n"
curl -X POST http://$KONG_IP:8001/routes/userFindByIdRoute/plugins \
	--data "name=jwt" \
	--data "config.secret_is_base64=false" \
	--data "config.run_on_preflight=true" \
        --data "config.claims_to_verify=exp"

sleep 3

echo -e "\n\nAdd JWT plugin to route USER - AUDIT - FULL\n"
curl -X POST http://$KONG_IP:8001/routes/userAuditFull/plugins \
	--data "name=jwt" \
	--data "config.secret_is_base64=false" \
	--data "config.run_on_preflight=true" \
        --data "config.claims_to_verify=exp"

sleep 3

echo -e "\n\nAdd JWT plugin to route USER - AUDIT - ITEM\n"
curl -X POST http://$KONG_IP:8001/routes/userAuditItem/plugins \
	--data "name=jwt" \
	--data "config.secret_is_base64=false" \
	--data "config.run_on_preflight=true" \
        --data "config.claims_to_verify=exp"

sleep 3

echo -e "\n\nAdd JWT plugin to route USER - AUDIT - DETAIL ITEM BY REVISION\n"
curl -X POST http://$KONG_IP:8001/routes/userAuditItemDetail/plugins \
	--data "name=jwt" \
	--data "config.secret_is_base64=false" \
	--data "config.run_on_preflight=true" \
        --data "config.claims_to_verify=exp"

sleep 2

echo -e "\n\nEnd of processing....\n\n"
