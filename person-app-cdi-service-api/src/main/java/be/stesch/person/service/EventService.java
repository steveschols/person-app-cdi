package be.stesch.person.service;

import be.stesch.person.model.event.MaritalStatusChangeEvent;

import javax.ejb.Local;

/**
 * @author Steve Schols
 * @since 1/09/2015
 */
@Local
public interface EventService {

    void publishMaritalStatusChangeEvent(MaritalStatusChangeEvent maritalStatusChangeEvent);

}
