package be.stesch.person.service;

import be.stesch.person.common.Notification;

/**
 * @author Steve Schols
 * @since 1/09/2015
 */
public interface NotificationService {

    void publishNotification(Notification notification);

}
