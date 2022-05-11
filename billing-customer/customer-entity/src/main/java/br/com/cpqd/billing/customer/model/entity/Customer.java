package br.com.cpqd.billing.customer.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * This class represents a model for {@link Customer} entity.
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 */
@Entity
@Table(name = "CUSTOMER", uniqueConstraints = {
        @UniqueConstraint(name = "uk_customer_name", columnNames = {"name"}),
        @UniqueConstraint(name = "uk_customer_phone", columnNames = {"phone"})
        })
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@SuppressWarnings("serial")
public class Customer implements Serializable {

    /**
     * Attribute that represents the internal identifier for the entity
     */
    @EqualsAndHashCode.Include
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Attribute that represents the customer name
     */
    @EqualsAndHashCode.Include
    @Column(nullable = false, length = 250)
    private String name;
    
    /**
     * Attribute that represents the phone
     */
    @EqualsAndHashCode.Include
    @Column(nullable = false, length = 15)
    private String phone;

    // ========================================================
    // GETTERS AND SETTERS
    // ========================================================

    /**
     * Method responsible for returning the own object.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @return The {@link Customer} object
     */
    @JsonIgnore
    public Customer getThisObject() {

        return this;
    }

}
