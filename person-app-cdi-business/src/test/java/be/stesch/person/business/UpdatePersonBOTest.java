package be.stesch.person.business;

import be.stesch.person.common.exception.BusinessException;
import be.stesch.person.model.Person;
import be.stesch.person.model.event.MaritalStatusChangeEvent;
import be.stesch.person.service.PersonService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.enterprise.event.Event;

import static be.stesch.person.model.MaritalStatus.MARRIED;
import static be.stesch.person.model.MaritalStatus.SINGLE;
import static be.stesch.person.model.PersonTestData.createPerson;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.rules.ExpectedException.none;
import static org.mockito.Mockito.*;

/**
 * Created by Steve Schols on 11/9/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class UpdatePersonBOTest {

    @Rule
    public ExpectedException expectedException = none();

    @Mock
    private PersonService personService;
    @Mock
    private Event<MaritalStatusChangeEvent> notificationEvent;

    @InjectMocks
    private UpdatePersonBO updatePersonBO;

    @Test
    public void testUpdatePersonUnmodifiedMaritalStatus() throws Exception {
        Long personId = 1L;

        Person personToUpdate = createPerson(personId, "Test", "Person", SINGLE);
        personToUpdate.setOriginalMaritalStatus(SINGLE);

        Person updatedPerson = createPerson(personId, "John", "Doe", SINGLE);

        when(personService.findPerson(personId)).thenReturn(personToUpdate);
        when(personService.updatePerson(personToUpdate)).thenReturn(updatedPerson);

        Long updatedPersonId = updatePersonBO.updatePerson(personId, updatedPerson);

        verify(personService).findPerson(personId);
        verify(personService).updatePerson(personToUpdate);
        verify(notificationEvent, never()).fire(isA(MaritalStatusChangeEvent.class));

        assertThat(updatedPersonId, is(personId));
    }

    @Test
    public void testUpdatePersonModifiedMaritalStatus() throws Exception {
        Long personId = 1L;

        Person personToUpdate = createPerson(personId, "Test", "Person", SINGLE);
        personToUpdate.setOriginalMaritalStatus(SINGLE);

        Person updatedPerson = createPerson(personId, "John", "Doe", MARRIED);

        when(personService.findPerson(personId)).thenReturn(personToUpdate);
        when(personService.updatePerson(personToUpdate)).thenReturn(updatedPerson);

        Long updatedPersonId = updatePersonBO.updatePerson(personId, updatedPerson);

        verify(personService).findPerson(personId);
        verify(personService).updatePerson(personToUpdate);
        verify(notificationEvent).fire(isA(MaritalStatusChangeEvent.class));

        assertThat(updatedPersonId, is(personId));
    }

    @Test
    public void testUpdatePersonNotFound() throws Exception {
        Long personId = 1L;

        Person updatedPerson = createPerson(personId, "John", "Doe", MARRIED);

        when(personService.findPerson(personId)).thenReturn(null);
        expectedException.expect(BusinessException.class);

        updatePersonBO.updatePerson(personId, updatedPerson);
    }

}
