package be.stesch.person.model.listener;

import be.stesch.person.model.Person;

import javax.persistence.PostLoad;

/**
 * @author Steve Schols
 * @since 3/09/2015
 */
public class OriginalStateEntityListener {

    @PostLoad
    public void retainOriginalState(Object object) {
        if (object instanceof Person) {
            Person person = (Person) object;
            person.setOriginalMaritalStatus(person.getMaritalStatus());
        }
    }

}
