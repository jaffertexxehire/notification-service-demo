package dev.jaffer.demonotificationservice.notificationprocessingchain.handlers;

import dev.jaffer.demonotificationservice.entity.Event;
import dev.jaffer.demonotificationservice.entity.Notification;
import dev.jaffer.demonotificationservice.notificationprocessingchain.AbstractNotificationHandler;
import org.springframework.beans.factory.annotation.Autowired;

public class Validator extends AbstractNotificationHandler {

    //validate user and fetch user details
    @Autowired
    private UserRepository userRepository;


    @Override
    public void handle(Notification notification) {

        //use try catch to handle exceptions

        // if the user is not found, throw an exception and stop the chain

        // if the user is found, set the user details in the notification object attach the user object to the notification
        // and call the next handler in the chain
        //and stop the chain


        s....

    }
}
