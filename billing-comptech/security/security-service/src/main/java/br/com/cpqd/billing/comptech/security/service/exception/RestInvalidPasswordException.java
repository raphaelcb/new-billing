package br.com.cpqd.billing.comptech.security.service.exception;

import br.com.cpqd.billing.comptech.security.service.UserService;
import lombok.Getter;

/**
 * This class represents the REST exception when the user password is invalid on create or update methods of
 * the {@link UserService} class.
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 * @see java.lang.RuntimeException
 */
@SuppressWarnings("serial")
public class RestInvalidPasswordException extends RuntimeException {
    
    /**
     * Attribute that represents the field name associated with rest exception
     */
    @Getter
    private String field;
    
    /**
     * Constructor for RestObjectNotFoundException.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param field The password field name
     * @param msg The detailed message
     */
    public RestInvalidPasswordException(String field, String msg) {

        super(msg);
        this.field = field;
    }

}
