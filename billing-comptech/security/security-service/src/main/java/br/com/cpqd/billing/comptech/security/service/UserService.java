package br.com.cpqd.billing.comptech.security.service;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.CaseFormat;

import br.com.cpqd.billing.comptech.exception.core.rest.RestObjectNotFoundException;
import br.com.cpqd.billing.comptech.security.model.entity.User;
import br.com.cpqd.billing.comptech.security.model.entity.enums.StatusUserEnum;
import br.com.cpqd.billing.comptech.security.repository.UserRepository;
import br.com.cpqd.billing.comptech.security.service.exception.RestInvalidPasswordException;
import lombok.extern.log4j.Log4j2;

/**
 * This class is responsible for Service layer of the application.
 * <p>
 * It contains the methods to manipulate the data for {@link User}.
 * <p>
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 */
@Log4j2
@Service
@Transactional
public class UserService {

    /**
     * Attribute that represents the regex for password
     */
    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%&*.;])[A-Za-z\\d!@#$%&*.;]{8,}$";

    /**
     * Injection of dependency of {@link ProfileService}
     */
    @Autowired
    private ProfileService profileService;

    /**
     * Injection of dependency of {@link UserRepository}
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Injection of dependency of {@link MessageSource}
     */
    @Autowired
    private MessageSource messageSource;

    /**
     * Method responsible for adding a new {@link User}.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param user The {@link User} object to persist
     * @return The {@link User} object persisted
     * @throws DataAccessException Exception thrown when some persistence error occurs
     */
    public User addUser(User user) throws DataAccessException {

        // Validate the password
        if (!user.getActivePassword().getPassword().trim().matches(PASSWORD_REGEX)) {
            log.error(this.messageSource.getMessage("comptech.security.component.user.invalid.password", null,
                    LocaleContextHolder.getLocale()));
            throw new RestInvalidPasswordException("password",
                    this.messageSource.getMessage("comptech.security.component.user.invalid.password", null,
                            LocaleContextHolder.getLocale()));
        }

        // Retrieving the profile and associated it to user
        user.setProfile(this.profileService.findProfileById(user.getProfile().getId()));

        // Set the user status
        user.setStatusUserEnum(StatusUserEnum.ACTIVE);

        // Set the first access to user
        user.setFirstAccess(true);

        // Encrypt the password
        user.getActivePassword()
                .setPassword(this.passwordEncoder().encode(user.getActivePassword().getPassword()));

        // Setting the initial attempts login
        user.setAttemptsLogin(0);

        // Preparing the acronym of the user
        user.setAcronym(Normalizer.normalize(user.getName(), Normalizer.Form.NFD));
        user.setAcronym(user.getAcronym().replaceAll("[^\\p{ASCII}]", ""));
        user.setAcronym(user.getAcronym().replace(" ", "_"));
        user.setAcronym(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, user.getAcronym()));

        // Save the user
        return this.userRepository.saveAndFlush(user);
    }

    /**
     * Method responsible for updating an {@link User}.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param user The {@link User} object to persist
     * @return The {@link User} object persisted
     * @throws DataAccessException Exception thrown when some persistence error occurs
     */
    public User updateUser(User user) throws DataAccessException {

        var theUser = this.findUserById(user.getId());
        BeanUtils.copyProperties(user, theUser, "id", "login", "acronym", "attemptsLogin", "firstAccess",
                "profile", "activePassword", "inactivePassword");

        // Save the user
        return this.userRepository.saveAndFlush(theUser);
    }

    /**
     * Method responsible for deleting an {@link User}.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param id The {@link User} {code id} attribute
     * @throws DataAccessException Exception thrown when some persistence error occurs
     */
    public void deleteUser(Long id) throws DataAccessException {

        // Looking for user
        var user = this.findUserById(id);

        // Set the user status
        user.setStatusUserEnum(StatusUserEnum.INACTIVE);

        // Save the user
        this.userRepository.saveAndFlush(user);
    }

    /**
     * Method responsible for getting the {@link User} through {@code id} attribute.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param id The {@link User} {@code id} attribute
     * @return The {@link User} found
     * @throws RestObjectNotFoundException Exception thrown if the user was not found
     */
    public User findUserById(Long id) {

        return this.userRepository.findById(id)
                .orElseThrow(() -> new RestObjectNotFoundException(this.messageSource.getMessage(
                        "comptech.security.component.authentication.user.notfound", null,
                        LocaleContextHolder.getLocale())));
    }

    /**
     * Method responsible for getting the {@link User} through {@code login} attribute.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param login The {@link User} {@code login} attribute
     * @return The {@link User} found
     * @throws RestObjectNotFoundException Exception thrown if the user was not found
     */
    public User findUserByLogin(String login) {

        return this.userRepository.findByLogin(login)
                .orElseThrow(() -> new RestObjectNotFoundException(this.messageSource.getMessage(
                        "comptech.security.component.authentication.user.notfound", null,
                        LocaleContextHolder.getLocale())));
    }

    /**
     * Method responsible for getting all {@link User} through parameters.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param login The {@link User} {@code login} attribute
     * @param name The {@link User} {@code name} attribute
     * @param email The {@link User} {@code email} attribute
     * @param statusUserEnum The {@link User} {@code statusUserEnum} attribute. It is associated with
     *            enumeration {@code StatusUserEnum}
     * @param page The desired page number to pagination
     * @param pageSize The desired page size to pagination
     * @return The list with all {@link User} found and ordered by your name ASC
     * @throws RestObjectNotFoundException Exception thrown if there are not users to return
     */
    public List<User> findUserByLoginAndNameAndEmailAndStatusUserEnum(String login, String name, String email,
            StatusUserEnum statusUserEnum, int page, int pageSize) {

        // Validate and treat the input parameters
        login = (login == null) ? "" : login;
        name = (name == null) ? "" : name;
        email = (email == null) ? "" : email;
        statusUserEnum = (statusUserEnum == null) ? StatusUserEnum.ACTIVE : statusUserEnum;

        // Create the pageable object and call the find method
        var sortedByName = PageRequest.of(page, pageSize, Sort.by("name").ascending());
        var lstUser = this.userRepository
                .findUserByLoginContainingIgnoreCaseAndNameContainingIgnoreCaseAndEmailContainingIgnoreCaseAndStatusUserEnum(
                        login, name, email, statusUserEnum, sortedByName)
                .getContent();

        // Verify if there are results
        if (lstUser.isEmpty()) {
            throw new RestObjectNotFoundException(this.messageSource.getMessage("global.no.data.found", null,
                    LocaleContextHolder.getLocale()));
        }

        // Return the result
        return lstUser;
    }

    /**
     * Method responsible for providing a {@link PasswordEncoder} to user authentication in the application.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @return The {@link PasswordEncoder}
     */
    private PasswordEncoder passwordEncoder() {

        var encodingId = "bcrypt";
        var encoders = new HashMap<String, PasswordEncoder>();
        encoders.put(encodingId, new BCryptPasswordEncoder());
        encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
        encoders.put("scrypt", new SCryptPasswordEncoder());

        var passwordEncoder = new DelegatingPasswordEncoder(encodingId, encoders);
        passwordEncoder.setDefaultPasswordEncoderForMatches(new BCryptPasswordEncoder());
        return passwordEncoder;
    }

}
