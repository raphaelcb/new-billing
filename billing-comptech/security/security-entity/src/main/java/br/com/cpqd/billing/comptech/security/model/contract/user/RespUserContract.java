package br.com.cpqd.billing.comptech.security.model.contract.user;

import java.io.Serializable;

import br.com.cpqd.billing.comptech.security.model.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * This class represents a response contract to {@link User} REST API service when the operation is FINDBYID.
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 * @see br.com.cpqd.billing.comptech.security.model.contract.user.RespUserSummaryContract
 */
@ApiModel(value = "Response User Contract", 
          description = "Modelo de entidade responsável pela estrutura da resposta sobre um determinado usuário")
@Data
@EqualsAndHashCode(callSuper = false)
@SuppressWarnings("serial")
public class RespUserContract extends RespUserSummaryContract implements Serializable {

    /**
     * Attribute that represents the occupation
     */
    @ApiModelProperty(name = "occupation", value = "Cargo do usuário", example = "Analista de Sistemas", position = 6)
    private String occupation;

    /**
     * Attribute that represents the email
     */
    @ApiModelProperty(name = "email", value = "E-mail do usuário", example = "user_teste@cpqd.com.br", position = 7)
    private String email;

}
