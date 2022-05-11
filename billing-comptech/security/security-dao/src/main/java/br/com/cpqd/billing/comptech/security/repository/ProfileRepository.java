package br.com.cpqd.billing.comptech.security.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cpqd.billing.comptech.commons.entities.model.entity.enums.StatusEnum;
import br.com.cpqd.billing.comptech.security.model.entity.Profile;

/**
 * This interface represents a JPA Repository for {@link Profile}.
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 */
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    /**
     * Method responsible for getting all {@link Profile} through {@code status} attribute.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param statusEnum The {@link StatusEnum} values available
     * @return A list with all {@link Profile} found or a new empty list
     */
    public List<Profile> findByStatusEnumAndVisibleTrueOrderByNameAsc(StatusEnum statusEnum);

}
