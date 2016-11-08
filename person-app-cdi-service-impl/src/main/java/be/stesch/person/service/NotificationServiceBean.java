package be.stesch.person.service;

import be.stesch.person.common.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;

/**
 * @author Steve Schols
 * @since 1/09/2015
 */
@Dependent
public class NotificationServiceBean implements NotificationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationServiceBean.class);

    public void publishNotification(@Observes Notification notification) {
        LOGGER.info("External system notified: {}", notification.getNotification());
        notification.setSent(true);
    }

}
