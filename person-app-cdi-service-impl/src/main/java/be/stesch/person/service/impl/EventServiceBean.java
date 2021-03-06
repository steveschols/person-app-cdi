package be.stesch.person.service.impl;

import be.stesch.person.model.event.MaritalStatusChangeEvent;
import be.stesch.person.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.enterprise.event.Observes;

/**
 * @author Steve Schols
 * @since 1/09/2015
 */
@Stateless
@Local(EventService.class)
public class EventServiceBean implements EventService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventServiceBean.class);

    public void publishMaritalStatusChangeEvent(@Observes MaritalStatusChangeEvent maritalStatusChangeEvent) {
        LOGGER.info("External system notified: {}", maritalStatusChangeEvent.getNotification());
    }

}
