package br.com.cpqd.billing.company.api.config.persistence;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * This class represents an enumeration to JPA configuration.
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 */
@AllArgsConstructor
public enum JPAConfigEnum {

    VAR_CONFIG_JDBC_DRIVERCLASSNAME("jdbc.driverClassName"), 
    VAR_CONFIG_JDBC_URL("jdbc.url"), 
    VAR_CONFIG_JDBC_USER("jdbc.user"), 
    VAR_CONFIG_JDBC_PASS("jdbc.pass"), 
    VAR_CONFIG_JPA_HIBERNATE_DIALECT("jpa.hibernate.dialect"), 
    VAR_CONFIG_JPA_HIBERNATE_HBM2DDL_AUTO("jpa.hibernate.hbm2ddl.auto"), 
    VAR_CONFIG_JPA_HIBERNATE_SHOW_SQL("jpa.hibernate.show_sql"), 
    VAR_CONFIG_JPA_HIBERNATE_FORMAT_SQL("jpa.hibernate.format_sql"), 
    VAR_CONFIG_JPA_HIBERNATE_USE_SQL_COMMENTS("jpa.hibernate.use_sql_comments");

    /**
     * Attribute that represents the key configuration of each property of this enumeration
     */
    @Getter
    private String key;

}
