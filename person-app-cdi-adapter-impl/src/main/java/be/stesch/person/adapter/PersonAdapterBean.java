package be.stesch.person.adapter;

import be.stesch.person.adapter.mapper.PersonMapper;
import be.stesch.person.business.CreatePersonBO;
import be.stesch.person.business.GetPersonBO;
import be.stesch.person.business.UpdatePersonBO;
import be.stesch.person.model.Person;
import be.stesch.person.person.v1.PersonType;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.inject.Inject;

import static javax.ejb.TransactionAttributeType.NEVER;

/**
 * Created by u420643 on 11/15/2016.
 */
@Stateless
@Local(PersonAdapter.class)
public class PersonAdapterBean implements PersonAdapter {

    @Inject
    private CreatePersonBO createPersonBO;
    @Inject
    private UpdatePersonBO updatePersonBO;
    @Inject
    private GetPersonBO getPersonBO;

    @Inject
    private PersonMapper personMapper;

    @Override
    public Long createPerson(PersonType personType) {
        createPersonBO.setPerson(personMapper.mapToDomain(personType));

        return createPersonBO.execute();
    }

    @Override
    public Long updatePerson(Long personId, PersonType personType) {
        updatePersonBO.setPersonId(personId);
        updatePersonBO.setPerson(personMapper.mapToDomain(personType));

        return updatePersonBO.execute();
    }

    @Override
    @TransactionAttribute(NEVER)
    public PersonType getPerson(Long personId) {
        getPersonBO.setId(personId);
        Person person = getPersonBO.execute();

        return personMapper.mapToResource(person);
    }

}
