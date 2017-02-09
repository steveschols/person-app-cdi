package be.stesch.person.adapter;

import be.stesch.person.person.v1.PersonType;

import javax.ejb.Local;

/**
 * Created by u420643 on 11/15/2016.
 */
@Local
public interface PersonAdapter {

    Long createPerson(PersonType personType);

    Long updatePerson(Long personId, PersonType personType);

    PersonType getPerson(Long personId);

}
