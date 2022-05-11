package br.com.cpqd.billing.customer.model.contract;

import java.io.Serializable;
import java.util.List;

import br.com.cpqd.billing.customer.model.entity.Customer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * This class represents a response contract to {@link Customer} REST API service on application.
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 */
@ApiModel(value = "Response Customer Contract", 
          description = "Modelo de entidade responsável pela estrutura da resposta sobre os clientes")
@Data
@SuppressWarnings("serial")
public class RespCustomerContract implements Serializable {

    /**
     * Attribute that represents the list of customer
     */
    @ApiModelProperty(name = "customer", notes = "Lista de clientes disponíveis")
    private List<CustomerItem> customer;

}
