package be.stesch.person.business;

import be.stesch.person.common.exception.BusinessException;
import be.stesch.person.model.Person;
import be.stesch.person.service.PersonService;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

/**
 * Created by Steve Schols on 11/9/2016.
 */
@Dependent
public class CreatePersonBO implements BusinessObject<Long> {

    @Inject
    private PersonService personService;

    private Person person;

    @Override
    public Long onExecute() throws BusinessException {
        return personService.createPerson(person);
    }

    public void setPerson(Person person) {
        this.person = person;
    }

}
