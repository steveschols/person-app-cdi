package be.stesch.person.model;

import be.stesch.person.common.Notification;
import be.stesch.person.model.listener.AuditEntityListener;
import be.stesch.person.model.listener.OriginalStateEntityListener;
import com.google.common.annotations.VisibleForTesting;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static java.lang.String.format;
import static javax.persistence.EnumType.STRING;

/**
 * @author Steve Schols
 * @since 27/08/2015
 */
@Entity
@EntityListeners({AuditEntityListener.class, OriginalStateEntityListener.class})
@Dependent
public class Person implements Auditable, Serializable {

    private static final String NOTIFICATION_STRING = "Person ID %s, %s %s changed marital status from %s to %s";

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    @Enumerated(STRING)
    private MaritalStatus maritalStatus;

    private Date creationDate;

    private Date lastUpdateDate;

    @Transient
    private MaritalStatus originalMaritalStatus;

    @Transient
    @Inject
    private Event<Notification> notificationEvent;
    @Transient
    private BeanManager beanManager;

    public Person() {
    }

    public Person(String firstName, String lastName, MaritalStatus maritalStatus) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.maritalStatus = maritalStatus;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;

        if (maritalStatus != originalMaritalStatus) {
            String notificationString = format(NOTIFICATION_STRING, id, firstName, lastName, originalMaritalStatus, maritalStatus);

            Notification notification = new Notification(notificationString);
            //notificationEvent.fire(notification);
            getBeanManager().fireEvent(notification);
        }

    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public MaritalStatus getOriginalMaritalStatus() {
        return originalMaritalStatus;
    }

    public void setOriginalMaritalStatus(MaritalStatus originalMaritalStatus) {
        this.originalMaritalStatus = originalMaritalStatus;
    }

    private BeanManager getBeanManager() {
        if (beanManager == null) {
            beanManager = CDI.current().getBeanManager();
        }
        return beanManager;
    }

    @VisibleForTesting
    public void setNotificationEvent(Event<Notification> notificationEvent) {
        this.notificationEvent = notificationEvent;
    }
}
