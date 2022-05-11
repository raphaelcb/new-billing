package br.com.cpqd.billing.comptech.security.model.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.cpqd.billing.comptech.security.model.TrimConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * This class represents a model for {@link Permission} entity.
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 */
@Entity
@Table(name = "PERMISSION", uniqueConstraints = {
        @UniqueConstraint(name = "uk_permission_key", columnNames = {"key"}),
        @UniqueConstraint(name = "uk_permission_description", columnNames = {"description"})
        })
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@SuppressWarnings("serial")
public class Permission implements Serializable {

    /**
     * Attribute that represents the internal identifier for the entity
     */
    @EqualsAndHashCode.Include
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Attribute that represents the key
     */
    @EqualsAndHashCode.Include
    @Convert(converter = TrimConverter.class)
    @Column(nullable = false, length = 150)
    private String key;

    /**
     * Attribute that represents the description
     */
    @EqualsAndHashCode.Include
    @Convert(converter = TrimConverter.class)
    @Column(nullable = false, length = 255)
    private String description;

    /**
     * Attribute that represents the relationship between {@link Permission} and {@link Profile}. The
     * relationship is many to many, so we have to provide the name of the field which maps the relationship.
     * The relationship owner is {@link Profile}
     */
    @ManyToMany(mappedBy = "permission")
    private Set<Profile> profile;

    // ========================================================
    // GETTERS AND SETTERS
    // ========================================================

    /**
     * Method responsible for returning the own object.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @return The {@link Permission} object
     */
    @JsonIgnore
    public Permission getThisObject() {

        return this;
    }

}
