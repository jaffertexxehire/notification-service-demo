package dev.jaffer.demonotificationservice.notificationprocessingchain.handlers;

import dev.jaffer.demonotificationservice.entity.Notification;
import dev.jaffer.demonotificationservice.notificationprocessingchain.AbstractNotificationHandler;

public class Prioritizer extends AbstractNotificationHandler {
    @Override
    public void handle(Notification notification) {

        //use try catch to handle exceptions

        // if priority already set, skip this step and forward to next handler

        passToNext(notification);

        // if priority not set, set the priority based on notification type and other factors
        // pass the event to the next handler in the chain

    }
}
