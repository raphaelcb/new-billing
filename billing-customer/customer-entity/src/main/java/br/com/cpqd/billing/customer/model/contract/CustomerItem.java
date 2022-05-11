package br.com.cpqd.billing.customer.model.contract;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * This class represents a body to {@link RespCustomerContract}.
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 */
@ApiModel(value = "Customer", 
          description = "Modelo de entidade responsável pela estrutura da resposta sobre os clientes")
@Data
@SuppressWarnings("serial")
public class CustomerItem implements Serializable {

    /**
     * Attribute that represents the permission key
     */
    @ApiModelProperty(name = "name", notes = "Nome do cliente", example = "Raphael de Carvalho Barbosa", position = 1)
    private String name;

    /**
     * Attribute that represents the description
     */
    @ApiModelProperty(name = "phone", notes = "Telefone do cliente", example = "(99) 98888-8888", position = 2)
    private String phone;

}
