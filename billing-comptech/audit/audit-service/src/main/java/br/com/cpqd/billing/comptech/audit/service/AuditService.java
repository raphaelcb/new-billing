package br.com.cpqd.billing.comptech.audit.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import br.com.cpqd.billing.comptech.audit.core.model.entity.CustomRevisionEntity;
import br.com.cpqd.billing.comptech.audit.model.entity.AuditEntityResult;
import br.com.cpqd.billing.comptech.exception.core.rest.RestObjectNotFoundException;

/**
 * This class is responsible for Service layer of the application.
 * <p>
 * It contains the methods to manipulate the data for {@link AuditEntityResult}.
 * </p>
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 */
@Service
@Transactional
public class AuditService {

    /**
     * Injection of dependency of {@link EntityManager}
     */
    @PersistenceContext
    private EntityManager entityManager;
    
    /**
     * Injection of dependency of {@link MessageSource}
     */
    @Autowired
    private MessageSource messageSource;

    /**
     * Method responsible for getting the specific audit history associated with a revision for generic entity
     * class.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param theClass The entity class where the data will be retrieved
     * @param revision The revision identifier for audit history
     * @return The audit history for generic entity class
     */
    @SuppressWarnings("unchecked")
    public List<AuditEntityResult> findHistory(Class<? extends Object> theClass, Integer revision) {

        // Initializing the object
        var auditReader = AuditReaderFactory.get(this.entityManager);

        // Create and mount the query for retrieve the history data
        var auditQuery = auditReader.createQuery().forRevisionsOfEntity(theClass, theClass.getName(), false,
                true);
        auditQuery.add(AuditEntity.revisionNumber().eq(revision));

        // Execute the query
        var lstEntity = auditQuery.getResultList();

        if ((lstEntity == null) || (lstEntity.isEmpty())) {
            throw new RestObjectNotFoundException(this.messageSource.getMessage("global.no.data.found",
                    null, LocaleContextHolder.getLocale()));
        }
        
        // Extract the query result and return the history result
        return extractResult(auditReader, lstEntity);
    }

    /**
     * Method responsible for getting the specific audit history associated with a identifier for generic
     * entity class.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param theClass The entity class where the data will be retrieved
     * @param id The identifier of the object for audit history
     * @return The audit history for generic entity class
     */
    @SuppressWarnings("unchecked")
    public List<AuditEntityResult> findHistory(Class<? extends Object> theClass, Long id) {

        // Initializing the object
        var auditReader = AuditReaderFactory.get(this.entityManager);

        // Create and mount the query for retrieve the history data
        var auditQuery = auditReader.createQuery().forRevisionsOfEntity(theClass, theClass.getName(), false,
                true);
        auditQuery.add(AuditEntity.id().eq(id));

        // Execute the query
        var lstEntity = auditQuery.getResultList();

        if ((lstEntity == null) || (lstEntity.isEmpty())) {
            throw new RestObjectNotFoundException(this.messageSource.getMessage("global.no.data.found",
                    null, LocaleContextHolder.getLocale()));
        }
        
        // Extract the query result and return the history result
        return extractResult(auditReader, lstEntity);
    }

    /**
     * Method responsible for finding the audit history data of the generic entity class.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param theClass The entity class where the data will be retrieved
     * @param filterFrom The initial date/time filter
     * @param filterTo The final date/time filter
     * @param filterRevisionType The revision type identifier (0 - ADD / 1 - UPDATE / 2 - DELETE)
     * @return The list with all history data found for generic entity class
     * @throws RestObjectNotFoundException Exception thrown if there are not audit to return
     */
    @SuppressWarnings("unchecked")
    public List<AuditEntityResult> findHistory(Class<? extends Object> theClass, Date filterFrom,
            Date filterTo, List<Integer> filterRevisionType) throws RestObjectNotFoundException {

        // Initializing the object
        var auditReader = AuditReaderFactory.get(this.entityManager);

        // Create and mount the query for retrieve the history data
        var auditQuery = mountFilters(theClass, filterFrom, filterTo, filterRevisionType, auditReader);

        // Execute the query
        var lstEntity = auditQuery.getResultList();
        
        if ((lstEntity == null) || (lstEntity.isEmpty())) {
            throw new RestObjectNotFoundException(this.messageSource.getMessage("global.no.data.found",
                    null, LocaleContextHolder.getLocale()));
        }

        // Extract the query result and return the history result
        return extractResult(auditReader, lstEntity);
    }

