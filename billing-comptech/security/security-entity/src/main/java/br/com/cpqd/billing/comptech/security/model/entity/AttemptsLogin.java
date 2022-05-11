package br.com.cpqd.billing.comptech.security.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.cpqd.billing.comptech.security.model.TrimConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * This class represents a model for {@link AttemptsLogin} entity.
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 */
@Entity
@Table(name = "ATTEMPTS_LOGIN")
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@SuppressWarnings("serial")
public class AttemptsLogin implements Serializable {

    /**
     * Attribute that represents the internal identifier for the entity
     */
    @EqualsAndHashCode.Include
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Attribute that represents the timestamp
     */
    @EqualsAndHashCode.Include
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date timestamp;

    /**
     * Attribute that represents the user's login
     */
    @EqualsAndHashCode.Include
    @Convert(converter = TrimConverter.class)
    @Column(nullable = false, name = "login", length = 100)
    private String login;

    /**
     * Attribute that represents the IP terminal
     */
    @EqualsAndHashCode.Include
    @Convert(converter = TrimConverter.class)
    @Column(nullable = false, length = 20)
    private String ip;

    /**
     * Attribute that represents the status for attempt login
     */
    @Column(nullable = false, name = "successfully_login")
    private boolean status;

    // ========================================================
    // GETTERS AND SETTERS
    // ========================================================

    /**
     * Method responsible for returning the own object.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @return The {@link AttemptsLogin} object
     */
    @JsonIgnore
    public AttemptsLogin getThisObject() {

        return this;
    }

}
