package br.com.cpqd.billing.comptech.security.model.contract.user;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import br.com.cpqd.billing.comptech.security.model.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * This class represents a request contract to {@link User} REST API service when the operation is FINDBYPARAMETERS.
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 */
@ApiModel(value = "Request Find User Contract", 
          description = "Modelo de entidade responsável pela estrutura da requisição para os serviços relacionados a busca de usuários na aplicação")
@Data
@SuppressWarnings("serial")
public class ReqFindUserContract implements Serializable {

    /**
     * Attribute that represents the login of the user
     */
    @ApiModelProperty(name = "login", value = "Login do usuário", required = false, example = "_user_", position = 1)
    private String login;

    /**
     * Attribute that represents the name of the user
     */
    @ApiModelProperty(name = "name", value = "Nome do usuário", required = false, example = "ÁRIO", position = 2)
    private String name;

    /**
     * Attribute that represents the email
     */
    @ApiModelProperty(name = "email", value = "E-mail do usuário", required = false, example = "@cpqd", position = 3)
    @Email(message = "{comptech.security.component.validation.user.email.invalid}")
    private String email;

    /**
     * Attribute that represents the status for user
     */
    @ApiModelProperty(name = "status", value = "Situação do usuário. Valores válidos: 0 - Inativo; 1 - Ativo; 2 - Bloqueado", required = true, example = "2", position = 4)
    @Min(value = 0, message = "{comptech.security.component.validation.user.status.min.value}")
    @Max(value = 2, message = "{comptech.security.component.validation.user.status.max.value}")
    private Integer status;

}
