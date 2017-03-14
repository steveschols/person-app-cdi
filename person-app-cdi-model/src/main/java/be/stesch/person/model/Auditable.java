package be.stesch.person.model;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;

/**
 * @author Steve Schols
 * @since 27/08/2015
 */
public interface Auditable {

    Date getCreationDate();

    void setCreationDate(Date creationDate);

    Date getLastUpdateDate();

    void setLastUpdateDate(Date lastUpdateDate);

    @PrePersist
    default void setAuditDataOnPersist() {
        setCreationDate(new Date());
    }

    @PreUpdate
    default void setAuditDataOnUpdate() {
        setLastUpdateDate(new Date());
    }

}
