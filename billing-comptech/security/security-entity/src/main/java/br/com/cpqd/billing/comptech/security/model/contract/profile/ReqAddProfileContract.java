package br.com.cpqd.billing.comptech.security.model.contract.profile;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.cpqd.billing.comptech.audit.model.entity.AuditUsername;
import br.com.cpqd.billing.comptech.security.model.entity.Profile;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * This class represents a request contract to {@link Profile} REST API service when the operation is ADD.
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 * @see br.com.cpqd.billing.comptech.audit.model.entity.AuditUsername
 */
@ApiModel(value = "Request Add Profile Contract", 
          description = "Modelo de entidade responsável pela estrutura da requisição para o serviço de inclusão de um novo perfil de acesso na aplicação")
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@SuppressWarnings("serial")
public class ReqAddProfileContract extends AuditUsername implements Serializable {

    /**
     * Attribute that represents the name of the profile
     */
    @ApiModelProperty(name = "name", value = "Nome do perfil de acesso", required = true, example = "Administrador", position = 1)
    @NotEmpty(message = "{comptech.security.component.validation.profile.name.not.empty}")
    private String name;

    /**
     * Attribute that represents the status for profile
     */
    @ApiModelProperty(name = "status", value = "Situação do perfil de acesso. Valores válidos: 0 - Inativo; 1 - Ativo", required = true, example = "1", position = 2)
    @NotNull(message = "{comptech.security.component.validation.profile.status.not.null}")
    @Min(value = 0, message = "{comptech.security.component.validation.profile.status.min.value}")
    @Max(value = 1, message = "{comptech.security.component.validation.profile.status.max.value}")
    private Integer status;

    /**
     * Attribute that represents if the profile will be visible in frontend
     */
    @ApiModelProperty(name = "visible", value = "Indicador se o perfil de acesso será visível ao usuário. Valores válidos: true - Visível; false - Não visível", required = true, example = "true", position = 3)
    @NotNull(message = "{comptech.security.component.validation.profile.visible.not.null}")
    private Boolean visible;

    /**
     * Attribute that represents the authorities keys
     */
    @ApiModelProperty(name = "authorities", value = "Lista com as chaves das permissões das funcionalidades da aplicação", required = true, example = "['ADD_PROFILE', 'SEARCH_PROFILE', 'VIEW_AUDIT_PROFILE', 'UPDATE_USER', 'DELETE_USER']", position = 4)
    @NotEmpty(message = "{comptech.security.component.validation.profile.authorities.not.null}")
    private List<String> authorities;

}
