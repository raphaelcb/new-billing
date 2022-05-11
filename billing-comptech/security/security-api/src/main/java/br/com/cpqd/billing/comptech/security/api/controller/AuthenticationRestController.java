package br.com.cpqd.billing.comptech.security.api.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.cpqd.billing.comptech.exception.core.environment.EnvironmentVariableNotFoundException;
import br.com.cpqd.billing.comptech.security.api.config.SecurityWebConfig;
import br.com.cpqd.billing.comptech.security.model.contract.auth.ReqAuthentication;
import br.com.cpqd.billing.comptech.security.model.contract.auth.RespAuthentication;
import br.com.cpqd.billing.comptech.security.model.entity.Profile;
import br.com.cpqd.billing.comptech.security.service.PermissionService;
import br.com.cpqd.billing.comptech.security.service.UserService;
import br.com.cpqd.billing.comptech.security.service.auth.JwtService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;

/**
 * This class represents a REST Controller and is responsible for exposing security entry points for
 * authentication of the users on application.
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 */
@Log4j2
@Api(value = "AuthenticationRestController", 
     consumes = "application/json", 
     produces = "application/json", 
     protocols = "http,https", 
     tags = "Autenticação")
@RestController
@Validated
public class AuthenticationRestController {

    /**
     * Attribute the represents the token prefix for JWT Token
     */
    private static final String TOKEN_PREFIX = "Bearer";

    /**
     * Injection of dependency of {@link JwtService}
     */
    @Autowired
    private JwtService jwtService;

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
     * Injection of dependency of {@link AuthenticationManager}.
     * <p>
     * The {@link AuthenticationManager} is exposed through definition on {@link SecurityWebConfig} class.
     * </p>
     */
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * Injection of dependency of {@link MessageSource}
     */
    @Autowired
    private MessageSource messageSource;

    /**
     * Method responsible for exposing an entry point for authenticate the user on application and generate a
     * new JWT token to use on each entry point that need of security.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param reqAuthentication The {@link ReqAuthentication} object
     * @return The {@link HttpStatus} and the JWT token when the authentication passed
     */
    @ApiOperation(value = "Autenticar usuários", 
                  notes = "Autenticação de um usuário na aplicação e geração de um token JWT com base nas configurações de segurança da aplicação. O token têm um tempo máximo de uso e deve ser renovado quando expirado", 
                  response = RespAuthentication.class)
    @PostMapping(path = "/api/v0/public/auth", consumes = "application/json", produces = "application/json")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<Object> auth(@Valid @RequestBody ReqAuthentication reqAuthentication) {

        try {
            // Perform the attempt to authenticate the user
            var authentication = this.authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(reqAuthentication.getUsername(),
                            reqAuthentication.getPassword()));

            /*
             * With user successfully authenticated, call the method for getting the current user and retrieve
             * some informations and call the method for creating the JWT Token to this session. This token
             * must be passed on each entry point for access other entry points on application
             */
            var respAuthentication = new RespAuthentication();
            var user = this.userService.findUserByLogin(authentication.getName());
            respAuthentication.setName(user.getName());
            respAuthentication.setFirstAccess(user.isFirstAccess());
            respAuthentication.setAuthorities(
                    ((authentication.getAuthorities() == null) || (authentication.getAuthorities().isEmpty()))
                            ? authorities(user.getProfile())
                            : authentication.getAuthorities());
            respAuthentication
                    .setAuthorization(TOKEN_PREFIX + " " + this.jwtService.createJWT(respAuthentication));
            return new ResponseEntity<>(respAuthentication, null, HttpStatus.CREATED);
        } catch (BadCredentialsException e) {
            log.error(this.messageSource.getMessage(
                    "comptech.security.component.authentication.user.bad.credentials", null,
                    LocaleContextHolder.getLocale()) + " - Exception: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (EnvironmentVariableNotFoundException e) {
            log.error(this.messageSource.getMessage("comptech.security.component.jwt.invalid", null,
                    LocaleContextHolder.getLocale()) + " - Exception: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (NoSuchElementException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            log.error(this.messageSource.getMessage(
                    "comptech.security.component.authentication.user.bad.credentials", null,
                    LocaleContextHolder.getLocale()) + " - Exception: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    /**
     * Method responsible for getting all permission from user loaded.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param profile The profile associated with user loaded
     * @return All permissions from user
     */
    private Collection<? extends GrantedAuthority> authorities(Profile profile) {

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
