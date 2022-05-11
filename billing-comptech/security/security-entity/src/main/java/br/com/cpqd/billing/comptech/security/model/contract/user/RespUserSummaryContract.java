package br.com.cpqd.billing.comptech.security.model.contract.user;

import java.io.Serializable;

import br.com.cpqd.billing.comptech.security.model.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * This class represents a response contract to {@link User} REST API service when the operation is
 * FINDBYPARAMETERS.
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 */
@ApiModel(value = "Response User Summary Contract", 
          description = "Modelo de entidade responsável pela estrutura da resposta sobre a listagem de usuários")
@Data
@SuppressWarnings("serial")
public class RespUserSummaryContract implements Serializable {

    /**
     * Attribute that represents the internal identifier for the entity
     */
    @ApiModelProperty(name = "id", value = "Identificador único do usuário", example = "2", position = 1)
    private Long id;

    /**
     * Attribute that represents the login of the user
     */
    @ApiModelProperty(name = "login", value = "Login do usuário", example = "login_user_teste", position = 2)
    private String login;

    /**
     * Attribute that represents the name of the user
     */
    @ApiModelProperty(name = "name", value = "Nome do usuário", example = "Nome do usuário de teste", position = 3)
    private String name;

    /**
     * Attribute that represents the status for user
     */
    @ApiModelProperty(name = "status", value = "Situação do usuário. Valores válidos: 0 - Inativo; 1 - Ativo; 2 - Bloqueado", example = "ACTIVE", position = 4)
    private String status;

    /**
     * Attribute that represents the profile associated with user
     */
    @ApiModelProperty(name = "profile", value = "Perfil de acesso associado ao usuário", example = "Administrador", position = 5)
    private String profile;

}
