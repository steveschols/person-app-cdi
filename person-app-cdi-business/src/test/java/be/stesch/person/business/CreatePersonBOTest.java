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
        Person person = new Person("John", "Doe", MARRIED);

        createPersonBO.setPerson(person);
        Person createdPerson = createPersonBO.execute();

        verify(personService).createPerson(person);
        assertThat(createdPerson, is(person));
    }
}