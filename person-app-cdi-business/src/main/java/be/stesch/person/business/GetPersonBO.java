package be.stesch.person.business;

import be.stesch.person.model.Person;
import be.stesch.person.service.PersonService;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import static java.lang.Long.valueOf;

/**
 * Created by Steve Schols on 11/9/2016.
 */
@Dependent
public class GetPersonBO implements BusinessObject<Person> {

    @Inject
    private PersonService personService;

    private String id;

    @Override
    public Person onExecute() throws Exception {
        return personService.findPerson(valueOf(id));
    }

    public void setId(String id) {
        this.id = id;
    }

}
