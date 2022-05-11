package br.com.cpqd.billing.comptech.security.api.config;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.context.request.RequestContextListener;

import br.com.cpqd.billing.comptech.exception.core.environment.EnvironmentVariableNotFoundException;
import br.com.cpqd.billing.comptech.security.api.listener.AuthenticationEventListener;
import br.com.cpqd.billing.comptech.security.service.auth.SecurityUserDetailsService;
import br.com.cpqd.billing.comptech.security.service.util.EnvValidateUtil;
import lombok.extern.log4j.Log4j2;

/**
 * This class is responsible for configuring the security for URL's and resources of the application.
 * <p>
 * For this component the authentication can be made through database or LDAP. There is the possibility of
 * generation JWT Token and integration with API Gateway like Kong.
 * </p>
 * <p>
 * The resources are mapped to each role and the user only will access the resource if has permission. If he
 * hasn't access the application will show an unauthorized error code to user.
 * </p>
 * <p>
 * The class use the annotation {@code @EnabledGlobalMethodSecurity} to perform the role validation on each
 * entry point available on application. With this annotation is possible to use regular expression to
 * validate the roles.
 * </p>
 * <p>
 * The class use too the annotation {@code @Order} because is protecting only the common URL's of the
 * component. When some application use this component it must implement an another class that extends
 * {@link WebSecurityConfigurerAdapter} and override the method {@code configure} to protect the specific
 * URL's of the application. For this class the {@link Order} is 1.
 * </p>
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 */
@Log4j2
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // Enable the security validation (permission) above each
                                                   // desired method
@Order(1)
public class SecurityWebConfig extends WebSecurityConfigurerAdapter {

    /**
     * Attributes that represents the environment variables to LDAP authentication
     */
    @Value("#{systemProperties['ldap_searchBase']}")
    private String ldapSearchBase;
    @Value("#{systemProperties['ldap_searchFilter']}")
    private String ldapSearchFilter;
    @Value("#{systemProperties['ldap_groupSearchBase']}")
    private String ldapGroupSearchBase;
    @Value("#{systemProperties['ldap_groupSearchFilter']}")
    private String ldapGroupSearchFilter;
    @Value("#{systemProperties['ldap_url']}")
    private String ldapUrl;
    @Value("#{systemProperties['ldap_port']}")
    private String ldapPort;
    @Value("#{systemProperties['ldap_managerDN']}")
    private String ldapManagerDN;
    @Value("#{systemProperties['ldap_managerPassword']}")
    private String ldapManagerPassword;

    /**
     * Attribute that represents the authentication method on application
     */
    @Value("#{systemProperties['auth_method']}")
    private String authMethod;
    private static final String AUTH_METHOD_LDAP = "ldap";
    private static final String AUTH_METHOD_DATABASE = "database";

    /**
     * Injection of dependency of {@link SecurityUserDetailsService}
     */
    @Autowired
    private SecurityUserDetailsService securityUserDetailsService;

    /**
     * Injection of dependency of {@link AuthenticationEventListener}
     */
    @Autowired
    private AuthenticationEventListener authenticationEventListener;

    /**
     * Injection of dependency of {@link MessageSource}
     */
    @Autowired
    private MessageSource messageSource;

    /**
     * Injection of dependency of {@link EnvValidateUtil}
     */
    @Autowired
    private EnvValidateUtil envValidateUtil;

