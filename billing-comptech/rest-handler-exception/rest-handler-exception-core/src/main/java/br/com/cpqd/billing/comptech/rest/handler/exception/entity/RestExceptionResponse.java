package br.com.cpqd.billing.comptech.rest.handler.exception.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * This class represents a model for {@link RestExceptionResponse} entity.
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 */
@AllArgsConstructor
@ToString
public class RestExceptionResponse {

    /**
     * Attribute that represents the timestamp of the REST response
     */
    @Getter
    private String timestamp;

    /**
     * Attribute that represents the HTTP Status code
     */
    @Getter
    private int status;

    /**
     * Attribute that represents the HTTP Status description
     */
    @Getter
    private String description;

    /**
     * Attribute that represents the error list
     */
    @Getter
    private List<ValidationError> errors;

    /**
     * Attribute that represents the URI path
     */
    @Getter
    private String path;

}
