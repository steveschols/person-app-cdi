package be.stesch.person.business;

import be.stesch.person.common.exception.BusinessException;
import be.stesch.person.model.Person;
import be.stesch.person.model.event.MaritalStatusChangeEvent;
import be.stesch.person.service.PersonService;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;

/**
 * Created by Steve Schols on 11/9/2016.
 */
@RequestScoped
public class UpdatePersonBO {

    @Inject
    private PersonService personService;
    @Inject
    private Event<MaritalStatusChangeEvent> notificationEvent;

    public Long updatePerson(Long personId, Person person) throws BusinessException {
        Person personToUpdate = personService.findPerson(personId);
        if (personToUpdate == null) {
            throw new BusinessException("Person with ID " + personId + " not found!");
        }

        personToUpdate.setFirstName(person.getFirstName());
        personToUpdate.setLastName(person.getLastName());
        personToUpdate.setMaritalStatus(person.getMaritalStatus());

        Person updatedPerson = personService.updatePerson(personToUpdate);

        if (!updatedPerson.getMaritalStatus().equals(personToUpdate.getOriginalMaritalStatus())) {
            notificationEvent.fire(new MaritalStatusChangeEvent(personToUpdate));
        }

        return updatedPerson.getId();
    }

}
