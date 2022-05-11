package br.com.cpqd.billing.comptech.security.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cpqd.billing.comptech.security.model.contract.password.ReqChangePasswordContract;
import br.com.cpqd.billing.comptech.security.model.entity.ActivePassword;
import br.com.cpqd.billing.comptech.security.model.entity.InactivePassword;
import br.com.cpqd.billing.comptech.security.model.entity.User;
import br.com.cpqd.billing.comptech.security.service.exception.RestInvalidPasswordException;

/**
 * This class is responsible for Service layer of the application.
 * <p>
 * It contains the methods to manipulate the data for {@link ActivePassword} and {@link InactivePassword}.
 * <p>
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 */
@Service
@Transactional
public class ChangePasswordService {

    /**
     * Attribute that represents the regex for password
     */
    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%&*.;])[A-Za-z\\d!@#$%&*.;]{8,}$";

    /**
     * Injection of dependency of {@link UserService}
     */
    @Autowired
    private UserService userService;

    /**
     * Injection of dependency of {@link MessageSource}
     */
    @Autowired
    private MessageSource messageSource;

    /**
     * Method responsible for change the password of the {@link User} on application.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param reqChangePasswordContract The {@link ReqChangePasswordContract} object with password
     *            informations
     * @throws DataAccessException Exception thrown when some persistence error occurs
     */
    public void update(ReqChangePasswordContract reqChangePasswordContract) throws DataAccessException {

        // Getting the user through login
        var theUser = this.userService.findUserByLogin(reqChangePasswordContract.getLogin());

        // Call the method for validating the change password
        validateChangePassword(theUser, reqChangePasswordContract);

        // Encrypt the new password
        var newPassword = this.passwordEncoder().encode(reqChangePasswordContract.getNewPassword());

        // Change the current password to inactive
        var inactivePassword = new InactivePassword();
        inactivePassword.setPassword(theUser.getActivePassword().getPassword());
        inactivePassword.setUser(theUser);

        // Associate the history of passwords to user
        var setInactivePassword = theUser.getInactivePassword();
        setInactivePassword.add(inactivePassword);
        theUser.setInactivePassword(setInactivePassword);

        // Associate the new password like active to user
        theUser.getActivePassword().setPassword(newPassword);

        // Change the status of the user
        theUser.setFirstAccess(false);
       
        // Save the user with modified fields
        this.userService.updateUser(theUser);
    }

    /**
     * Method responsible for validating the change password before persistence.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param user The {@link User} object
     * @param reqChangePasswordContract The {@link ReqChangePasswordContract} object
     * @throws RestInvalidPasswordException Exception thrown when some error on password validation occurs
     */
    private void validateChangePassword(User user, ReqChangePasswordContract reqChangePasswordContract)
            throws RestInvalidPasswordException {

        // Validate if the current password is right
        if (!this.passwordEncoder().matches(reqChangePasswordContract.getCurrentPassword(),
                user.getActivePassword().getPassword())) {
            throw new RestInvalidPasswordException("current_password",
                    this.messageSource.getMessage(
                            "comptech.security.component.change.password.current.password.not.match", null,
                            LocaleContextHolder.getLocale()));

        }

        // Validate if the password and the password confirmation are equals
        if (!reqChangePasswordContract.getNewPassword()
                .equals(reqChangePasswordContract.getConfirmNewPassword())) {
            throw new RestInvalidPasswordException("confirm_new_password",
                    this.messageSource.getMessage(
                            "comptech.security.component.change.password.password.confirm.not.match", null,
                            LocaleContextHolder.getLocale()));

        }

        // Verify if the new password is equal the current password
        if (this.passwordEncoder().matches(reqChangePasswordContract.getNewPassword(),
                user.getActivePassword().getPassword())) {
            throw new RestInvalidPasswordException("new_password",
                    this.messageSource.getMessage(
                            "comptech.security.component.change.password.current.password.equal.new.password",
                            null, LocaleContextHolder.getLocale()));

        }

        // Verify if the new password is in password history
        for (InactivePassword element : user.getInactivePassword()) {
            if (this.passwordEncoder().matches(reqChangePasswordContract.getNewPassword(),
                    element.getPassword())) {
                throw new RestInvalidPasswordException("new_password", this.messageSource.getMessage(
                        "comptech.security.component.change.password.new.password.is.in.password.history",
                        null, LocaleContextHolder.getLocale()));

            }
        }

        // Verify if the new password has the right format
        if (!reqChangePasswordContract.getNewPassword().trim().matches(PASSWORD_REGEX)) {
            throw new RestInvalidPasswordException("new_password",
                    this.messageSource.getMessage("comptech.security.component.user.invalid.password", null,
                            LocaleContextHolder.getLocale()));
        }
    }

    /**
     * Method responsible for providing a {@link PasswordEncoder}.
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
