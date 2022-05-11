# #####################################################################
# Dockerfile for Billing Customer App
# @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
# @since 1.0
# #####################################################################

# Image base
FROM tomcat:9.0

# Create the app directories
RUN mkdir /opt/billing-customer-app
RUN mkdir /opt/billing-customer-app/log

# Copy database configuration file
COPY /app/persistence-postgresql.properties /opt/billing-customer-app

# Copy customer app microservice
COPY /app/customer-api-1.0.0-SNAPSHOT.war /usr/local/tomcat/webapps/customer.war

# Run tomcat
CMD ["/usr/local/tomcat/bin/catalina.sh", "run"]