package br.com.cpqd.billing.comptech.security.model.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * This class represents an enumeration to LDAP configuration properties.
 * <p>
 * These properties name must be defined as environment variables for future read.
 * </p>
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 */
@AllArgsConstructor
public enum LdapConfigEnum {

    VAR_CONFIG_LDAP_SEARCH_BASE("ldap_searchBase"), 
    VAR_CONFIG_LDAP_SEARCH_FILTER("ldap_searchFilter"), 
    VAR_CONFIG_LDAP_GROUP_SEARCH_BASE("ldap_groupSearchBase"),
    VAR_CONFIG_LDAP_GROUP_SEARCH_FILTER("ldap_groupSearchFilter"),
    VAR_CONFIG_LDAP_URL("ldap_url"),
    VAR_CONFIG_LDAP_PORT("ldap_port"),
    VAR_CONFIG_LDAP_MANAGER_DN("ldap_managerDN"),
    VAR_CONFIG_LDAP_MANAGER_PASSWORD("ldap_managerPassword");

    /**
     * Attribute that represents the configuration key of each property of this enumeration
     */
    @Getter
    private String key;

}
