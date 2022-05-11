package br.com.cpqd.billing.comptech.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cpqd.billing.comptech.security.model.entity.AttemptsLogin;

/**
 * This interface represents a JPA Repository for {@link AttemptsLogin}.
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 */
public interface AttemptsLoginRepository extends JpaRepository<AttemptsLogin, Long> {

}
