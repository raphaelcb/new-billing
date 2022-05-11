package br.com.cpqd.billing.comptech.security.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cpqd.billing.comptech.security.model.entity.Permission;
import br.com.cpqd.billing.comptech.security.model.entity.Profile;
import br.com.cpqd.billing.comptech.security.repository.PermissionRepository;

/**
 * This class is responsible for Service layer of the application.
 * <p>
 * It contains the methods to manipulate the data for {@link Permission}.
 * <p>
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 */
@Service
@Transactional
public class PermissionService {

    /**
     * Injection of dependency of {@link PermissionRepository}
     */
    @Autowired
    private PermissionRepository permissionRepository;

    /**
     * Method responsible for getting all {@link Permission}.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param page The desired page number to pagination
     * @param pageSize The desired page size to pagination
     * @return The list with all {@link Permission} found or a new empty list
     */
    public List<Permission> findAll(int page, int pageSize) {

        var pageable = PageRequest.of(page, pageSize);
        return this.permissionRepository.findAll(pageable).getContent();
    }

    /**
     * Method responsible for getting all {@link Permission} through {@code key} attribute.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param key The list with all {@link Permission} {@code key} attribute values
     * @return The set with all {@link Permission} found or a new empty list
     */
    public Set<Permission> findByKeyIn(Set<String> key) throws NoSuchElementException {

        return this.permissionRepository.findByKeyIn(key);
    }

    /**
     * Method responsible for getting all {@link Permission} through {@code profile} attribute.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param setProfile The list with all {@link Permission} {@code profile} attribute values
     * @return The list with all {@link Permission} found or a new empty list
     */
    public List<Permission> findByProfileIn(Set<Profile> setProfile) {

        return this.permissionRepository.findByProfileIn(setProfile);
    }

}
