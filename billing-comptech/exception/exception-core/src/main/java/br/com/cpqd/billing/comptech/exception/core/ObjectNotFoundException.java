package br.com.cpqd.billing.comptech.exception.core;

/**
 * This class represents an exception for situation when objects were not found in application.
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 * @see br.com.cpqd.billing.comptech.exception.core.CoreException
 */
@SuppressWarnings("serial")
public class ObjectNotFoundException extends CoreException {

    /**
     * Construtor for ObjectNotFoundException.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     */
    public ObjectNotFoundException() {

        super();
    }

	/**
	 * Constructor for ObjectNotFoundException.
	 * 
	 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
	 * @since 1.0
	 * @param msg The detailed message
	 */
    public ObjectNotFoundException(String msg) {

        super(msg);
    }

	/**
	 * Constructor for ObjectNotFoundException.
	 * 
	 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
	 * @since 1.0
	 * @param msg   The detailed message
	 * @param cause The root cause
	 */
    public ObjectNotFoundException(String msg, Throwable cause) {

        super(msg, cause);
    }

	/**
	 * Constructor for ObjectNotFoundException.
	 * 
	 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
	 * @since 1.0
	 * @param cause The root cause
	 */
    public ObjectNotFoundException(Throwable cause) {

        super(cause);
    }

}
