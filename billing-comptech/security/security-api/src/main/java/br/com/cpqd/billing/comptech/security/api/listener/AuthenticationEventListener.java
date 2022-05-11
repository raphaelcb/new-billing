package br.com.cpqd.billing.comptech.security.api.listener;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import br.com.cpqd.billing.comptech.audit.core.thread.CurrentAuditor;
import br.com.cpqd.billing.comptech.audit.core.thread.CurrentIP;
import br.com.cpqd.billing.comptech.security.model.entity.AttemptsLogin;
import br.com.cpqd.billing.comptech.security.model.entity.Profile;
import br.com.cpqd.billing.comptech.security.model.entity.User;
import br.com.cpqd.billing.comptech.security.model.entity.enums.StatusUserEnum;
import br.com.cpqd.billing.comptech.security.service.AttemptsLoginService;
import br.com.cpqd.billing.comptech.security.service.UserService;
import lombok.extern.log4j.Log4j2;

/**
 * This class is responsible for controlling and updating the attempts of login for all users.
 * <p>
 * When the login was made successfully the attempts of login is reseted, otherwise is incremented and the
 * user can be block if the number of attempts is reached. The number of attempts is defined as environment
 * variable.
 * </p>
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 * @see org.springframework.security.authentication.AuthenticationEventPublisher
 */
@Log4j2
@Component
public class AuthenticationEventListener implements AuthenticationEventPublisher {

    /**
     * Injection of dependency of {@link UserService}
     */
    @Autowired
    private UserService userService;

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
     * Attribute that represents the number of attempts to authentication. If the variable isn't configured so
     * the default value is used
     */
    @Value("#{systemProperties['auth.attempts'] ?: '3'}")
    private Integer authAttemptsLogin;

    /**
     * Method responsible for reseting the attempts of login when the authentication process is made
     * successfully.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     */
    @Override
    public void publishAuthenticationSuccess(Authentication authentication) {

        // Call the method for getting the user and perform the validations
        var theUser = doCommonWork(authentication);

        // Reset the number of attempts login
        theUser.setAttemptsLogin(0);

        // Call the method to persisting the attempt login
        persistAttemptLogin(theUser, true);

        // Call the method for updating the user with a new number of attempts login
        this.userService.updateUser(theUser);
    }

    /**
     * Method responsible for incrementing the attempts of login when the authentication process fail.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     */
    @Override
    public void publishAuthenticationFailure(AuthenticationException exception,
            Authentication authentication) {

        // Call the method for getting the user and perform the validations
        var theUser = doCommonWork(authentication);

        // Increment the number of attempts login
        theUser.setAttemptsLogin(theUser.getAttemptsLogin() + 1);

        // Verify if the attempts login is in maximum
        if ((theUser.getAttemptsLogin().equals(this.authAttemptsLogin))
                && (theUser.getProfile().getId() != Profile.SUPER_USER.longValue())) {
            // User must be blocked
            theUser.setStatusUserEnum(StatusUserEnum.BLOCKED);
        }

        // Call the method to persisting the attempt login
        persistAttemptLogin(theUser, false);

        // Call the method for updating the user with a new number of attempts login
        this.userService.updateUser(theUser);
    }

    /**
     * Method responsible for retrieving the current user and perform your validation.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param authentication The {@link Authentication} object
     * @return The {@link User} object with all your informations
     * @throws UsernameNotFoundException Exception thrown when an user was not found
     * @throws DisabledException Exception thrown when some validate error occurs
     */
    private User doCommonWork(Authentication authentication)
            throws UsernameNotFoundException, DisabledException {

        // Getting the information that the user filled in form
        var login = authentication.getName();

        // Call the method for finding the user with all his informations
        var theUser = this.userService.findUserByLogin(login);

        // Call the method for validating the user
        validateUser(theUser);

        // Return the object
        return theUser;
    }

    /**
     * Method responsible for validating the {@link User} in various situations.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param theUser The {@link User} object
     * @throws UsernameNotFoundException Exception thrown when an user was not found
     * @throws DisabledException Exception thrown when some validate error occurs
     */
    private void validateUser(User theUser) throws UsernameNotFoundException, DisabledException {

        // Verify if is a valid user
        if (theUser == null) {
            log.error(
                    this.messageSource.getMessage("comptech.security.component.authentication.user.notfound",
                            null, LocaleContextHolder.getLocale()));
            throw new UsernameNotFoundException(
                    this.messageSource.getMessage("comptech.security.component.authentication.user.notfound",
                            null, LocaleContextHolder.getLocale()));
        }

        // Verify if the user is blocked
        if (theUser.getStatusUserEnum() == StatusUserEnum.BLOCKED) {
            // Call the method to persisting the attempt login
            persistAttemptLogin(theUser, false);

            log.error(this.messageSource.getMessage("comptech.security.component.authentication.user.blocked",
                    null, LocaleContextHolder.getLocale()));
            throw new DisabledException(
                    this.messageSource.getMessage("comptech.security.component.authentication.user.blocked",
                            null, LocaleContextHolder.getLocale()));
        }

        // Verify if the user is inactive
        if (theUser.getStatusUserEnum() == StatusUserEnum.INACTIVE) {
            // Call the method to persisting the attempt login
            persistAttemptLogin(theUser, false);

            log.error(
                    this.messageSource.getMessage("comptech.security.component.authentication.user.inactive",
                            null, LocaleContextHolder.getLocale()));
            throw new DisabledException(
                    this.messageSource.getMessage("comptech.security.component.authentication.user.inactive",
                            null, LocaleContextHolder.getLocale()));
        }
    }

    /**
     * Method responsible for persisting the attempt login for {@link User}. The attempt failure.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param theUser The {@link User} object
     * @param status The status for attempt login
     * @throws DataAccessException Exception thrown when some persistence error occurs
     */
    private void persistAttemptLogin(User theUser, boolean status) throws DataAccessException {

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
        attemptsLogin.setTimestamp(Calendar.getInstance().getTime());
        attemptsLogin.setStatus(status);
        attemptsLogin.setIp(remoteAddress);

        if ((theUser == null) || (theUser.getLogin() == null) || (theUser.getLogin().trim().equals(""))) {
            attemptsLogin.setLogin("");
        } else {
            attemptsLogin.setLogin(theUser.getLogin());
        }

        // Audit informations
        CurrentAuditor.INSTANCE.store(attemptsLogin.getLogin());
        CurrentIP.INSTANCE.store(remoteAddress);

        // Call the method for updating the attempt of login
        this.attemptsLoginService.save(attemptsLogin);
    }

}
