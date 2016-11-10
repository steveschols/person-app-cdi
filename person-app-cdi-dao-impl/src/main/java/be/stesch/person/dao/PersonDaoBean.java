package be.stesch.person.dao;

import be.stesch.person.model.Person;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Steve Schols
 * @since 28/08/2015
 */
@Dependent
public class PersonDaoBean implements PersonDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void persist(Person person) {
        entityManager.persist(person);
    }

    public Person find(Long id) {
        return entityManager.find(Person.class, id);
    }

    public Person merge(Person person) {
        return entityManager.merge(person);
    }

}