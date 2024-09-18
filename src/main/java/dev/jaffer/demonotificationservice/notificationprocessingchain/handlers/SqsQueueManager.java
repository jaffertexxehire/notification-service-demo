package dev.jaffer.demonotificationservice.notificationprocessingchain.handlers;

import dev.jaffer.demonotificationservice.entity.Notification;
import dev.jaffer.demonotificationservice.notificationprocessingchain.NotificationHandler;
import dev.jaffer.demonotificationservice.repositories.EventRepository;
import dev.jaffer.demonotificationservice.services.EventService;
import dev.jaffer.demonotificationservice.services.SqsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class SqsQueueManager implements NotificationHandler {

    private final EventService eventService;
    private final SqsService sqsService;
    private NotificationHandler nextHandler;

    @Autowired
    public SqsQueueManager(EventService eventService, SqsService sqsService) {
        this.eventService = eventService;
        this.sqsService = sqsService;
    }

    @Override
    public void handle(Notification notification) {
        try {
            // Check in the event table if the event is pending
            if (eventService.isEventPending(notification.getEventId())) {
                // Attempt to send the notification to the SQS queue
                boolean sent = sqsService.sendNotification(notification);

                if (sent) {
                    // Mark the event as processed if the notification is successfully sent to the SQS queue
                    eventService.markEventAsProcessed(notification.getEventId());
                    System.out.println("Notification " + notification.getEventId() + " sent to SQS queue and marked as processed.");
                } else {
                    System.err.println("Failed to send notification " + notification.getEventId() + " to SQS queue.");
                }
            } else {
                System.out.println("Notification " + notification.getEventId() + " is not pending. Skipping SQS queue.");
            }

            // Pass to the next handler if exists
            if (nextHandler != null) {
                nextHandler.handle(notification);
            }
        } catch (Exception e) {
            System.err.println("Error in SqsQueueManager while handling notification " + notification.getEventId() + ": " + e.getMessage());
            e.printStackTrace();
            // You might want to implement more sophisticated error handling here
            // For example, you could retry the operation or log the error for manual intervention
        }
    }

    @Override
    public void setNext(NotificationHandler handler) {
        this.nextHandler = handler;
    }
}