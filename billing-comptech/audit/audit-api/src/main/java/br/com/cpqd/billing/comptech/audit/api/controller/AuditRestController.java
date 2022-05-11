package br.com.cpqd.billing.comptech.audit.api.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.cpqd.billing.comptech.audit.model.entity.AuditEntityResult;
import br.com.cpqd.billing.comptech.audit.service.AuditService;

/**
 * This class represents a REST Controller and is responsible for exposing audit entry points for any entity
 * of the application.
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 * @param <T> The generic type that will have the data manipulated on this class
 */
public class AuditRestController<T> {

    /**
     * Injection of dependency of {@link AuditService}
     */
    @Autowired
    private AuditService auditService;

    /**
     * Attribute that represents the T generic type associated with this class
     */
    private final T genericType;

    /**
     * Constructor
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param theClass The T generic type associated with this class
     */
    public AuditRestController(T theClass) {

        this.genericType = theClass;
    }

    /**
     * Method responsible for getting the full audit history for T generic type associated with this class.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param from The initial date/time of the period for searching the history. The format is
     *            yyyy-MM-ddTHH:mm:ss
     * @param to The final date/time of the period for searching the history. The format is
     *            yyyy-MM-ddTHH:mm:ss
     * @param revisionType The list of all revision type codes (0 - ADD / 1 - UPDATE / 2 - DELETE)
     * @return List with all audit history for T generic type associated with this class
     */
    @GetMapping(path = {"/audit/full"}, produces = "application/json")
    public List<AuditEntityResult> findEntityHistory(
            @RequestParam(name = "from", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date from, 
            @RequestParam(name = "to", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date to, 
            @RequestParam(name = "revision_type", required = false) List<Integer> revisionType) {

        return this.auditService.findHistory(this.genericType.getClass(), from, to, revisionType);
    }

    /**
     * Method responsible for getting the specific audit history of the item associated with a identifier for T
     * generic type associated with this class.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param id The identifier of the item for audit history
     * @return The audit history for T generic type associated with this class
     */
    @GetMapping(path = {"/audit/item"}, produces = "application/json")
    public List<AuditEntityResult> findEntityHistory(@RequestParam(name = "id", required = true) Long id) {

        return this.auditService.findHistory(this.genericType.getClass(), id);
    }

    /**
     * Method responsible for detailing a specific item associated with a revision for T generic type
     * associated with this class.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param revision The revision identifier for audit history
     * @return The audit history for T generic type associated with this class
     */
    @GetMapping(path = {"/audit/item/detail"}, produces = "application/json")
    public List<AuditEntityResult> findEntityHistory(@RequestParam(name = "revision", required = true) Integer revision) {

        return this.auditService.findHistory(this.genericType.getClass(), revision);
    }

}
