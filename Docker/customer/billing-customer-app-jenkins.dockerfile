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
COPY /Docker/customer/app/persistence-postgresql.properties /opt/billing-customer-app

# Copy customer app microservice
COPY /billing-customer/customer-api/target/customer-api.war /usr/local/tomcat/webapps/customer.war

# Run tomcat
CMD ["/usr/local/tomcat/bin/catalina.sh", "run"]