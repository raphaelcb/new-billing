package br.com.cpqd.billing.comptech.security.model.contract.user;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.cpqd.billing.comptech.audit.model.entity.AuditUsername;
import br.com.cpqd.billing.comptech.security.model.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * This class represents a request contract to {@link User} REST API service when the operation is UPDATE.
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 * @see br.com.cpqd.billing.comptech.audit.model.entity.AuditUsername
 */
@ApiModel(value = "Request Update User Contract", 
          description = "Modelo de entidade responsável pela estrutura da requisição para os serviços relacionados ao usuário da aplicação")
@Data
@EqualsAndHashCode(callSuper = false)
@SuppressWarnings("serial")
public class ReqUpdateUserContract extends AuditUsername implements Serializable {

    /**
     * Attribute that represents the internal identifier for the entity
     */
    @ApiModelProperty(name = "id", value = "Identificador único do usuário", example = "2", position = 1)
    @NotNull(message = "{comptech.security.component.validation.user.id.not.null}")
    private Long id;

    /**
     * Attribute that represents the name of the user
     */
    @ApiModelProperty(name = "name", value = "Nome do usuário", required = true, example = "Nome do usuário de teste alterado", position = 2)
    @NotEmpty(message = "{comptech.security.component.validation.user.name.not.empty}")
    private String name;

    /**
     * Attribute that represents the occupation
     */
    @ApiModelProperty(name = "occupation", value = "Cargo do usuário", required = false, example = "Analista de Sistemas alterado", position = 3)
    private String occupation;

    /**
     * Attribute that represents the email
     */
    @ApiModelProperty(name = "email", value = "E-mail do usuário", required = true, example = "user_teste_alterado@cpqd.com.br", position = 4)
    @NotEmpty(message = "{comptech.security.component.validation.user.email.not.empty}")
    @Email(message = "{comptech.security.component.validation.user.email.invalid}")
    private String email;

    /**
     * Attribute that represents the status for user
     */
    @ApiModelProperty(name = "status", value = "Situação do usuário. Valores válidos: 0 - Inativo; 1 - Ativo; 2 - Bloqueado", required = true, example = "2", position = 5)
    @NotNull(message = "{comptech.security.component.validation.user.status.not.null}")
    @Min(value = 0, message = "{comptech.security.component.validation.user.status.min.value}")
    @Max(value = 2, message = "{comptech.security.component.validation.user.status.max.value}")
    private Integer status;
   
}
