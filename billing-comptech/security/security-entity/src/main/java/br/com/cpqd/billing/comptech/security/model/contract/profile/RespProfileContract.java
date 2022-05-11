package br.com.cpqd.billing.comptech.security.model.contract.profile;

import java.io.Serializable;
import java.util.List;

import br.com.cpqd.billing.comptech.security.model.contract.permission.PermissionItem;
import br.com.cpqd.billing.comptech.security.model.entity.Profile;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * This class represents a response contract to {@link Profile} REST API service on application when the
 * operation is FINDBYID.
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 * @see br.com.cpqd.billing.comptech.security.model.contract.profile.RespProfileSummaryContract
 */
@ApiModel(value = "Response Profile Contract", 
          description = "Modelo de entidade responsável pela estrutura da resposta sobre um determinado perfil de acesso")
@Data
@EqualsAndHashCode(callSuper = false)
@SuppressWarnings("serial")
public class RespProfileContract extends RespProfileSummaryContract implements Serializable {

    /**
     * Attribute that represents the list of permission
     */
    @ApiModelProperty(name = "permission", notes = "Lista de permissões de acesso às funcionalidades da aplicação associadas ao perfil de acesso", position = 5)
    private List<PermissionItem> permission;

}
