package br.com.cpqd.billing.comptech.security.model.contract.permission;

/**
 * This class represents all permission constants for security component.
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 */
public class SecurityPermissionConstants {

    // Monitoring
    public static final String ROLE_MONITORING = "ROLE_MONITORING";

    // Profile
    public static final String ROLE_ADD_PROFILE = "ROLE_ADD_PROFILE";
    public static final String ROLE_UPDATE_PROFILE = "ROLE_UPDATE_PROFILE";
    public static final String ROLE_DELETE_PROFILE = "ROLE_DELETE_PROFILE";
    public static final String ROLE_SEARCH_PROFILE = "ROLE_SEARCH_PROFILE";
    public static final String ROLE_VIEW_AUDIT_PROFILE = "ROLE_VIEW_AUDIT_PROFILE";

    // User
    public static final String ROLE_ADD_USER = "ROLE_ADD_USER";
    public static final String ROLE_UPDATE_USER = "ROLE_UPDATE_USER";
    public static final String ROLE_DELETE_USER = "ROLE_DELETE_USER";
    public static final String ROLE_SEARCH_USER = "ROLE_SEARCH_USER";
    public static final String ROLE_VIEW_AUDIT_USER = "ROLE_VIEW_AUDIT_USER";
    
    // Permission
    public static final String ROLE_SEARCH_PERMISSION = "ROLE_SEARCH_PERMISSION";

    /**
     * Private constructor
     */
    private SecurityPermissionConstants() {

        // Private constructor
    }

}
