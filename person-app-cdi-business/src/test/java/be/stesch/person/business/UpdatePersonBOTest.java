package be.stesch.person.business;

import be.stesch.person.model.Person;
import be.stesch.person.model.event.MaritalStatusChangeEvent;
import be.stesch.person.service.PersonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.enterprise.event.Event;

import static be.stesch.person.model.MaritalStatus.MARRIED;
import static be.stesch.person.model.MaritalStatus.SINGLE;
import static java.lang.Long.valueOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

/**
 * Created by Steve Schols on 11/9/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class UpdatePersonBOTest {

    @Mock
    private PersonService personService;
    @Mock
    private Event<MaritalStatusChangeEvent> notificationEvent;

    @InjectMocks
    private UpdatePersonBO updatePersonBO;

    @Test
    public void testUpdatePersonUnmodifiedMaritalStatus() throws Exception {
        String id = "1";
        Person person = new Person("Test", "Person", SINGLE);
        person.setOriginalMaritalStatus(SINGLE);

        when(personService.findPerson(valueOf(id))).thenReturn(person);
        when(personService.updatePerson(person)).thenReturn(person);

        updatePersonBO.setId(id);
        updatePersonBO.setPerson(new Person("John", "Doe", SINGLE));
        Person updatedPerson = updatePersonBO.execute();

        verify(personService).findPerson(valueOf(id));
        verify(personService).updatePerson(person);
        verify(notificationEvent, never()).fire(isA(MaritalStatusChangeEvent.class));

        assertThat(updatedPerson.getFirstName(), is("John"));
        assertThat(updatedPerson.getLastName(), is("Doe"));
        assertThat(updatedPerson.getMaritalStatus(), is(SINGLE));
    }

    @Test
    public void testUpdatePersonModifiedMaritalStatus() throws Exception {
        String id = "1";
        Person person = new Person("Test", "Person", SINGLE);
        person.setOriginalMaritalStatus(SINGLE);

        when(personService.findPerson(valueOf(id))).thenReturn(person);
        when(personService.updatePerson(person)).thenReturn(person);

        updatePersonBO.setId(id);
        updatePersonBO.setPerson(new Person("John", "Doe", MARRIED));
        Person updatedPerson = updatePersonBO.execute();

        verify(personService).findPerson(valueOf(id));
        verify(personService).updatePerson(person);
        verify(notificationEvent).fire(isA(MaritalStatusChangeEvent.class));

        assertThat(updatedPerson.getFirstName(), is("John"));
        assertThat(updatedPerson.getLastName(), is("Doe"));
        assertThat(updatedPerson.getMaritalStatus(), is(MARRIED));
    }

}