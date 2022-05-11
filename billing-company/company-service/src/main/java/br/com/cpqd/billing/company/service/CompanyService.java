package br.com.cpqd.billing.company.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cpqd.billing.company.model.entity.Company;
import br.com.cpqd.billing.company.repository.CompanyRepository;

/**
 * This class is responsible for Service layer of the application.
 * <p>
 * It contains the methods to manipulate the data for {@link Company}.
 * </p>
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 */
@Service
@Transactional
public class CompanyService {

    /**
     * Injection of dependency of {@link CompanyRepository}
     */
    @Autowired
    private CompanyRepository companyRepository;

    /**
     * Method responsible for getting all {@link Company}.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param page The desired page number to pagination
     * @param pageSize The desired page size to pagination
     * @return The list with all {@link Company} found or a new empty list
     */
    public List<Company> findAll(int page, int pageSize) {

        var pageable = PageRequest.of(page, pageSize);
        return this.companyRepository.findAll(pageable).getContent();
    }
}
