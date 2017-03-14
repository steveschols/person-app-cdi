package be.stesch.person.model;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.TemporalType.TIMESTAMP;

/**
 * Created by u420643 on 3/14/2017.
 */
@MappedSuperclass
public abstract class AbstractAuditedEntity implements Auditable {

    @Temporal(TIMESTAMP)
    private Date creationDate;

    @Temporal(TIMESTAMP)
    private Date lastUpdateDate;

    @Override
    public Date getCreationDate() {
        return creationDate;
    }

    @Override
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    @Override
    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    @PrePersist
    public void setAuditDataOnPersist() {
        setCreationDate(new Date());
    }

    @PreUpdate
    public void setAuditDataOnUpdate() {
        setLastUpdateDate(new Date());
    }

}