    /**
     * Method responsible for mount the filters to audit history query.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param theClass The entity class where the data will be retrieved
     * @param filterFrom The initial date/time filter
     * @param filterTo The final date/time filter
     * @param filterRevisionType The revision type identifier (0 - ADD / 1 - UPDATE / 2 - DELETE)
     * @param auditReader The main object of the Envers framework to mount the query
     * @return The query with all filters filled (if applicable)
     */
    private AuditQuery mountFilters(Class<? extends Object> theClass, Date filterFrom, Date filterTo,
            List<Integer> filterRevisionType, AuditReader auditReader) {

        // Create the basic query
        var auditQuery = auditReader.createQuery().forRevisionsOfEntityWithChanges(theClass, theClass.getName(), 
                true);

        // Verify if the date/time filters are filled to add it on query
        if ((filterFrom != null) && (filterTo != null)) {
            auditQuery.add(AuditEntity.revisionProperty("timestamp").gt(filterFrom.getTime()))
                    .add(AuditEntity.revisionProperty("timestamp").lt(filterTo.getTime()));
        }

        // Verify if the revision type filter is filled to add it on query
        if (filterRevisionType != null) {
            var lstRevisionType = new ArrayList<RevisionType>();
            filterRevisionType.forEach(element -> {
                if (element == RevisionType.ADD.ordinal()) {
                    lstRevisionType.add(RevisionType.ADD);
                } else if (element == RevisionType.MOD.ordinal()) {
                    lstRevisionType.add(RevisionType.MOD);
                } else if (element == RevisionType.DEL.ordinal()) {
                    lstRevisionType.add(RevisionType.DEL);
                }
            });
            if (!lstRevisionType.isEmpty()) {
                auditQuery.add(AuditEntity.revisionType().in(lstRevisionType));
            }
        }

        // Add order criteria and maximum result to query
        auditQuery.addOrder(AuditEntity.property("id").asc());
        auditQuery.setMaxResults(500);

        // Return the query
        return auditQuery;
    }

    /**
     * Method responsible for extracting the audit history result.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param auditReader The main object of the Envers framework to mount the query
     * @param lstEntity The list with query result to be extracted
     * @return The list with all audit history data found for generic class
     */
    private List<AuditEntityResult> extractResult(AuditReader auditReader, List<Object[]> lstEntity) {

        // Initialize the objects
        var lstAuditEntityResult = new ArrayList<AuditEntityResult>();

        // Run for each revision found to fill the list of objects
        lstEntity.forEach(element -> {
            // The AuditEntityResult current
            var auditEntityResult = new AuditEntityResult();

            // Retrieving the username and the IP address
            var customRevisionEntity = (CustomRevisionEntity) element[1];
            auditEntityResult.setUsername(customRevisionEntity.getUsername());
            auditEntityResult.setIp(customRevisionEntity.getIpAddress());
            auditEntityResult.setRevision(customRevisionEntity.getId());

            // Retrieving the timestamp of the event
            auditEntityResult.setTimestamp(auditReader.getRevisionDate(customRevisionEntity.getId()));

            // Retrieving the operation type (ADD/UPDATE/DELETE)
            var revisionType = (RevisionType) element[2];
            auditEntityResult.setOperation(revisionType.name());

            // Retrieving the data
            auditEntityResult.setDetails(element[0].toString());

            // Add the current object to list that will be returned
            lstAuditEntityResult.add(auditEntityResult);
        });

        // Order the result and return the list with all history data
        return lstAuditEntityResult.stream()
                .sorted(Comparator.comparing(AuditEntityResult::getTimestamp).reversed())
                .collect(Collectors.toList());
    }

}
