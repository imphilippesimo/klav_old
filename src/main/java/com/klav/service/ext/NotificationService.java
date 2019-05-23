package com.klav.service.ext;

/**
 * Interface use to push notifications out of the system.
 */
public interface NotificationService {

    void pushAccountCreation(NotificationContext notificationContext);
}
