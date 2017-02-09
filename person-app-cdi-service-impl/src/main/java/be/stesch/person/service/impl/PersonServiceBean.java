package be.stesch.person.service.impl;

import be.stesch.person.dao.PersonDao;
import be.stesch.person.model.Person;
import be.stesch.person.service.PersonService;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * @author Steve Schols
 * @since 3/09/2015
 */
@Stateless
public class PersonServiceBean implements PersonService {

    @Inject
    private PersonDao personDao;

    @Override
    public Long createPerson(Person person) {
        personDao.persist(person);

        return person.getId();
    }

    @Override
    public Person findPerson(Long id) {
        return personDao.find(id);
    }

    @Override
    public Person updatePerson(Person person) {
        return personDao.merge(person);
    }

}
