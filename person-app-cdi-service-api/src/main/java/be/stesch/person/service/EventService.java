package be.stesch.person.service;

import be.stesch.person.model.event.MaritalStatusChangeEvent;

/**
 * @author Steve Schols
 * @since 1/09/2015
 */
public interface EventService {

    void publishMaritalStatusChangeEvent(MaritalStatusChangeEvent maritalStatusChangeEvent);

}
