package br.com.cpqd.billing.customer.api.util;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import br.com.cpqd.billing.customer.model.contract.CustomerItem;
import br.com.cpqd.billing.customer.model.entity.Customer;

/**
 * This class is responsible for configuring the {@link ModelMapper} library to convert classes on component.
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 */
public class CustomerModelMapperUtil {

    /**
     * Attribute that represents the instance to {@link ModelMapper}
     */
    private static ModelMapper modelMapper;

    /**
     * Static block for initializing the {@link ModelMapper} object and define the mapping strategy
     */
    static {
        // ModelMapper global configuration
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        // Mapper's - Customer
        modelMapper.createTypeMap(Customer.class, CustomerItem.class)
                .<String> addMapping(src -> src.getName(), (dest, v) -> dest.setName(v))
                .<String> addMapping(src -> src.getPhone(), (dest, v) -> dest.setPhone(v));
    }

    /**
     * Private constructor
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     */
    private CustomerModelMapperUtil() {

        // Private constructor
    }

    /**
     * Method responsible for converting the entity {@link Customer} to contract {@link CustomerItem}.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param customer The {@link Customer} object
     * @return The contract object converted
     */
    public static CustomerItem toCustomerDTOMapper(Customer customer) {

        return modelMapper.map(customer, CustomerItem.class);
    }

}
