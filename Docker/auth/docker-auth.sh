#!/bin/sh

# Define the path
authentication_app_build_path=/var/lib/jenkins/.m2/repository/br/com/cpqd/billing/auth/auth-api/1.0.0-SNAPSHOT
authentication_app_build_version=1.0.0-SNAPSHOT
docker_context=/home/raphaelb/git/new-billing/Docker/auth
docker_image_namespace=raphaelcb
docker_auth_app_version=1.0
docker_auth_database_version=1.0
DOCKER_HUB_UNAME=raphaelcb

echo "############################################################################"
echo "Copying the Authentication app build file to Docker folder..."

# Copy the authentication app build file to mount the docker image
cp $authentication_app_build_path/auth-api-$authentication_app_build_version.war $docker_context/app/auth-api-$authentication_app_build_version.war

echo ""

echo "############################################################################"
echo "Building the Authentication app microservice image..."
echo ""
# Build the authentication app microservice image
docker build -f billing-auth-app.dockerfile -t $docker_image_namespace/billing-auth-app:$docker_auth_app_version $docker_context
docker tag $docker_image_namespace/billing-auth-app:$docker_auth_app_version $docker_image_namespace/billing-auth-app:latest

echo ""

echo "############################################################################"
echo "Building the Authentication database microservice image..."
echo ""
# Build the authentication database image to authentication app microservice
docker build -f billing-auth-database.dockerfile -t $docker_image_namespace/billing-auth-database:$docker_auth_database_version $docker_context
docker tag $docker_image_namespace/billing-auth-database:$docker_auth_database_version $docker_image_namespace/billing-auth-database:latest

echo ""

echo "############################################################################"
echo "Deleting the Authentication app build file in Docker folder..."

# Delete the authentication build app build file
rm $docker_context/app/auth-api-$authentication_app_build_version.war

echo ""

echo "############################################################################"
echo "Log to Docker Hub..."

# Log to docker hub
docker login --username=$DOCKER_HUB_UNAME

echo ""

echo "############################################################################"
echo "Pushing the Authentication app microservice image to Docker Hub..."
echo ""
# Push the authentication app microservice image to dockerhub
docker push $docker_image_namespace/billing-auth-app:$docker_auth_app_version
echo ""
docker push $docker_image_namespace/billing-auth-app:latest

echo ""

echo "############################################################################"
echo "Pushing the Authentication database microservice image to Docker Hub..."
echo ""
# Push the authentication database microservice image to dockerhub
docker push $docker_image_namespace/billing-auth-database:$docker_auth_database_version
echo ""
docker push $docker_image_namespace/billing-auth-database:latest

echo ""

echo "############################################################################"
echo "End..."

echo ""
