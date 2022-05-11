package br.com.cpqd.billing.comptech.security.service.auth;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import br.com.cpqd.billing.comptech.security.model.entity.AttemptsLogin;
import br.com.cpqd.billing.comptech.security.model.entity.Permission;
import br.com.cpqd.billing.comptech.security.model.entity.Profile;
import br.com.cpqd.billing.comptech.security.model.entity.User;
import br.com.cpqd.billing.comptech.security.model.entity.auth.UserSystem;
import br.com.cpqd.billing.comptech.security.service.AttemptsLoginService;
import br.com.cpqd.billing.comptech.security.service.PermissionService;
import br.com.cpqd.billing.comptech.security.service.UserService;
import lombok.extern.log4j.Log4j2;

/**
 * This class is responsible for realizing the loading of {@link User}, his {@link Permission} and make the
 * authentication.
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 * @see org.springframework.security.core.userdetails.UserDetailsService
 */
@Log4j2
@Component
public class SecurityUserDetailsService implements UserDetailsService {

    /**
     * Injection of dependency of {@link UserService}
     */
    @Autowired
    private UserService userService;

    /**
     * Injection of dependency of {@link PermissionService}
     */
    @Autowired
    private PermissionService permissionService;

    /**
     * Injection of dependency of {@link AttemptsLoginService}
     */
    @Autowired
    private AttemptsLoginService attemptsLoginService;

    /**
     * Injection of dependency of {@link HttpServletRequest}
     */
    @Autowired
    private HttpServletRequest httpServletRequest;

    /**
     * Injection of dependency of {@link MessageSource}
     */
    @Autowired
    private MessageSource messageSource;

    /**
     * (non-Javadoc)
     * 
     * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
     */
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        // Call the method for getting the user object by login
        var user = this.userService.findUserByLogin(login.toLowerCase());

        // Verify if is a valid user
        if (user == null) {
            // Call the method to persisting the attempt login
            persistAttemptLogin(login);

            log.error(
                    this.messageSource.getMessage("comptech.security.component.authentication.user.notfound",
                            null, LocaleContextHolder.getLocale()));
            throw new UsernameNotFoundException(
                    this.messageSource.getMessage("comptech.security.component.authentication.user.notfound",
                            null, LocaleContextHolder.getLocale()));
        }

        // Return a new instance for Spring user
        return new UserSystem(user.getLogin(), user.getActivePassword().getPassword(),
                authorities(user.getProfile()));
    }

    /**
     * Method responsible for persisting the attempt login for user. The attempt failure.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param login The user login
     * @throws DataAccessException Exception thrown when some persistence error occurs
     */
    private void persistAttemptLogin(String login) throws DataAccessException {

        // Retrieving user's terminal IP through HTTP header
        var remoteAddress = "";
        if (this.httpServletRequest != null) {
            remoteAddress = this.httpServletRequest.getHeader("X-FORWARDED-FOR");
            if ((remoteAddress == null) || ("".equals(remoteAddress))) {
                remoteAddress = this.httpServletRequest.getRemoteAddr();
            }
        }

        // Create the object for attempt login
        var attemptsLogin = new AttemptsLogin();
        attemptsLogin.setLogin(login);
        attemptsLogin.setTimestamp(Calendar.getInstance().getTime());
        attemptsLogin.setStatus(false);
        attemptsLogin.setIp(remoteAddress);

        // Call the method for updating the attempt of login
        this.attemptsLoginService.save(attemptsLogin);
    }

    /**
     * Method responsible for getting all permission from user loaded.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param profile The profile associated with user loaded
     * @return All permissions from user
     */
    public Collection<GrantedAuthority> authorities(Profile profile) {

        var authorities = new ArrayList<GrantedAuthority>();

        // Call the method for loading all permission associated with user profile
        var setProfile = new HashSet<Profile>();
        setProfile.add(profile);
        var lstPermission = this.permissionService.findByProfileIn(setProfile);

        // Run for each permission found and associate a name for it
        lstPermission
                .forEach(element -> authorities.add(new SimpleGrantedAuthority("ROLE_" + element.getKey())));

        // Return all permission found
        return authorities;
    }

}
