package be.stesch.person.web.servlet;

import be.stesch.person.common.Notification;
import be.stesch.person.model.Person;
import be.stesch.person.service.PersonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.enterprise.event.Event;
import javax.enterprise.inject.spi.BeanManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

import static be.stesch.person.model.MaritalStatus.SINGLE;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Steve Schols
 * @since 4/09/2015
 */
@RunWith(MockitoJUnitRunner.class)
public class PersonAppServletTest {

    @Mock
    private Event<Notification> notificationEvent;
    @Mock
    private BeanManager beanManager;
    @Mock
    private PersonService personService;
    @Mock
    private HttpServletRequest httpServletRequest;
    @Mock
    private HttpServletResponse httpServletResponse;
    @Mock
    private PrintWriter printWriter;

    @InjectMocks
    private PersonAppServlet personAppServlet;

    @Test
    public void testDoGet() throws Exception {
        Person person = new Person("Test", "Person", SINGLE);
        person.setNotificationEvent(notificationEvent);
        person.setBeanManager(beanManager);

        when(httpServletResponse.getWriter()).thenReturn(printWriter);
        when(personService.findPerson(person.getId())).thenReturn(person);

        personAppServlet.doGet(httpServletRequest, httpServletResponse);

        verify(personService).createPerson(isA(Person.class));
        verify(personService).updatePerson(isA(Person.class));
        verify(httpServletResponse).getWriter();
    }

}
