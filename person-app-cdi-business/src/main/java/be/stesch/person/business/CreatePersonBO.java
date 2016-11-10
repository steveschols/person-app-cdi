package be.stesch.person.business;

import be.stesch.person.model.Person;
import be.stesch.person.service.PersonService;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

/**
 * Created by Steve Schols on 11/9/2016.
 */
@Dependent
public class CreatePersonBO implements BusinessObject<Person> {

    @Inject
    private PersonService personService;

    private Person person;

    @Override
    public Person onExecute() throws Exception {
        personService.createPerson(person);

        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

}
