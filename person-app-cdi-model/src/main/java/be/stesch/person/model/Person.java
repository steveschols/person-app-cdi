package be.stesch.person.model;

import be.stesch.person.model.listener.AuditEntityListener;
import be.stesch.person.model.listener.OriginalStateEntityListener;
import com.google.common.annotations.VisibleForTesting;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.jboss.resteasy.annotations.providers.jaxb.IgnoreMediaTypes;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.net.URI;
import java.util.Date;

import static be.stesch.person.common.URIFactory.getPersonUri;
import static javax.persistence.EnumType.STRING;

/**
 * @author Steve Schols
 * @since 27/08/2015
 */
@Entity
@EntityListeners({AuditEntityListener.class, OriginalStateEntityListener.class})
@XmlRootElement
public class Person implements Auditable, UriGenerating, Serializable {

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

    @VisibleForTesting
    public Person(Long id, String firstName, String lastName, MaritalStatus maritalStatus) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.maritalStatus = maritalStatus;
    }

    @JsonIgnore
    @XmlTransient
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
    }

    @JsonIgnore
    @XmlTransient
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @JsonIgnore
    @XmlTransient
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    @JsonIgnore
    @XmlTransient
    public MaritalStatus getOriginalMaritalStatus() {
        return originalMaritalStatus;
    }

    public void setOriginalMaritalStatus(MaritalStatus originalMaritalStatus) {
        this.originalMaritalStatus = originalMaritalStatus;
    }

    @Override
    @JsonIgnore
    @XmlTransient
    public URI getUri() {
        return getPersonUri(id);
    }

}
