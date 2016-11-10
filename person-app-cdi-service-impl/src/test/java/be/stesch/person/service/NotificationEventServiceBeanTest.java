package be.stesch.person.service;

import be.stesch.person.model.Person;
import be.stesch.person.model.event.MaritalStatusChangeEvent;
import be.stesch.person.service.impl.EventServiceBean;
import org.junit.Test;

import static be.stesch.person.model.MaritalStatus.MARRIED;

/**
 * @author Steve Schols
 * @since 3/09/2015
 */
public class NotificationEventServiceBeanTest {

    @Test
    public void testPublishNotification() throws Exception {
        Person person = new Person(1L, "John", "Doe", MARRIED);
        MaritalStatusChangeEvent maritalStatusChangeEvent = new MaritalStatusChangeEvent(person);

        new EventServiceBean().publishMaritalStatusChangeEvent(maritalStatusChangeEvent);
    }

}
