package dev.jaffer.demonotificationservice.notificationprocessingchain.handlers;

import dev.jaffer.demonotificationservice.entity.Notification;
import dev.jaffer.demonotificationservice.notificationprocessingchain.AbstractNotificationHandler;
import dev.jaffer.demonotificationservice.notificationprocessingchain.NotificationHandler;
import org.springframework.stereotype.Component;

@Component
public class Validator extends AbstractNotificationHandler {


    @Override
    public void handle(Notification notification) {
        // Perform validation logic here : if userIds exist in the database (in our case this is mostly true)
        // For example:
        if (isValid(notification)) {
            // If valid fetch user details from the database and modify the notification
            passToNext(notification);
        } else {
            // Handle invalid notification (log error, send to dead-letter queue)
            System.err.println("Invalid notification: " + notification);
        }
    }

    private boolean isValid(Notification notification) {
        // check if userIds exist in the database users table
        return true; // Placeholder
    }
}