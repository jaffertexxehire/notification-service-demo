package dev.jaffer.demonotificationservice.notificationprocessingchain.handlers;

import dev.jaffer.demonotificationservice.entity.Notification;
import dev.jaffer.demonotificationservice.notificationprocessingchain.NotificationHandler;

public class SqsQueueManager implements NotificationHandler {
    @Override
    public void handle(Notification notification) {
        //use try catch to handle exceptions

        //first attempt to send the notification to the SQS queue
}
