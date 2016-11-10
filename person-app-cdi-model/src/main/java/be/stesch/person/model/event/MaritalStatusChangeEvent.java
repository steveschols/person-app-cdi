package be.stesch.person.model.event;

import be.stesch.person.model.Person;

import static java.lang.String.format;

/**
 * @author Steve Schols
 * @since 1/09/2015
 */
public class MaritalStatusChangeEvent {

    private static final String NOTIFICATION_STRING = "Person ID %s, %s %s changed marital status from %s to %s";

    private Person person;

    public MaritalStatusChangeEvent(Person person) {
        this.person = person;
    }

    public String getNotification() {
        return format(NOTIFICATION_STRING,
                person.getId(),
                person.getFirstName(),
                person.getLastName(),
                person.getOriginalMaritalStatus(),
                person.getMaritalStatus());
    }

}
