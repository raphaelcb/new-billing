package br.com.cpqd.billing.company.model.contract;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * This class represents a body to {@link RespCompanyContract}.
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 */
@ApiModel(value = "Company", 
          description = "Modelo de entidade responsável pela estrutura da resposta sobre as empresas")
@Data
@SuppressWarnings("serial")
public class CompanyItem implements Serializable {

    /**
     * Attribute that represents the permission key
     */
    @ApiModelProperty(name = "name", notes = "Nome da empresa", example = "Fundação CPqD", position = 1)
    private String name;

    /**
     * Attribute that represents the description
     */
    @ApiModelProperty(name = "cnpj", notes = "CNPJ da empresa", example = "02.641.663-0001/10", position = 2)
    private String cnpj;

}
