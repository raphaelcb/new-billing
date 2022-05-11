package br.com.cpqd.billing.comptech.exception.core;

/**
 * This class represents the root of the hierarchy of exceptions.
 * <p>
 * This exception hierarchy aims to let user find and handle the kind of error found without knowing the
 * details of the particular exception.
 * </p>
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 * @see java.lang.Exception
 */
@SuppressWarnings("serial")
public class CoreException extends Exception {

    /**
     * Constructor for CoreException.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     */
    public CoreException() {

        super();
    }

    /**
     * Constructor for CoreException.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param msg The detailed message
     */
    public CoreException(String msg) {

        super(msg);
    }

    /**
     * Constructor for CoreException.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param msg The detailed message
     * @param cause The root cause
     */
    public CoreException(String msg, Throwable cause) {

        super(msg, cause);
    }

    /**
     * Constructor for CoreException.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param cause The root cause
     */
    public CoreException(Throwable cause) {

        super(cause);
    }

}
