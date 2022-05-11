package br.com.cpqd.billing.company.model.contract;

import java.io.Serializable;
import java.util.List;

import br.com.cpqd.billing.company.model.entity.Company;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * This class represents a response contract to {@link Company} REST API service on application.
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 */
@ApiModel(value = "Response Company Contract", 
          description = "Modelo de entidade responsável pela estrutura da resposta sobre as empresas")
@Data
@SuppressWarnings("serial")
public class RespCompanyContract implements Serializable {

    /**
     * Attribute that represents the list of company
     */
    @ApiModelProperty(name = "company", notes = "Lista de empresas disponíveis")
    private List<CompanyItem> company;

}
