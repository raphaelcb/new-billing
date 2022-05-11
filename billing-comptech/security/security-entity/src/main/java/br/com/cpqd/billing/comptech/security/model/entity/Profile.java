package br.com.cpqd.billing.comptech.security.model.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.cpqd.billing.comptech.commons.entities.model.entity.enums.StatusEnum;
import br.com.cpqd.billing.comptech.security.model.TrimConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * This class represents a model for {@link Profile} entity.
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 */
@Entity
@Table(name = "PROFILE", uniqueConstraints = {
        @UniqueConstraint(name = "uk_profile_name", columnNames = {"name"})
        })
@Audited(withModifiedFlag = true)
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@SuppressWarnings("serial")
public class Profile implements Serializable {

    // Attribute that represents the database load for this entity
    public static final Long SUPER_USER = 1l;

    /**
     * Attribute that represents the internal identifier for the entity
     */
    @EqualsAndHashCode.Include
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Attribute that represents the name of the profile
     */
    @EqualsAndHashCode.Include
    @Convert(converter = TrimConverter.class)
    @Column(nullable = false, length = 255)
    private String name;

    /**
     * Attribute that represents the status for profile. It is associated with enumeration {@code StatusEnum}
     */
    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false, name = "status")
    private StatusEnum statusEnum;

    /**
     * Attribute that represents if the profile will be visible in frontend
     */
    @Column(nullable = false)
    private boolean visible;

    /**
     * Attribute that represents the relationship between {@link Profile} and {@link Permission}. The
     * relationship is many to many, so the owner side must configure the relationship with the owner entity
     * id (JoinColumns) and the inverse join column of the target entity (InverseJoinColumns). Must be
     * configured too the table name and the attribute must be a list
     */
    @NotAudited
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "PROFILE_PERMISSION", 
               joinColumns = @JoinColumn(name = "id_profile"), 
               inverseJoinColumns = @JoinColumn(name = "id_permission"), 
               uniqueConstraints = {@UniqueConstraint(columnNames = {"id_profile", "id_permission"})}
    )
    private Set<Permission> permission;

    // ========================================================
    // CONSTRUCTORS
    // ========================================================
    
    public Profile(Long id) {
        
        this.id = id;
    }
    
    // ========================================================
    // GETTERS AND SETTERS
    // ========================================================
    
    /**
     * Method responsible for returning the own object.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @return The {@link Profile} object
     */
    @JsonIgnore
    public Profile getThisObject() {

        return this;
    }

    // ========================================================
    // TOSTRING
    // ========================================================

    @Override
    public String toString() {

        return "Name: " + this.getName() + " - Status: " + this.getStatusEnum() + " - Visible: "
                + this.isVisible();
    }

}
