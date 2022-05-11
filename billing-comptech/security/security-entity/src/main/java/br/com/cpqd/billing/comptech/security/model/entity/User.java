package br.com.cpqd.billing.comptech.security.model.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.cpqd.billing.comptech.security.model.TrimConverter;
import br.com.cpqd.billing.comptech.security.model.entity.enums.StatusUserEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * This class represents a model for {@link User} entity.
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 */
@Entity
@Table(name = "USERS", uniqueConstraints = {
        @UniqueConstraint(name = "uk_user_name", columnNames = {"name"}),
        @UniqueConstraint(name = "uk_user_login", columnNames = {"login"}),
        @UniqueConstraint(name = "uk_user_email", columnNames = {"email"})
        })
@Audited(withModifiedFlag = true)
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@SuppressWarnings("serial")
public class User implements Serializable {

    /**
     * Attribute that represents the internal identifier for the entity
     */
    @EqualsAndHashCode.Include
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Attribute that represents the login of the user
     */
    @EqualsAndHashCode.Include
    @Convert(converter = TrimConverter.class)
    @Column(nullable = false, length = 100)
    private String login;

    /**
     * Attribute that represents the name of the user
     */
    @EqualsAndHashCode.Include
    @Convert(converter = TrimConverter.class)
    @Column(nullable = false, length = 255)
    private String name;

    /**
     * Attribute that represents the acronym of the user
     */
    @Convert(converter = TrimConverter.class)
    @Column(nullable = false, length = 255)
    private String acronym;

    /**
     * Attribute that represents the occupation
     */
    @Convert(converter = TrimConverter.class)
    @Column(length = 150)
    private String occupation;

    /**
     * Attribute that represents the email
     */
    @Convert(converter = TrimConverter.class)
    @Column(nullable = false, length = 100)
    private String email;

    /**
     * Attribute that represents the attempts of login
     */
    @Column(nullable = false, name = "attempts_login")
    private Integer attemptsLogin;

    /**
     * Attribute that represents if is the first access of the user. On positive case will be necessary to
     * change the password
     */
    @Column(nullable = false, name = "first_access")
    private boolean firstAccess;

    /**
     * Attribute that represents the status for user. It is associated with enumeration {@code StatusUserEnum}
     */
    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false, name = "status")
    private StatusUserEnum statusUserEnum;

    /**
     * Attribute that represents the relationship between {@link User} and {@link Profile}. The column
     * {@code id_profile} on {@link User} table maps to the primary key in the {@link Profile}, in this case
     * {@code id} column.
     */
    @NotAudited
    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "id_profile", referencedColumnName = "id", nullable = false)
    private Profile profile;

    /**
     * Attribute that represents the relationship between {@link User} and {@link ActivePassword}. The column
     * {@code id_active_password} on {@link User} table maps to the primary key in the {@link ActivePassword},
     * in this case {@code id} column.
     */
    @NotAudited
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_active_password", referencedColumnName = "id", nullable = false)
    private ActivePassword activePassword;

    /**
     * Attribute that represents the relationship between {@link User} and {@link InactivePassword}. The
     * {@link InactivePassword} class must be have an attribute where the User's Id is mapped
     */
    @NotAudited
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<InactivePassword> inactivePassword;

    // ========================================================
    // GETTERS AND SETTERS
    // ========================================================

    /**
     * Method responsible for returning the own object.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @return The {@link User} object
     */
    @JsonIgnore
    public User getThisObject() {

        return this;
    }

    // ========================================================
    // TOSTRING
    // ========================================================

    @Override
    public String toString() {

        return "Login: " + this.getLogin() + " - Name: " + this.getName() + " - Acronym: " + this.getAcronym()
                + " - Occupation: " + this.getOccupation() + " - E-mail: " + this.getEmail()
                + " - Attempts of login: " + this.getAttemptsLogin() + " - First access: "
                + this.isFirstAccess() + " - Status: " + this.getStatusUserEnum().name();
    }

}
