package be.stesch.person.adapter;

import be.stesch.person.adapter.mapper.PersonMapper;
import be.stesch.person.business.CreatePersonBO;
import be.stesch.person.business.GetPersonBO;
import be.stesch.person.business.UpdatePersonBO;
import be.stesch.person.model.Person;
import be.stesch.person.person.v1.PersonType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static be.stesch.person.model.MaritalStatus.SINGLE;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by u420643 on 11/15/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class PersonAdapterBeanTest {

    @Mock
    private CreatePersonBO createPersonBO;
    @Mock
    private UpdatePersonBO updatePersonBO;
    @Mock
    private GetPersonBO getPersonBO;

    @Mock
    private PersonMapper personMapper;

    @InjectMocks
    private PersonAdapterBean personAdapterBean;

    @Test
    public void createPerson() throws Exception {
        Long personId = 1L;
        PersonType personType = new PersonType()
                .withFirstName("John")
                .withLastName("Doe")
                .withMaritalStatus("SINGLE");

        when(createPersonBO.execute()).thenReturn(personId);

        Long createdPersonId = personAdapterBean.createPerson(personType);

        verify(createPersonBO).execute();
        verify(personMapper).mapToDomain(personType);
        assertThat(createdPersonId, is(personId));
    }

    @Test
    public void updatePerson() throws Exception {
        Long personId = 1L;
        PersonType personType = new PersonType()
                .withFirstName("John")
                .withLastName("Doe")
                .withMaritalStatus("SINGLE");

        when(updatePersonBO.execute()).thenReturn(personId);

        Long updatedPersonId = personAdapterBean.updatePerson(personId, personType);

        verify(updatePersonBO).execute();
        verify(personMapper).mapToDomain(personType);
        assertThat(updatedPersonId, is(personId));
    }

    @Test
    public void getPerson() throws Exception {
        Long personId = 1L;

        Person person = new Person(1L, "John", "Doe", SINGLE);
        PersonType personType = new PersonType()
                .withFirstName("John")
                .withLastName("Doe")
                .withMaritalStatus("SINGLE");

        when(getPersonBO.execute()).thenReturn(person);
        when(personMapper.mapToResource(person)).thenReturn(personType);

        PersonType returnedPerson = personAdapterBean.getPerson(personId);

        verify(getPersonBO).execute();
        verify(personMapper).mapToResource(person);
        assertThat(returnedPerson, is(personType));
    }

}