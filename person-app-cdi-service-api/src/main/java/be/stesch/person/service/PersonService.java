package be.stesch.person.service;

import be.stesch.person.model.Person;

import javax.ejb.Local;
import java.io.Serializable;

/**
 * @author Steve Schols
 * @since 3/09/2015
 */
@Local
public interface PersonService {

    <T extends Serializable> T createPerson(Person person);

    Person findPerson(Long id);

    Person updatePerson(Person person);

}
