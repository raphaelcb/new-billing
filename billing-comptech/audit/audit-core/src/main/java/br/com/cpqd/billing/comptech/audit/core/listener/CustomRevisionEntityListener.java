package br.com.cpqd.billing.comptech.audit.core.listener;

import org.hibernate.envers.RevisionListener;

import br.com.cpqd.billing.comptech.audit.core.model.entity.CustomRevisionEntity;
import br.com.cpqd.billing.comptech.audit.core.thread.CurrentAuditor;
import br.com.cpqd.billing.comptech.audit.core.thread.CurrentIP;

/**
 * This class represents an implementation of the {@link RevisionListener} interface and override the method
 * for getting the custom properties that will be registered on audit revision table.
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 * @see org.hibernate.envers.RevisionListener
 */
public class CustomRevisionEntityListener implements RevisionListener {

    /**
     * @see RevisionListener#newRevision(Object)
     */
    @Override
    public void newRevision(Object revisionEntity) {

        // Casting and retrieving the custom properties to audit revision table
        var customRevisionEntity = (CustomRevisionEntity) revisionEntity;
        customRevisionEntity.setUsername(CurrentAuditor.INSTANCE.get());
        customRevisionEntity.setIpAddress(CurrentIP.INSTANCE.get());
    }

}
