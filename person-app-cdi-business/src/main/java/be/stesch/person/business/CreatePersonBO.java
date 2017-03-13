package be.stesch.person.business;

import be.stesch.person.common.exception.BusinessException;
import be.stesch.person.model.Person;
import be.stesch.person.service.PersonService;
import com.google.common.annotations.VisibleForTesting;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

/**
 * Created by Steve Schols on 11/9/2016.
 */
@RequestScoped
public class CreatePersonBO {

    @Inject
    private PersonService personService;

    public Long createPerson(Person person) throws BusinessException {
        return personService.createPerson(person);
    }

}
