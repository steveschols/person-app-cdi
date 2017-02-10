package be.stesch.person.dao;

import be.stesch.person.model.Person;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static javax.ejb.TransactionAttributeType.SUPPORTS;

/**
 * @author Steve Schols
 * @since 28/08/2015
 */
@Stateless
@Local(PersonDao.class)
@TransactionAttribute(SUPPORTS)
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