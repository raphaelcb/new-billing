package br.com.cpqd.billing.comptech.exception.core.jpa.config;

import br.com.cpqd.billing.comptech.exception.core.CoreException;

/**
 * This class represents the JPA Configuration exception.
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 * @see br.com.cpqd.billing.comptech.exception.core.CoreException
 */
@SuppressWarnings("serial")
public class JPAConfigException extends CoreException {

    /**
     * Constructor for JPAConfigException.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     */
    public JPAConfigException() {

        super();
    }

    /**
     * Constructor for JPAConfigException.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param msg The detailed message
     */
    public JPAConfigException(String msg) {

        super(msg);
    }

    /**
     * Constructor for JPAConfigException.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param msg The detailed message
     * @param cause The root cause
     */
    public JPAConfigException(String msg, Throwable cause) {

        super(msg, cause);
    }

    /**
     * Constructor for JPAConfigException.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param cause The root cause
     */
    public JPAConfigException(Throwable cause) {

        super(cause);
    }

}
