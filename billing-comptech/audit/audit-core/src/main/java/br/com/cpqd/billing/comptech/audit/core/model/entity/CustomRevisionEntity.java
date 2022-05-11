package br.com.cpqd.billing.comptech.audit.core.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

import br.com.cpqd.billing.comptech.audit.core.listener.CustomRevisionEntityListener;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * This class represents a model for {@link CustomRevisionEntity} entity.
 * <p>
 * It is a custom class to register the audit events because add more important elements on table.
 * </p>
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 * @see org.hibernate.envers.DefaultRevisionEntity
 */
@Entity
@Table(name = "AUDIT_REVISION")
@RevisionEntity(CustomRevisionEntityListener.class)
@EqualsAndHashCode(callSuper = false)
@SuppressWarnings("serial")
public class CustomRevisionEntity extends DefaultRevisionEntity {

    /**
     * Attribute that represents the username of who made the operation (ADD/UPDATE/DELETE) on application
     */
    @Getter
    @Setter
    @Column(nullable = false)
    private String username;

    /**
     * Attribute that represents the IP address of the client machine
     */
    @Getter
    @Setter
    @Column(name = "ip_address")
    private String ipAddress;

}
