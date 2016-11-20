package be.stesch.person.business;

import be.stesch.person.model.Person;
import be.stesch.person.service.PersonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static be.stesch.person.model.MaritalStatus.MARRIED;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Steve Schols on 11/9/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class CreatePersonBOTest {

    @Mock
    private PersonService personService;

    @InjectMocks
    private CreatePersonBO createPersonBO;

    @Test
    public void testCreatePerson() throws Exception {
        Long personId = 1L;
        Person person = new Person(null, "John", "Doe", MARRIED);

        createPersonBO.setPerson(person);

        when(personService.createPerson(person)).thenReturn(personId);

        Long createdPersonId = createPersonBO.execute();

        verify(personService).createPerson(person);
        assertThat(createdPersonId, is(personId));
    }
}