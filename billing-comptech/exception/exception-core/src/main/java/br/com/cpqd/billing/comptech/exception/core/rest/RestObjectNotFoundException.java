package br.com.cpqd.billing.comptech.exception.core.rest;

/**
 * This class represents the REST exception when entities objects were not found.
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 * @see java.lang.RuntimeException
 */
@SuppressWarnings("serial")
public class RestObjectNotFoundException extends RuntimeException {

    /**
     * Constructor for RestObjectNotFoundException.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param msg The detailed message
     */
    public RestObjectNotFoundException(String msg) {

        super(msg);
    }
    
}
