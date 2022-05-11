package br.com.cpqd.billing.comptech.security.service.auth;

import java.util.Calendar;
import java.util.HashMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import br.com.cpqd.billing.comptech.exception.core.environment.EnvironmentVariableNotFoundException;
import br.com.cpqd.billing.comptech.security.model.contract.auth.RespAuthentication;
import br.com.cpqd.billing.comptech.security.service.util.EnvValidateUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * This class is responsible for Service layer of the application.
 * <p>
 * It contains the methods to manipulate the data for JWT Token.
 * <p>
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 */
@Service
public class JwtService {

    /**
     * Injection of dependency of {@link EnvValidateUtil}
     */
    @Autowired
    private EnvValidateUtil envValidateUtil;

    /**
     * Attributes that represents the JWT basic informations to create the token. The JWT issuer and secret
     * are the values of the Kong API Gateway consumer
     */
    @Value("#{systemProperties['jwt_issuer']}")
    private String jwtIssuer;
    @Value("#{systemProperties['jwt_issuer_secret']}")
    private String jwtIssuerSecret;
    @Value("#{systemProperties['jwt_token_expiration']}")
    private Integer jwtTokenExpiration;

    /**
     * Method responsible for creating a new JWT Token.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param respAuthentication The {@link RespAuthentication} object
     * @return The JWT Token created
     * @throws EnvironmentVariableNotFoundException Exception thrown when some environment variable was not
     *             found
     */
    public String createJWT(RespAuthentication respAuthentication)
            throws EnvironmentVariableNotFoundException {

        // Verify if the basic elements to JWT are valid
        this.envValidateUtil.validateJwtEnvironmentVariables();

        // The JWT signature algorithm we will be using to sign the token
        var signatureAlgorithm = SignatureAlgorithm.HS256;

        // Getting the current date/time and increment with value of expiration
        var theCalendar = Calendar.getInstance();
        var now = theCalendar.getTime();
        theCalendar.add(Calendar.SECOND, this.jwtTokenExpiration.intValue());
        var exp = theCalendar.getTime();

        var claims = new HashMap<String, Object>();
        claims.put("scopes", respAuthentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(",")));

        // Set the JWT claims
        var builder = Jwts.builder().setNotBefore(now).setIssuedAt(now).setClaims(claims).setExpiration(exp)
                .setIssuer(this.jwtIssuer).signWith(signatureAlgorithm, this.jwtIssuerSecret.getBytes());

        // Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }

}
