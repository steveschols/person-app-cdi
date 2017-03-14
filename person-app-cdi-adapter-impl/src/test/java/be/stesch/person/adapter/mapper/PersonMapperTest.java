package be.stesch.person.adapter.mapper;

import be.stesch.person.model.Person;
import be.stesch.person.person.v1.PersonType;
import org.junit.Test;

import static be.stesch.person.common.web.PersonAppURIFactory.getPersonUri;
import static be.stesch.person.model.MaritalStatus.SINGLE;
import static be.stesch.person.model.MaritalStatus.valueOf;
import static be.stesch.person.model.PersonTestData.createPerson;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mapstruct.factory.Mappers.getMapper;

/**
 * Created by u420643 on 11/15/2016.
 */
public class PersonMapperTest {

    private PersonMapper personMapper = getMapper(PersonMapper.class);

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
        Person person = createPerson(1L, "John", "Doe", SINGLE);

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