package br.com.cpqd.billing.comptech.security.service.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import br.com.cpqd.billing.comptech.exception.core.environment.EnvironmentVariableNotFoundException;
import br.com.cpqd.billing.comptech.security.model.entity.enums.JWTConfigEnum;
import br.com.cpqd.billing.comptech.security.model.entity.enums.LdapConfigEnum;

/**
 * This class is a utility class for exposing methods to validate environment variables.
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 */
@Component
public class EnvValidateUtil {

    /**
     * Injection of dependency of {@link Environment}
     */
    @Autowired
    private Environment environment;

    /**
     * Injection of dependency of {@link MessageSource}
     */
    @Autowired
    private MessageSource messageSource;

    /**
     * Method responsible for validating the basic informations configured on application.
     * <p>
     * Each entry is in environment variables and are required. If one is not configured an exception is
     * thrown and the user's authentication is interrupted.
     * </p>
     * <p>
     * The exception thrown will show to user which entry is not configured properly.
     * </p>
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @throws EnvironmentVariableNotFoundException Exception thrown when some environment variable was not
     *             found
     */
    public void validateLdapEnvironmentVariables() throws EnvironmentVariableNotFoundException {
        
        for (LdapConfigEnum element : LdapConfigEnum.values()) {
            if (this.environment.getProperty(element.getKey()) == null) {
                throw new EnvironmentVariableNotFoundException(String.format(
                        this.messageSource.getMessage("comptech.security.component.configuration.attribute.null",
                                null, LocaleContextHolder.getLocale()),
                        element.getKey()));
            }
        }
    }

    /**
     * Method responsible for validating the basic informations configured on application.
     * <p>
     * Each entry is in environment variables and are required. If one is not configured an exception is
     * thrown and the user's authentication is interrupted.
     * </p>
     * <p>
     * The exception thrown will show to user which entry is not configured properly.
     * </p>
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @throws EnvironmentVariableNotFoundException Exception thrown when some environment variable was not
     *             found
     */
    public void validateJwtEnvironmentVariables() throws EnvironmentVariableNotFoundException {

        for (JWTConfigEnum element : JWTConfigEnum.values()) {
            if (this.environment.getProperty(element.getKey()) == null) {
                throw new EnvironmentVariableNotFoundException(String.format(
                        this.messageSource.getMessage("comptech.security.component.configuration.attribute.null",
                                null, LocaleContextHolder.getLocale()),
                        element.getKey()));
            }
        }
    }
}
