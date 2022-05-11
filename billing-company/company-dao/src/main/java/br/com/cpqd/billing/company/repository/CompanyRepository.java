package br.com.cpqd.billing.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cpqd.billing.company.model.entity.Company;

/**
 * This interface represents a JPA Repository for {@link Company}.
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 */
public interface CompanyRepository extends JpaRepository<Company, Long> {

}
