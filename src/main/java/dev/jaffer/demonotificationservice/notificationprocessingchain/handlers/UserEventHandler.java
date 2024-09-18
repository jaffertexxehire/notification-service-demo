package dev.jaffer.demonotificationservice.notificationprocessingchain.handlers;

import dev.jaffer.demonotificationservice.entity.Notification;
import dev.jaffer.demonotificationservice.notificationprocessingchain.AbstractNotificationHandler;
import dev.jaffer.demonotificationservice.notificationprocessingchain.NotificationHandler;
import org.springframework.stereotype.Component;

/*
    * This class is responsible for handling notifications of type USER_
    * It saves the user details to the database for the
    * first time a user is created or when a user updates
    * their details
 */
@Component
public class UserEventHandler extends AbstractNotificationHandler {





    @Override
    public void handle(Notification notification) {


        String notificationType = notification.getNotificationType();
        if(notificationType.contains("USER")) {

            //if the user is registered for the first time create new user out of the notification details
            System.out.println("UserEventHandler: Saving user details to the database");

            //pass the notification to the next handler in the chain


        }
        passToNext(notification);


    }


}
