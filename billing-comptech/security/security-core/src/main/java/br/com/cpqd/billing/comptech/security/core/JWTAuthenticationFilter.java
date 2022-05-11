package br.com.cpqd.billing.comptech.security.core;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.cpqd.billing.comptech.security.model.entity.enums.JWTConfigEnum;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.extern.log4j.Log4j2;

/**
 * This class represents a filter responsible to perform the authentication/authorization of the user through
 * provided JWT token and release the access for other entry points on application.
 * <p>
 * The class will be called before of the execution of the Spring Security internal
 * {@link UsernamePasswordAuthenticationFilter} for each entry point of the application.
 * </p>
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 * @see org.springframework.web.filter.OncePerRequestFilter
 */
@Log4j2
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    /**
     * Attribute that represents the attribute Authorization on header of the request
     */
    private static final String HEADER_STRING = "Authorization";

    /**
     * Attribute the represents the token prefix for JWT Token
     */
    private static final String TOKEN_PREFIX = "Bearer";

    /**
     * Attribute used on error messages
     */
    private static final String EXCEPTION_STRING = " Exception: ";

    /**
     * Attribute that represents an instance of {@link Environment}
     */
    private Environment env;

    /**
     * Attribute that represents an instance of {@link MessageSource}
     */
    private MessageSource messageSource;

    /**
     * Constructor
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param applicationContext The Spring application context
     * @param messageSource The {@link MessageSource} object
     */
    public JWTAuthenticationFilter(ApplicationContext applicationContext, MessageSource messageSource) {

        this.env = applicationContext.getEnvironment();
        this.messageSource = messageSource;
    }

    /**
     * @see OncePerRequestFilter#doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        // Get authorization header and validate
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if ((header == null) || (!header.startsWith(TOKEN_PREFIX))) {
            filterChain.doFilter(request, response);
            return;
        }

        // Get JWT token and validate
        var authentication = decodeJWT(request);
        if (authentication == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // JWT token valid, so update the current user authenticated and perform the action
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    /**
     * Method responsible for decoding the JWT Token.
     * <p>
     * The JWT Token must be present in header of the entry point with Authorization attribute.
     * </p>
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param request The {@link HttpServletRequest} object
     * @return The {@link Authentication} object with user's credential
     * @throws ExpiredJwtException Exception thrown when the JWT token was accepted after it expired and must
     *             be rejected
     * @throws MalformedJwtException Exception thrown when the JWT token was not correctly constructed and
     *             should be rejected
     * @throws SignatureException Exception indicating that either calculating a signature or verifying an
     *             existing signature of a JWT token failed
     */
    public Authentication decodeJWT(HttpServletRequest request)
            throws ExpiredJwtException, MalformedJwtException, SignatureException {

        try {
            var token = request.getHeader(HEADER_STRING);
            var secret = this.env.getProperty(JWTConfigEnum.VAR_CONFIG_ISSUER_SECRET.getKey());
            if (secret != null) {
                var claims = Jwts.parser().setSigningKey(secret.getBytes())
                        .parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody();
                Collection<? extends GrantedAuthority> authorities = Arrays
                        .stream(claims.get("scopes").toString().split(",")).map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
                var user = claims.getIssuer();
                if (user != null) {
                    return new UsernamePasswordAuthenticationToken(user, null, authorities);
                }
            }
        } catch (ExpiredJwtException e) {
            log.error(this.messageSource.getMessage("comptech.security.component.jwt.token.expired", null,
                    LocaleContextHolder.getLocale()) + EXCEPTION_STRING + e.getMessage());
        } catch (MalformedJwtException e) {
            log.error(this.messageSource.getMessage("comptech.security.component.jwt.token.malformed", null,
                    LocaleContextHolder.getLocale()) + EXCEPTION_STRING + e.getMessage());
        } catch (SignatureException e) {
            log.error(this.messageSource.getMessage("comptech.security.component.jwt.token.invalid.signature",
                    null, LocaleContextHolder.getLocale()) + EXCEPTION_STRING + e.getMessage());
        }

        return null;
    }

}
