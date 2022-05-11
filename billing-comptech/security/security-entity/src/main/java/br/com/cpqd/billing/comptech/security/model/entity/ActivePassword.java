package br.com.cpqd.billing.comptech.security.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.cpqd.billing.comptech.security.model.TrimConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * This class represents a model for {@link ActivePassword} entity.
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 */
@Entity
@Table(name = "ACTIVE_PASSWORD")
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@SuppressWarnings("serial")
public class ActivePassword implements Serializable {

    /**
     * Attribute that represents the internal identifier for the entity
     */
    @EqualsAndHashCode.Include
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Attribute that represents the password
     */
    @EqualsAndHashCode.Include
    @Convert(converter = TrimConverter.class)
    @Column(nullable = false, length = 100)
    private String password;
    
    // ========================================================
    // CONSTRUCTORS
    // ========================================================
    
    public ActivePassword(String password) {
        
        this.password = password;
    }
    
    // ========================================================
    // GETTERS AND SETTERS
    // ========================================================
      
    /**
     * Method responsible for returning the own object.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @return The {@link ActivePassword} object
     */
    @JsonIgnore
    public ActivePassword getThisObject() {

        return this;
    }

}
