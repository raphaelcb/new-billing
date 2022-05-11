package br.com.cpqd.billing.auth.api.config.persistence;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import javax.transaction.TransactionManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import br.com.cpqd.SpringBootBillingAuthApplication;
import br.com.cpqd.billing.comptech.exception.core.jpa.config.JPAConfigException;
import lombok.extern.log4j.Log4j2;

/**
 * This class is responsible for configuring the JPA and the transaction management for application.
 * <p>
 * It uses an external configuration file to get the basic informations to connect in database and the base
 * file can be obtained in {@code src/main/resources} folder. The base file is
 * {@code persistence-postgresql-reference.properties}.
 * </p>
 * <p>
 * An environment variable must be configured with path to this file and the base name for variable is
 * {@code APP_DB_HOME}. The filename can't have your name changed and must be
 * {@code persistence-postgresql.properties}.
 * </p>
 * <p>
 * The transaction control is made in Spring's services classes that are noted with {@code Service} and
 * {@code Transactional}.
 * </p>
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 */
@Log4j2
@Configuration
@EnableTransactionManagement
@PropertySource({"file:${APP_DB_HOME}/persistence-postgresql.properties"})
@EnableJpaRepositories(basePackageClasses = SpringBootBillingAuthApplication.class, repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
public class JPAConfig {

    /**
     * Attribute that represents a message when some error in JPA Configuration is detected
     */
    private final String MESSAGE_ERROR_VAR_CONFIG_NOT_FOUND = "Attribute \"%s\" cannot be null. Please verify your \"persistence-postgresql.properties\" file in \"%s\"";

    /**
     * Injection of dependency of {@link Environment}
     */
    @Autowired
    private Environment env;

    /**
     * Method responsible for creating the {@link TransactionManager} for application.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param emf The {@link EntityManagerFactory} object
     * @return The {@link TransactionManager} obtained
     */
    @Bean
    public PlatformTransactionManager transactionManager(final EntityManagerFactory emf) {

        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }

    /**
     * Method responsible for expose the exceptions on database layer.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @return the {@link PersistenceExceptionTranslationPostProcessor} object
     */
    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {

        return new PersistenceExceptionTranslationPostProcessor();
    }

    /**
     * Method responsible for configuring the entity manager for JPA.
     * 
     * @author Barbosa, Rahpael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @return The entity manager configured
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

        try {
            // Call the method to validate the JPA basic informations
            validateJPABasicInformations();
        } catch (JPAConfigException e) {
            log.error("Error on getting the JPA Config basic informations. Exception: " + e.getMessage());
            return null;
        }

        // Create the entity manager factory bean
        final LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();

        // Associate the JPA data source and the path to scan the model's classes
        emf.setDataSource(jpaDataSource());
        emf.setPackagesToScan(SpringBootBillingAuthApplication.class.getPackage().getName());

        // Associate the Vendor Adapter (database)
        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        emf.setJpaVendorAdapter(vendorAdapter);

        // Configure the other properties for database
        emf.setJpaProperties(additionalProperties());

        // Return the entity manager factory bean
        return emf;
    }

    /**
     * Method responsible for validating the JPA Config basic informations.
     * <p>
     * Each entry that is in configuration file are required and if one is not configured, an exception is
     * thrown and the application server initialization is interrupted.
     * </p>
     * <p>
     * The exception thrown will show to user which entry is not configured properly.
     * </p>
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @throws JPAConfigException Exception thrown when some variable of configuration was not found
     */
    private void validateJPABasicInformations() throws JPAConfigException {

        String pathConfigFile = this.env.getProperty("MARKETPLACE_DB_HOME");

        for (JPAConfigEnum element : JPAConfigEnum.values()) {
            if (this.env.getProperty(element.getKey()) == null) {
                throw new JPAConfigException(
                        String.format(MESSAGE_ERROR_VAR_CONFIG_NOT_FOUND, element.getKey(), pathConfigFile));
            }
        }
    }

    /**
     * Method responsible for configuring the {@link DataSource} for JPA.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @return The {@link DataSource} configured
     */
    @Bean
    public DataSource jpaDataSource() {

        /*
         * Create the object and retrieve the informations through keys configured in file
         * "persistence-postgresql.properties" that was configured on top of this class through annotation
         * {@code @PropertySource}. The properties file exists in src/main/resources
         */
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(
                this.env.getProperty(JPAConfigEnum.VAR_CONFIG_JDBC_DRIVERCLASSNAME.getKey()));
        dataSource.setUrl(this.env.getProperty(JPAConfigEnum.VAR_CONFIG_JDBC_URL.getKey()));
        dataSource.setUsername(this.env.getProperty(JPAConfigEnum.VAR_CONFIG_JDBC_USER.getKey()));
        dataSource.setPassword(this.env.getProperty(JPAConfigEnum.VAR_CONFIG_JDBC_PASS.getKey()));

        // Return the datasource configured
        return dataSource;
    }

    /**
     * Method responsible for configuring extra properties for JPA.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @return The extra properties for JPA
     */
    final Properties additionalProperties() {

        final Properties hibernateProperties = new Properties();

        hibernateProperties.setProperty("hibernate.dialect",
                this.env.getProperty(JPAConfigEnum.VAR_CONFIG_JPA_HIBERNATE_DIALECT.getKey()));
//        hibernateProperties.setProperty("hibernate.hbm2ddl.auto",
//                this.env.getProperty(JPAConfigEnum.VAR_CONFIG_JPA_HIBERNATE_HBM2DDL_AUTO.getKey()));
        hibernateProperties.setProperty("hibernate.show_sql",
                this.env.getProperty(JPAConfigEnum.VAR_CONFIG_JPA_HIBERNATE_SHOW_SQL.getKey()));
        hibernateProperties.setProperty("hibernate.format_sql",
                this.env.getProperty(JPAConfigEnum.VAR_CONFIG_JPA_HIBERNATE_FORMAT_SQL.getKey()));
        hibernateProperties.setProperty("hibernate.use_sql_comments",
                this.env.getProperty(JPAConfigEnum.VAR_CONFIG_JPA_HIBERNATE_USE_SQL_COMMENTS.getKey()));

        return hibernateProperties;
    }

}
