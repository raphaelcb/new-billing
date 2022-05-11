package br.com.cpqd.billing.comptech.security.model.contract.permission;

import java.io.Serializable;
import java.util.List;

import br.com.cpqd.billing.comptech.security.model.entity.Permission;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * This class represents a response contract to {@link Permission} REST API service on application.
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 */
@ApiModel(value = "Response Permission Contract", 
          description = "Modelo de entidade responsável pela estrutura da resposta sobre as permissões")
@Data
@SuppressWarnings("serial")
public class RespPermissionContract implements Serializable {

    /**
     * Attribute that represents the list of permission
     */
    @ApiModelProperty(name = "permission", notes = "Lista de permissões disponíveis")
    private List<PermissionItem> permission;

}
