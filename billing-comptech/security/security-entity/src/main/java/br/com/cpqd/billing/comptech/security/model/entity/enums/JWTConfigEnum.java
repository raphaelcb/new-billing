package br.com.cpqd.billing.comptech.security.model.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * This class represents an enumeration to JWT configuration properties.
 * <p>
 * These properties name must be defined as environment variables for future read.
 * </p>
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 */
@AllArgsConstructor
public enum JWTConfigEnum {

    VAR_CONFIG_ISSUER("jwt_issuer"), 
    VAR_CONFIG_ISSUER_SECRET("jwt_issuer_secret"), 
    VAR_CONFIG_TOKEN_EXPIRATION("jwt_token_expiration");

    /**
     * Attribute that represents the configuration key of each property of this enumeration
     */
    @Getter
    private String key;

}
