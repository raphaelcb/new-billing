package br.com.cpqd.billing.comptech.security.model.contract.profile;

import java.io.Serializable;

import br.com.cpqd.billing.comptech.security.model.entity.Profile;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * This class represents a response contract to {@link Profile} REST API service on application when the
 * operation is FINDBYPARAMETERS.
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 */
@ApiModel(value = "Response Profile Summary Contract", 
          description = "Modelo de entidade responsável pela estrutura da resposta sobre a listagem de perfis de acesso")
@Data
@SuppressWarnings("serial")
public class RespProfileSummaryContract implements Serializable {

    /**
     * Attribute that represents the internal identifier for the entity
     */
    @ApiModelProperty(name = "id", value = "Identificador único do perfil de acesso", example = "2", position = 1)
    private Long id;

    /**
     * Attribute that represents the name of the profile
     */
    @ApiModelProperty(name = "name", value = "Nome do perfil de acesso", example = "Administrador", position = 2)
    private String name;

    /**
     * Attribute that represents the status for profile
     */
    @ApiModelProperty(name = "status", value = "Situação do perfil de acesso. Valores válidos: 0 - Inativo; 1 - Ativo", example = "ACTIVE", position = 3)
    private String status;

    /**
     * Attribute that represents if the profile will be visible in frontend
     */
    @ApiModelProperty(name = "visible", value = "Indicador se o perfil de acesso será visível ao usuário. Valores válidos: true - Visível; false - Não visível", example = "true", position = 4)
    private boolean visible;

}
