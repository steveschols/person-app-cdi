package be.stesch.person.model;

import be.stesch.person.common.Notification;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.enterprise.event.Event;
import javax.enterprise.inject.spi.BeanManager;

import static be.stesch.person.model.MaritalStatus.MARRIED;
import static be.stesch.person.model.MaritalStatus.SINGLE;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * @author Steve Schols
 * @since 3/09/2015
 */
@RunWith(MockitoJUnitRunner.class)
public class PersonTest {

    @Mock
    private Event<Notification> notificationEvent;
    @Mock
    private BeanManager beanManager;

    @Test
    public void testSetMaritalStatusWithChangeNotifiesObserver() {
        Person person = new Person("Test", "Person", SINGLE);
        // Set by EntityListener
        person.setOriginalMaritalStatus(SINGLE);
        person.setNotificationEvent(notificationEvent);
        person.setBeanManager(beanManager);
        person.setMaritalStatus(MARRIED);
        //verify(observer).notifyObserver(person);
//        verify(notificationEvent).fire(isA(Notification.class));
        verify(beanManager).fireEvent(isA(Notification.class));
    }

    @Test
    public void testSetMaritalStatusWithoutChangeDoesNotNotifyObserver() throws Exception {
        Person person = new Person("Test", "Person", SINGLE);
        // Set by EntityListener
        person.setOriginalMaritalStatus(SINGLE);

        person.setMaritalStatus(SINGLE);
        //verify(observer, never()).notifyObserver(person);
        verify(notificationEvent, never()).fire(isA(Notification.class));
    }

}