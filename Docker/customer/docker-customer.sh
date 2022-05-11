#!/bin/sh

# Define the path
customer_app_build_path=/var/lib/jenkins/.m2/repository/br/com/cpqd/billing/customer/customer-api/1.0.0-SNAPSHOT
customer_app_build_version=1.0.0-SNAPSHOT
docker_context=/home/raphaelb/git/new-billing/Docker/customer
docker_image_namespace=raphaelcb
docker_customer_app_version=1.0
docker_customer_database_version=1.0
DOCKER_HUB_UNAME=raphaelcb

echo "############################################################################"
echo "Copying the Customer app build file to Docker folder..."

# Copy the customer app build file to mount the docker image
cp $customer_app_build_path/customer-api-$customer_app_build_version.war $docker_context/app/customer-api-$customer_app_build_version.war

echo ""

echo "############################################################################"
echo "Building the Customer app microservice image..."
echo ""
# Build the customer app microservice image
docker build -f billing-customer-app.dockerfile -t $docker_image_namespace/billing-customer-app:$docker_customer_app_version $docker_context
docker tag $docker_image_namespace/billing-customer-app:$docker_customer_app_version $docker_image_namespace/billing-customer-app:latest

echo ""

echo "############################################################################"
echo "Building the Customer database microservice image..."
echo ""
# Build the customer database image to customer app microservice
docker build -f billing-customer-database.dockerfile -t $docker_image_namespace/billing-customer-database:$docker_customer_database_version $docker_context
docker tag $docker_image_namespace/billing-customer-database:$docker_customer_database_version $docker_image_namespace/billing-customer-database:latest

echo ""

echo "############################################################################"
echo "Deleting the Customer app build file in Docker folder..."

# Delete the customer build app build file
rm $docker_context/app/customer-api-$customer_app_build_version.war

echo ""

echo "############################################################################"
echo "Log to Docker Hub..."

# Log to docker hub
docker login --username=$DOCKER_HUB_UNAME

echo ""

echo "############################################################################"
echo "Pushing the Customer app microservice image to Docker Hub..."
echo ""
# Push the customer app microservice image to dockerhub
docker push $docker_image_namespace/billing-customer-app:$docker_customer_app_version
echo ""
docker push $docker_image_namespace/billing-customer-app:latest

echo ""

echo "############################################################################"
echo "Pushing the Customer database microservice image to Docker Hub..."
echo ""
# Push the customer database microservice image to dockerhub
docker push $docker_image_namespace/billing-customer-database:$docker_customer_database_version
echo ""
docker push $docker_image_namespace/billing-customer-database:latest

echo ""

echo "############################################################################"
echo "End..."

echo ""
