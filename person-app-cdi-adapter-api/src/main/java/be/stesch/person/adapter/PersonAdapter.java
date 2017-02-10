package be.stesch.person.adapter;

import be.stesch.person.person.v1.PersonType;

/**
 * Created by u420643 on 11/15/2016.
 */
public interface PersonAdapter {

    Long createPerson(PersonType personType);

    Long updatePerson(Long personId, PersonType personType);

    PersonType getPerson(Long personId);

}
