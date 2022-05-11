package br.com.cpqd.billing.comptech.security.model.contract.user;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.cpqd.billing.comptech.audit.model.entity.AuditUsername;
import br.com.cpqd.billing.comptech.security.model.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * This class represents a request contract to {@link User} REST API service when the operation is ADD.
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 * @see br.com.cpqd.billing.comptech.audit.model.entity.AuditUsername
 */
@ApiModel(value = "Request Add User Contract", 
          description = "Modelo de entidade responsável pela estrutura da requisição para o serviço de inclusão de um novo usuário na aplicação")
@Data
@EqualsAndHashCode(callSuper = false)
@SuppressWarnings("serial")
public class ReqAddUserContract extends AuditUsername implements Serializable {

    /**
     * Attribute that represents the login of the user
     */
    @ApiModelProperty(name = "login", value = "Login do usuário", required = true, example = "login_user_teste", position = 1)
    @NotEmpty(message = "{comptech.security.component.validation.user.login.not.empty}")
    private String login;

    /**
     * Attribute that represents the name of the user
     */
    @ApiModelProperty(name = "name", value = "Nome do usuário", required = true, example = "Nome do usuário de teste", position = 2)
    @NotEmpty(message = "{comptech.security.component.validation.user.name.not.empty}")
    private String name;

    /**
     * Attribute that represents the occupation
     */
    @ApiModelProperty(name = "occupation", value = "Cargo do usuário", required = false, example = "Analista de Sistemas", position = 3)
    private String occupation;

    /**
     * Attribute that represents the email
     */
    @ApiModelProperty(name = "email", value = "E-mail do usuário", required = true, example = "user_teste@cpqd.com.br", position = 4)
    @NotEmpty(message = "{comptech.security.component.validation.user.email.not.empty}")
    @Email(message = "{comptech.security.component.validation.user.email.invalid}")
    private String email;

    /**
     * Attribute that represents the profile associated with user
     */
    @ApiModelProperty(name = "profile", value = "Identificador do perfil de acesso associado ao usuário", required = true, example = "2", position = 5)
    @NotNull(message = "{comptech.security.component.validation.user.profile.not.null}")
    private Long profile;
    
    /**
     * Attribute that represents the password of the user
     */
    @ApiModelProperty(name = "password", value = "Senha do usuário", required = true, example = "Senha@2021", position = 6)
    @NotEmpty(message = "{comptech.security.component.validation.user.password.not.empty}")
    private String password;
    
}
