package be.stesch.person.service.impl;

import be.stesch.person.dao.PersonDao;
import be.stesch.person.model.Person;
import be.stesch.person.service.PersonService;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.Serializable;

/**
 * @author Steve Schols
 * @since 3/09/2015
 */
@Dependent
public class PersonServiceBean implements PersonService {

    @Inject
    private PersonDao personDao;

    @Override
    @Transactional
    public Serializable createPerson(Person person) {
        personDao.persist(person);

        return person.getId();
    }

    @Override
    public Person findPerson(Long id) {
        return personDao.find(id);
    }

    @Override
    @Transactional
    public Person updatePerson(Person person) {
        return personDao.merge(person);
    }

}
