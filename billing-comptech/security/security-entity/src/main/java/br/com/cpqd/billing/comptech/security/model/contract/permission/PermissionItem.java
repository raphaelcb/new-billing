package br.com.cpqd.billing.comptech.security.model.contract.permission;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * This class represents a body to {@link RespPermissionContract}.
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 */
@ApiModel(value = "Permission", 
          description = "Modelo de entidade responsável pela estrutura da resposta sobre as permissões")
@Data
@SuppressWarnings("serial")
public class PermissionItem implements Serializable {

    /**
     * Attribute that represents the permission key
     */
    @ApiModelProperty(name = "key", notes = "Chave única identificadora da permissão", example = "ADD_PROFILE", position = 1)
    private String key;

    /**
     * Attribute that represents the description
     */
    @ApiModelProperty(name = "description", notes = "Descrição da permissão", example = "Inclusão de perfil de acesso", position = 2)
    private String description;

}
