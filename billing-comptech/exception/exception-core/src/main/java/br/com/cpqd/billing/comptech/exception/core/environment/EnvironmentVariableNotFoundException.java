package br.com.cpqd.billing.comptech.exception.core.environment;

import br.com.cpqd.billing.comptech.exception.core.CoreException;

/**
 * This class represents the Environment Variable exception.
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 * @see br.com.cpqd.billing.comptech.exception.core.CoreException
 */
@SuppressWarnings("serial")
public class EnvironmentVariableNotFoundException extends CoreException {

    /**
     * Constructor for EnvironmentVariableNotFoundException.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     */
    public EnvironmentVariableNotFoundException() {

        super();
    }

    /**
     * Constructor for EnvironmentVariableNotFoundException.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param msg The detailed message
     */
    public EnvironmentVariableNotFoundException(String msg) {

        super(msg);
    }

    /**
     * Constructor for EnvironmentVariableNotFoundException.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param msg The detailed message
     * @param cause The root cause
     */
    public EnvironmentVariableNotFoundException(String msg, Throwable cause) {

        super(msg, cause);
    }

    /**
     * Constructor for EnvironmentVariableNotFoundException.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param cause The root cause
     */
    public EnvironmentVariableNotFoundException(Throwable cause) {

        super(cause);
    }

}
