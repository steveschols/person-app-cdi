package be.stesch.person.dao;

import be.stesch.person.model.Person;

import javax.ejb.Local;

/**
 * @author Steve Schols
 * @since 28/08/2015
 */
@Local
public interface PersonDao {

    void persist(Person person);

    Person find(Long id);

    Person merge(Person person);

}
