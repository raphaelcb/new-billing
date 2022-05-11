package br.com.cpqd.billing.comptech.security.model.entity.auth;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.EqualsAndHashCode;

/**
 * This class represents an {@link User} in Spring context.
 * <p>
 * It is used to authenticate the user on the application and get the authorities (profiles and permissions)
 * associated with current user.
 * </p>
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 * @see org.springframework.security.core.userdetails.User
 */
@SuppressWarnings("serial")
@EqualsAndHashCode(callSuper = false)
public class UserSystem extends User {

    /**
     * Constructor
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param name The full username
     * @param password The password
     * @param authorities The authorities found for the user
     * @return The {@link User} object
     */
    public UserSystem(String name, String password, Collection<? extends GrantedAuthority> authorities) {

        super(name, password, authorities);
    }

}
