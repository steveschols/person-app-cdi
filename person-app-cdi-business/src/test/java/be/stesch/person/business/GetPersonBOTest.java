package be.stesch.person.business;

import be.stesch.person.model.Person;
import be.stesch.person.service.PersonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static be.stesch.person.model.MaritalStatus.MARRIED;
import static be.stesch.person.model.PersonTestData.createPerson;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Steve Schols on 11/9/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetPersonBOTest {

    @Mock
    private PersonService personService;

    @InjectMocks
    private GetPersonBO getPersonBO;

    @Test
    public void testGetPerson() throws Exception {
        Long personId = 1L;
        Person person = createPerson(personId, "John", "Doe", MARRIED);

        when(personService.findPerson(personId)).thenReturn(person);

        Person foundPerson = getPersonBO.getPerson(personId);

        verify(personService).findPerson(personId);

        assertThat(foundPerson.getId(), is(personId));
        assertThat(foundPerson.getFirstName(), is("John"));
        assertThat(foundPerson.getLastName(), is("Doe"));
        assertThat(foundPerson.getMaritalStatus(), is(MARRIED));
    }

}
