package be.stesch.person.business;

import be.stesch.person.common.exception.BusinessException;
import be.stesch.person.model.Person;
import be.stesch.person.model.event.MaritalStatusChangeEvent;
import be.stesch.person.service.PersonService;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 * Created by Steve Schols on 11/9/2016.
 */
@Dependent
public class UpdatePersonBO implements BusinessObject<Long> {

    @Inject
    private PersonService personService;
    @Inject
    private Event<MaritalStatusChangeEvent> notificationEvent;

    private Long personId;
    private Person person;

    @Override
    @Transactional
    public Long onExecute() throws BusinessException {
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

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

}
