package br.com.cpqd.billing.comptech.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cpqd.billing.comptech.security.model.entity.AttemptsLogin;
import br.com.cpqd.billing.comptech.security.repository.AttemptsLoginRepository;

/**
 * This class is responsible for Service layer of the application.
 * <p>
 * It contains the methods to manipulate the data for {@link AttemptsLogin}.
 * <p>
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 */
@Service
@Transactional
public class AttemptsLoginService {

    /**
     * Injection of dependency of {@link AttemptsLoginRepository}
     */
    @Autowired
    private AttemptsLoginRepository attemptsLoginRepository;

    /**
     * Method responsible for saving an {@link AttemptsLogin}.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param attemptsLogin The {@link AttemptsLogin} object to persist
     * @return The {@link AttemptsLogin} object persisted
     * @throws DataAccessException Exception thrown when some persistence error occurs
     */
    public AttemptsLogin save(AttemptsLogin attemptsLogin) throws DataAccessException {

        return this.attemptsLoginRepository.saveAndFlush(attemptsLogin);
    }

}
