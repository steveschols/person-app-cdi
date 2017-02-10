package be.stesch.person.business;

import be.stesch.person.common.exception.BusinessException;
import be.stesch.person.model.Person;
import be.stesch.person.service.PersonService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 * Created by Steve Schols on 11/9/2016.
 */
@RequestScoped
public class GetPersonBO implements BusinessObject<Person> {

    @Inject
    private PersonService personService;

    private Long personId;

    @Override
    public Person onExecute() throws BusinessException {
        return personService.findPerson(personId);
    }

    public void setId(Long personId) {
        this.personId = personId;
    }

}
