package be.stesch.person.model;

/**
 * Created by u420643 on 3/14/2017.
 */
public final class PersonTestData {

    private PersonTestData() {
    }

    public static Person createPerson(Long id, String firstName, String lastName, MaritalStatus maritalStatus) {
        Person person = new Person();
        person.setId(id);
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setMaritalStatus(maritalStatus);

        return person;
    }

}
