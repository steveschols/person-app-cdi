package be.stesch.person.business;

import be.stesch.person.model.Person;
import be.stesch.person.service.PersonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static be.stesch.person.model.MaritalStatus.MARRIED;
import static java.lang.Long.valueOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
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
        String id = "1";
        Person person = new Person("John", "Doe", MARRIED);

        when(personService.findPerson(valueOf(id))).thenReturn(person);

        getPersonBO.setId(id);
        Person foundPerson = getPersonBO.execute();

        verify(personService).findPerson(valueOf(id));

        assertThat(foundPerson, is(person));
    }
}