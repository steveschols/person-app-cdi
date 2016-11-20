package be.stesch.person.adapter.mapper;

import be.stesch.person.model.Person;
import be.stesch.person.person.v1.PersonType;

import javax.enterprise.context.Dependent;

import static be.stesch.person.common.web.PersonAppURIFactory.getPersonUri;
import static be.stesch.person.model.MaritalStatus.valueOf;

/**
 * Created by u420643 on 11/15/2016.
 */
@Dependent
public class PersonMapper {

    public Person mapToDomain(PersonType personType) {
        Person person = new Person();
        person.setFirstName(personType.getFirstName());
        person.setLastName(personType.getLastName());
        person.setMaritalStatus(valueOf(personType.getMaritalStatus()));

        return person;
    }

    public PersonType mapToResource(Person person) {
        if (person != null) {
            return new PersonType()
                    .withFirstName(person.getFirstName())
                    .withLastName(person.getLastName())
                    .withMaritalStatus(person.getMaritalStatus().name())
                    .withUri(getPersonUri(person.getId()).toString());
        }
        return null;
    }

}
