package be.stesch.person.dao;

import be.stesch.person.model.Person;

/**
 * @author Steve Schols
 * @since 28/08/2015
 */
public interface PersonDao {

    void persist(Person person);

    Person find(Long id);

    Person merge(Person person);

}
