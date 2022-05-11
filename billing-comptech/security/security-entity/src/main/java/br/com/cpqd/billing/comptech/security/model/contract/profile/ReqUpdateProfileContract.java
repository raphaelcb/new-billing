package br.com.cpqd.billing.comptech.security.model.contract.profile;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import br.com.cpqd.billing.comptech.security.model.entity.Profile;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * This class represents a request contract to {@link Profile} REST API service when the operation is UPDATE.
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 * @see br.com.cpqd.billing.comptech.security.model.contract.profile.ReqAddProfileContract
 */
@ApiModel(value = "Request Update Profile Contract", 
          description = "Modelo de entidade responsável pela estrutura da requisição para o serviço de alteração de um perfil de acesso na aplicação")
@Data
@EqualsAndHashCode(callSuper = false)
@SuppressWarnings("serial")
public class ReqUpdateProfileContract extends ReqAddProfileContract implements Serializable {

    /**
     * Attribute that represents the internal identifier for the entity
     */
    @ApiModelProperty(name = "id", value = "Identificador único do perfil de acesso", example = "2", position = 5)
    @NotNull(message = "{comptech.security.component.validation.profile.id.not.null}")
    private Long id;

    // ========================================================
    // CONSTRUCTORS
    // ========================================================
    
    /**
     * Constructor
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param id The identifier of the profile
     * @param name The name of the profile
     * @param status The status of the profile
     * @param visible The flag if the profile will be visible in screen
     * @param authorities The authorities keys
     */
    public ReqUpdateProfileContract(Long id, String name, Integer status, boolean visible,
            List<String> authorities) {

        super(name, status, visible, authorities);
        this.id = id;
    }

}