    /**
     * (non-Javadoc)
     * 
     * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder)
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        // Verify if the authentication method is valid
        if (this.authMethod == null) {
            log.error(this.messageSource.getMessage(
                    "comptech.security.component.configuration.authentication.mode.not.configured", null,
                    LocaleContextHolder.getLocale()));
            throw new EnvironmentVariableNotFoundException(this.messageSource.getMessage(
                    "comptech.security.component.configuration.authentication.mode.not.configured", null,
                    LocaleContextHolder.getLocale()));
        }

        // Verify the authentication method to redirect to suitable method
        if (this.authMethod.equalsIgnoreCase(AUTH_METHOD_LDAP)) {
            configureLdapAuthMethod(auth);
        } else if (this.authMethod.equalsIgnoreCase(AUTH_METHOD_DATABASE)) {
            configureDatabaseAuthMethod(auth);
        } else {
            log.error(this.messageSource.getMessage(
                    "comptech.security.component.configuration.authentication.mode.invalid", null,
                    LocaleContextHolder.getLocale()));
            throw new EnvironmentVariableNotFoundException(this.messageSource.getMessage(
                    "comptech.security.component.configuration.authentication.mode.invalid", null,
                    LocaleContextHolder.getLocale()));
        }
    }

    /**
     * Method responsible for making the authentication through LDAP method.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param auth The {@link AuthenticationManagerBuilder} object
     * @throws Exception Exception thrown when some error occurs
     */
    protected void configureLdapAuthMethod(AuthenticationManagerBuilder auth) throws Exception {

        // Verify if the basic elements to LDAP are valid
        this.envValidateUtil.validateLdapEnvironmentVariables();

        // Authenticate the user
//        auth.ldapAuthentication()
//                .userSearchBase(this.ldapSearchBase)
//                .userSearchFilter(this.ldapSearchFilter)
//                .groupSearchBase(this.ldapGroupSearchBase)
//                .groupSearchFilter(this.ldapGroupSearchFilter)
//                .contextSource()
//                    .url(this.ldapUrl)
//                    .port(Integer.parseInt(ldapPort))
//                    .managerDn(this.ldapManagerDN)
//                    .managerPassword(this.ldapManagerPassword);
    }

    /**
     * Method responsible for making the authentication through database method.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param auth The {@link AuthenticationManagerBuilder} object
     * @throws Exception Exception thrown when some error occurs
     */
    protected void configureDatabaseAuthMethod(AuthenticationManagerBuilder auth) throws Exception {

        // Authentication by UserDetailsService (Spring component)
        auth.userDetailsService(this.securityUserDetailsService).passwordEncoder(this.passwordEncoder());

        // Registering the Authentication Event Listener
        auth.authenticationEventPublisher(this.authenticationEventListener);
    }

    /**
     * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(WebSecurity)
     */
    @Override
    public void configure(WebSecurity web) throws Exception {

        web.ignoring().antMatchers("/h2-console/**", 
                                   "/v2/api-docs/**", 
                                   "/v3/api-docs/**", 
                                   "/configuration/ui", 
                                   "/swagger-resources/**", 
                                   "/swagger-ui/index.html", 
                                   "/swagger-ui/**", 
                                   "/webjars/**");
    }

    /**
     * Method responsible for providing a {@link PasswordEncoder}.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @return The {@link PasswordEncoder}
     */
    @Bean
    public PasswordEncoder passwordEncoder() {

        var encodingId = "bcrypt";
        var encoders = new HashMap<String, PasswordEncoder>();
        encoders.put(encodingId, new BCryptPasswordEncoder());
        encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
        encoders.put("scrypt", new SCryptPasswordEncoder());

        var passwordEncoder = new DelegatingPasswordEncoder(encodingId, encoders);
        passwordEncoder.setDefaultPasswordEncoderForMatches(new BCryptPasswordEncoder());
        return passwordEncoder;
    }

    /**
     * Method responsible for defining a new instance to {@link AuthenticationManager}.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @return A new instance of {@link AuthenticationManager}
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {

        return super.authenticationManagerBean();
    }

    /**
     * Method responsible for defining a new instance to {@link RequestContextListener}.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @return A new instance of {@link RequestContextListener}
     */
    @Bean
    public RequestContextListener requestContextListener() {

        return new RequestContextListener();
    }

    /**
     * Method responsible for defining a new instance to {@link HttpSessionEventPublisher}.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @return A new instance of {@link HttpSessionEventPublisher}
     */
    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {

        return new HttpSessionEventPublisher();
    }

}
