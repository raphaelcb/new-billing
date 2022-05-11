#!/bin/sh

# Define the path
company_app_build_path=/var/lib/jenkins/.m2/repository/br/com/cpqd/billing/company/company-api/1.0.0-SNAPSHOT
company_app_build_version=1.0.0-SNAPSHOT
docker_context=/home/raphaelb/git/new-billing/Docker/company
docker_image_namespace=raphaelcb
docker_company_app_version=1.0
docker_company_database_version=1.0
DOCKER_HUB_UNAME=raphaelcb

echo "############################################################################"
echo "Copying the Company app build file to Docker folder..."

# Copy the company app build file to mount the docker image
cp $company_app_build_path/company-api-$company_app_build_version.war $docker_context/app/company-api-$company_app_build_version.war

echo ""

echo "############################################################################"
echo "Building the Company app microservice image..."
echo ""
# Build the company app microservice image
docker build -f billing-company-app.dockerfile -t $docker_image_namespace/billing-company-app:$docker_company_app_version $docker_context
docker tag $docker_image_namespace/billing-company-app:$docker_company_app_version $docker_image_namespace/billing-company-app:latest

echo ""

echo "############################################################################"
echo "Building the Company database microservice image..."
echo ""
# Build the company database image to company app microservice
docker build -f billing-company-database.dockerfile -t $docker_image_namespace/billing-company-database:$docker_company_database_version $docker_context
docker tag $docker_image_namespace/billing-company-database:$docker_company_database_version $docker_image_namespace/billing-company-database:latest

echo ""

echo "############################################################################"
echo "Deleting the Company app build file in Docker folder..."

# Delete the company build app build file
rm $docker_context/app/company-api-$company_app_build_version.war

echo ""

echo "############################################################################"
echo "Log to Docker Hub..."

# Log to docker hub
docker login --username=$DOCKER_HUB_UNAME

echo ""

echo "############################################################################"
echo "Pushing the Company app microservice image to Docker Hub..."
echo ""
# Push the company app microservice image to dockerhub
docker push $docker_image_namespace/billing-company-app:$docker_company_app_version
echo ""
docker push $docker_image_namespace/billing-company-app:latest

echo ""

echo "############################################################################"
echo "Pushing the Company database microservice image to Docker Hub..."
echo ""
# Push the company database microservice image to dockerhub
docker push $docker_image_namespace/billing-company-database:$docker_company_database_version
echo ""
docker push $docker_image_namespace/billing-company-database:latest

echo ""

echo "############################################################################"
echo "End..."

echo ""
