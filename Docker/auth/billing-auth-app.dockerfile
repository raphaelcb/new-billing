# #####################################################################
# Dockerfile for Billing Authentication and Authorization App
# @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
# @since 1.0
# #####################################################################

# Image base
FROM tomcat:9.0

# Create the app directories
RUN mkdir /opt/billing-auth-app
RUN mkdir /opt/billing-auth-app/log

# Copy database configuration file
COPY /app/persistence-postgresql.properties /opt/billing-auth-app

# Copy auth app microservice
COPY /app/auth-api-1.0.0-SNAPSHOT.war /usr/local/tomcat/webapps/auth.war

# Run tomcat
CMD ["/usr/local/tomcat/bin/catalina.sh", "run"]