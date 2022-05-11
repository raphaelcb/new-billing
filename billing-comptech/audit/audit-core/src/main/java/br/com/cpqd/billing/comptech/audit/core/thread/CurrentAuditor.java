package br.com.cpqd.billing.comptech.audit.core.thread;

/**
 * This class represents the implementation of the {@link ThreadLocal} for storing important data that will be
 * used on audit. These data are individually used for the current thread and will be retrieved on
 * appropriated moment.
 * <p>
 * This particular thread local will be store data involving the Auditor name that is realizing the operation
 * (ADD/UPDATE/DELETE).
 * </p>
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 */
public class CurrentAuditor {

    /**
     * Attributes that represents the {@link ThreadLocal} for storing the Auditor name
     */
    public static final CurrentAuditor INSTANCE = new CurrentAuditor();
    private static final ThreadLocal<String> storage = new ThreadLocal<>();

    /**
     * Method responsible for storing the data for current thread.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param auditorName The Auditor name
     */
    public void store(String auditorName) {

        storage.set(auditorName);
    }

    /**
     * Method responsible for removing the data for current thread.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     */
    public void remove() {

        storage.remove();
    }

    /**
     * Method responsible for retrieving the data for current thread.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @return The string representing the Auditor name that is realizing the operation (add / update /
     *         delete) for this thread
     */
    public String get() {

        return storage.get();
    }

}
