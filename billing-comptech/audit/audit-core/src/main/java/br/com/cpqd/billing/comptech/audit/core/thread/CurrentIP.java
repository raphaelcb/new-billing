package br.com.cpqd.billing.comptech.audit.core.thread;

/**
 * This class represents the implementation of the {@link ThreadLocal} for storing important data that will be
 * used on audit. These data are individually used for the current thread and will be retrieved on
 * appropriated moment.
 * <p>
 * This particular thread local will be store data involving the IP Address of the client machine.
 * </p>
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 */
public class CurrentIP {

    /**
     * Attributes that represents the {@link ThreadLocal} for storing the IP Address
     */
    public static final CurrentIP INSTANCE = new CurrentIP();
    private static final ThreadLocal<String> storage = new ThreadLocal<>();

    /**
     * Method responsible for storing the data for current thread.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param ipAddress The IP Address of the client machine
     */
    public void store(String ipAddress) {

        storage.set(ipAddress);
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
     * @return The string representing the IP Address for this current thread
     */
    public String get() {

        return storage.get();
    }

}
