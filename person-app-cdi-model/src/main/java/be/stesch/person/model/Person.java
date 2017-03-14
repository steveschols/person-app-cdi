package be.stesch.person.model;

import com.google.common.annotations.VisibleForTesting;

import javax.persistence.*;
import java.io.Serializable;

import static javax.persistence.EnumType.STRING;

/**
 * @author Steve Schols
 * @since 27/08/2015
 */
@Entity
public class Person extends AbstractAuditedEntity implements Identifiable<Long> {

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

    @Override
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

    public MaritalStatus getOriginalMaritalStatus() {
        return originalMaritalStatus;
    }

    public void setOriginalMaritalStatus(MaritalStatus originalMaritalStatus) {
        this.originalMaritalStatus = originalMaritalStatus;
    }

    @PostLoad
    public void retainOriginalMaritalStatus() {
        setOriginalMaritalStatus(getMaritalStatus());
    }

}
