package be.stesch.person.model;

import be.stesch.person.model.listener.AuditEntityListener;
import be.stesch.person.model.listener.OriginalStateEntityListener;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

import static javax.persistence.EnumType.STRING;

/**
 * @author Steve Schols
 * @since 27/08/2015
 */
@Entity
@EntityListeners({AuditEntityListener.class, OriginalStateEntityListener.class})
@XmlRootElement
public class Person implements Auditable, Serializable {

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

    public Person() {
    }

    public Person(String firstName, String lastName, MaritalStatus maritalStatus) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.maritalStatus = maritalStatus;
    }

    @JsonIgnore
    public Long getId() {
        return id;
    }

    @XmlElement
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @XmlElement
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @XmlElement
    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    @JsonIgnore
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @JsonIgnore
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    @JsonIgnore
    public MaritalStatus getOriginalMaritalStatus() {
        return originalMaritalStatus;
    }

    public void setOriginalMaritalStatus(MaritalStatus originalMaritalStatus) {
        this.originalMaritalStatus = originalMaritalStatus;
    }

}
