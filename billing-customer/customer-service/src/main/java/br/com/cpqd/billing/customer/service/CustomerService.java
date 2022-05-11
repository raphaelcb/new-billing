package br.com.cpqd.billing.customer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cpqd.billing.customer.model.entity.Customer;
import br.com.cpqd.billing.customer.repository.CustomerRepository;

/**
 * This class is responsible for Service layer of the application.
 * <p>
 * It contains the methods to manipulate the data for {@link Customer}.
 * </p>
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 */
@Service
@Transactional
public class CustomerService {

    /**
     * Injection of dependency of {@link CustomerRepository}
     */
    @Autowired
    private CustomerRepository customerRepository;

    /**
     * Method responsible for getting all {@link Customer}.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param page The desired page number to pagination
     * @param pageSize The desired page size to pagination
     * @return The list with all {@link Customer} found or a new empty list
     */
    public List<Customer> findAll(int page, int pageSize) {

        var pageable = PageRequest.of(page, pageSize);
        return this.customerRepository.findAll(pageable).getContent();
    }
}
