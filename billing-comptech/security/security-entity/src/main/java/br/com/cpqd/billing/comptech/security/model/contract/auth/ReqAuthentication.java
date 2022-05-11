package br.com.cpqd.billing.comptech.security.model.contract.auth;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * This class represents a request contract to authentication REST API service on application. The
 * authentication methods available are LDAP and database.
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 */
@ApiModel(value = "Request Authentication", 
          description = "Modelo de entidade responsável pela estrutura da requisição para o processo de autenticação de usuários")
@Data
@SuppressWarnings("serial")
public class ReqAuthentication implements Serializable {

    /**
     * Attribute that represents the username
     */
    @ApiModelProperty(name = "username", value = "Login do usuário", required = true, example = "root", position = 1)
    @NotEmpty(message = "{comptech.security.component.validation.authentication.username.not.empty}")
    private String username;

    /**
     * Attribute that represents the username's password
     */
    @ApiModelProperty(name = "password", value = "Senha do usuário", required = true, example = "SuperUser@2021", position = 2)
    @NotEmpty(message = "{comptech.security.component.validation.authentication.password.not.empty}")
    private String password;

}
