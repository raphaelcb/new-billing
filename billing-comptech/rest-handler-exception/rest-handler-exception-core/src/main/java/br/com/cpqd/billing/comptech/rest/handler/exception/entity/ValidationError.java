package br.com.cpqd.billing.comptech.rest.handler.exception.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * This class represents a model for {@link ValidationError} entity.
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 */
@AllArgsConstructor
@ToString
public class ValidationError {

    /**
     * Attribute that represents the field where the error occurs
     */
    @Getter
    private String field;

    /**
     * Attribute that represents the error description
     */
    @Getter
    private String description;

}
