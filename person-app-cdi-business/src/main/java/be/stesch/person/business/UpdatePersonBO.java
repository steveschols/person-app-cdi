package be.stesch.person.business;

import be.stesch.person.model.Person;
import be.stesch.person.model.event.MaritalStatusChangeEvent;
import be.stesch.person.service.PersonService;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import static java.lang.Long.valueOf;

/**
 * Created by Steve Schols on 11/9/2016.
 */
@Dependent
public class UpdatePersonBO implements BusinessObject<Person> {

    @Inject
    private PersonService personService;
    @Inject
    private Event<MaritalStatusChangeEvent> notificationEvent;

    private String id;
    private Person person;

    @Override
    public Person onExecute() throws Exception {
        Person personToUpdate = personService.findPerson(valueOf(id));
        personToUpdate.setFirstName(person.getFirstName());
        personToUpdate.setLastName(person.getLastName());
        personToUpdate.setMaritalStatus(person.getMaritalStatus());

        Person updatedPerson = personService.updatePerson(personToUpdate);

        if (!personToUpdate.getMaritalStatus().equals(personToUpdate.getOriginalMaritalStatus())) {
            notificationEvent.fire(new MaritalStatusChangeEvent(personToUpdate));
        }

        return updatedPerson;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
