package be.stesch.person.adapter.mapper;

import be.stesch.person.model.Person;
import be.stesch.person.person.v1.PersonType;
import org.junit.Test;

import static be.stesch.person.common.web.PersonAppURIFactory.getPersonUri;
import static be.stesch.person.model.MaritalStatus.SINGLE;
import static be.stesch.person.model.MaritalStatus.valueOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by u420643 on 11/15/2016.
 */
public class PersonMapperTest {

    PersonMapper personMapper = new PersonMapper();

    @Test
    public void testMapToDomain() throws Exception {
        PersonType personType = new PersonType()
                .withFirstName("John")
                .withLastName("Doe")
                .withMaritalStatus("SINGLE");

        Person person = personMapper.mapToDomain(personType);

        assertThat(person.getId(), is(nullValue()));
        assertThat(person.getFirstName(), is(personType.getFirstName()));
        assertThat(person.getLastName(), is(personType.getLastName()));
        assertThat(person.getMaritalStatus(), is(valueOf(personType.getMaritalStatus())));
    }

    @Test
    public void testMapToResource() throws Exception {
        Person person = new Person(1L, "John", "Doe", SINGLE);

        PersonType personType = personMapper.mapToResource(person);

        assertThat(personType.getFirstName(), is(person.getFirstName()));
        assertThat(personType.getLastName(), is(person.getLastName()));
        assertThat(personType.getMaritalStatus(), is(person.getMaritalStatus().name()));
        assertThat(personType.getUri(), is(getPersonUri(person.getId()).toString()));
    }

    @Test
    public void testMapToResourceNotFound() throws Exception {
        PersonType personType = personMapper.mapToResource(null);

        assertThat(personType, is(nullValue()));
    }
}