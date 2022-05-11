package br.com.cpqd.billing.comptech.security.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cpqd.billing.comptech.security.model.entity.User;
import br.com.cpqd.billing.comptech.security.model.entity.enums.StatusUserEnum;

/**
 * This interface represents a JPA Repository for {@link User}.
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Method responsible for getting the {@link User} through {@code login} attribute.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param login The {@link User} {@code login} attribute
     * @return An object of kind {@link Optional} containing the element that represents the business entity
     *         {@link User} that was found or an {@link Optional} empty when it was not found
     */
    public Optional<User> findByLogin(String login);

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
     * @param pageable The {@link Pageable} object. It is responsible for pagination
     * @return A list with all {@link User} found or a new empty list
     */
    public Page<User> findUserByLoginContainingIgnoreCaseAndNameContainingIgnoreCaseAndEmailContainingIgnoreCaseAndStatusUserEnum(
            String login, String name, String email, StatusUserEnum statusUserEnum, Pageable pageable);

}
