package dev.jaffer.demonotificationservice;

import dev.jaffer.demonotificationservice.entity.Notification;
import dev.jaffer.demonotificationservice.mapper.KafkaRecordToNotification;
import dev.jaffer.demonotificationservice.notificationprocessingchain.handlers.Prioritizer;
import dev.jaffer.demonotificationservice.notificationprocessingchain.handlers.SqsQueueManager;
import dev.jaffer.demonotificationservice.notificationprocessingchain.handlers.UserEventHandler;
import dev.jaffer.demonotificationservice.notificationprocessingchain.handlers.Validator;
import org.springframework.stereotype.Component;

@Component
public class NotificationProcessor {

    private final KafkaRecordToNotification kafkaRecordToNotification;
    private final Validator validationHandler;
    private final Prioritizer prioritizer;
    private final SqsQueueManager sqsQueueManager;
    private final UserEventHandler userEventHandler;

    public NotificationProcessor(KafkaRecordToNotification kafkaRecordToNotification,
                                 Validator validationHandler,
                                 Prioritizer prioritizer,
                                 SqsQueueManager sqsQueueManager, UserEventHandler userEventHandler
                                 ) {
        this.kafkaRecordToNotification = kafkaRecordToNotification;
        this.validationHandler = validationHandler;
        this.prioritizer = prioritizer;
        this.sqsQueueManager = sqsQueueManager;
        this.userEventHandler = userEventHandler;

        // Set up the chain of responsibility
        userEventHandler.setNext(validationHandler);
        validationHandler.setNext(prioritizer);
        prioritizer.setNext(sqsQueueManager);
    }

    public void process(String payload, Long eventId) {
        try {
            // Map the Kafka record to Notification object
            Notification notification = kafkaRecordToNotification.map(payload, eventId);

            // Call the first handler in the chain
//            validationHandler.handle(notification);
            userEventHandler.handle(notification);
        } catch (Exception e) {
            // Log the error and potentially implement retry or error handling logic
            System.err.println("Error processing notification: " + e.getMessage());
            e.printStackTrace();
            // You might want to implement more sophisticated error handling here
        }
    }
}