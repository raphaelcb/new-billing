package br.com.cpqd.billing.company.api.util;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import br.com.cpqd.billing.company.model.contract.CompanyItem;
import br.com.cpqd.billing.company.model.entity.Company;

/**
 * This class is responsible for configuring the {@link ModelMapper} library to convert classes on component.
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 */
public class CompanyModelMapperUtil {

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

        // Mapper's - Company
        modelMapper.createTypeMap(Company.class, CompanyItem.class)
                .<String> addMapping(src -> src.getName(), (dest, v) -> dest.setName(v))
                .<String> addMapping(src -> src.getCnpj(), (dest, v) -> dest.setCnpj(v));
    }

    /**
     * Private constructor
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     */
    private CompanyModelMapperUtil() {

        // Private constructor
    }

    /**
     * Method responsible for converting the entity {@link Company} to contract {@link CompanyItem}.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param company The {@link Company} object
     * @return The contract object converted
     */
    public static CompanyItem toCompanyDTOMapper(Company company) {

        return modelMapper.map(company, CompanyItem.class);
    }

}
