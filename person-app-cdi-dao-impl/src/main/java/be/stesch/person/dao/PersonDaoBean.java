package be.stesch.person.dao;

import be.stesch.person.model.Person;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;

import static javax.ejb.TransactionAttributeType.SUPPORTS;

/**
 * @author Steve Schols
 * @since 28/08/2015
 */
@Stateless
@Local(PersonDao.class)
@TransactionAttribute(SUPPORTS)
public class PersonDaoBean extends GenericDaoBean<Person, Long> implements PersonDao {

    public PersonDaoBean() {
        super(Person.class);
    }

}