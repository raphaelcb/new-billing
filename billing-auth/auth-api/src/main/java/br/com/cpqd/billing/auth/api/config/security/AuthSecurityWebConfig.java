package br.com.cpqd.billing.auth.api.config.security;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.cpqd.billing.comptech.security.core.JWTAuthenticationFilter;

/**
 * This class is responsible for configuring the security for URL's and resources of the application.
 * <p>
 * The class use the annotation {@code @Order} because is protecting the URL's of this application and has a
 * super class where the other security configurations are defined. In this way the class extends
 * {@link WebSecurityConfigurerAdapter} and override the method {@code configure} to protect the specific
 * URL's of this application. For this class the {@link Order} is highest precedence.
 * </p>
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
 */
@EnableWebSecurity
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AuthSecurityWebConfig extends WebSecurityConfigurerAdapter {

    /**
     * Injection of dependency of {@link MessageSource}
     */
    @Autowired
    private MessageSource messageSource;
    
    /**
     * (non-Javadoc)
     * 
     * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.web.builders.HttpSecurity)
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // Enable CORS and disable CSRF
        http.cors().and().csrf().disable();
        
        // Set session management to stateless
        http = http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and();

        // Set unauthorized requests exception handler
        http = http.exceptionHandling().authenticationEntryPoint((request, response, ex) -> {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
        }).and();

        // Permit access to /login path and for other paths where the authentication is required
        http.authorizeRequests()
            .antMatchers(HttpMethod.POST, "/api/v0/public/**").permitAll()
            .antMatchers(HttpMethod.GET, "/api/v0/public/**").permitAll()
            .antMatchers(HttpMethod.PUT, "/api/v0/public/**").permitAll()
            .antMatchers("/actuator/**").hasRole("MONITORING")
            .anyRequest()
                .authenticated()
            .and();

        // Add a filter to validate the authentication on each entry point available on application
        http.addFilterBefore(new JWTAuthenticationFilter(getApplicationContext(), this.messageSource),
                UsernamePasswordAuthenticationFilter.class);
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

}
