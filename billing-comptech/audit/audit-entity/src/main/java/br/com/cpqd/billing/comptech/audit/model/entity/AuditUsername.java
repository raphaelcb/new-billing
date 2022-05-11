package br.com.cpqd.billing.comptech.audit.model.entity;

import java.io.Serializable;

import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;

import org.hibernate.envers.NotAudited;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * This class represents a model for {@link AuditUsername} entity.
 * <p>
 * Other entities that need to do the audit must extends this class to set the username of the operation. The
 * username will be passed through separated argument on each REST entry point.
 * </p>
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 */
@ApiModel(value = "Audit Username", 
          description = "Modelo de entidade extensível e responsável pela estrutura de auditoria em relação ao usuário quem está realizando as operações")
@Data
@SuppressWarnings("serial")
public class AuditUsername implements Serializable {

    /**
     * Attribute that represents the username of who made the operation (ADD/UPDATE/DELETE) on application
     */
    @ApiModelProperty(name = "username", value = "Login do usuário quem está efetuando a operação no sistema para efeitos de auditoria", required = true, example = "login_user", position = 100)
    @NotEmpty(message = "{comptech.audit.component.validation.authentication.username.not.empty}")
    @Transient
    @NotAudited
    private String username;

}
