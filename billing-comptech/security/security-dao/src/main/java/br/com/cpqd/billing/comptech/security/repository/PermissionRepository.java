package br.com.cpqd.billing.comptech.security.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cpqd.billing.comptech.security.model.entity.Permission;
import br.com.cpqd.billing.comptech.security.model.entity.Profile;

/**
 * This interface represents a JPA Repository for {@link Permission}.
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 */
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    /**
     * Method responsible for getting all {@link Permission} through {@code key} attribute.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param setKey The list with all {@link Permission} {@code key} attribute values
     * @return A set with all {@link Permission} found or a new empty set
     */
    public Set<Permission> findByKeyIn(Set<String> setKey);

    /**
     * Method responsible for getting all {@link Permission} through {@code profile} attribute.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param setProfile The list with all {@link Permission} {@code profile} attribute values
     * @return A list with all {@link Permission} found or a new empty list
     */
    public List<Permission> findByProfileIn(Set<Profile> setProfile);

}
