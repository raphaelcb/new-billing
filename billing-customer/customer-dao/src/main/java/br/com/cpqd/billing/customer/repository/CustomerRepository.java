package br.com.cpqd.billing.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cpqd.billing.customer.model.entity.Customer;

/**
 * This interface represents a JPA Repository for {@link Customer}.
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
