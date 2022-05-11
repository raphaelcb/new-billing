# #####################################################################
# Dockerfile for Billing Company App
# @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
# @since 1.0
# #####################################################################

# Image base
FROM tomcat:9.0

# Create the app directories
RUN mkdir /opt/billing-company-app
RUN mkdir /opt/billing-company-app/log

# Copy database configuration file
COPY /app/persistence-postgresql.properties /opt/billing-company-app

# Copy company app microservice
COPY /app/company-api-1.0.0-SNAPSHOT.war /usr/local/tomcat/webapps/company.war

# Run tomcat
CMD ["/usr/local/tomcat/bin/catalina.sh", "run"]