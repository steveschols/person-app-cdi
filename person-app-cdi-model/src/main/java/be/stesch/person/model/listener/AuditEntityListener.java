package be.stesch.person.model.listener;

import be.stesch.person.model.Auditable;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;

/**
 * @author Steve Schols
 * @since 27/08/2015
 */
public class AuditEntityListener {

    @PrePersist
    public void setAuditDataOnPersist(Object object) {
        if (object instanceof Auditable) {
            Auditable auditable = (Auditable) object;
            auditable.setCreationDate(new Date());
            auditable.setLastUpdateDate(new Date());
        }
    }

    @PreUpdate
    public void setAuditDataOnUpdate(Object object) {
        if (object instanceof Auditable) {
            Auditable auditable = (Auditable) object;
            auditable.setLastUpdateDate(new Date());
        }
    }

}
