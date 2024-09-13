package dev.jaffer.demonotificationservice;

import dev.jaffer.demonotificationservice.entity.Notification;
import dev.jaffer.demonotificationservice.mapper.KafkaRecordToNotification;
import dev.jaffer.demonotificationservice.notificationprocessingchain.handlers.Prioritizer;
import dev.jaffer.demonotificationservice.notificationprocessingchain.handlers.SqsQueueManager;
import dev.jaffer.demonotificationservice.notificationprocessingchain.handlers.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/*
    * This class is the entry point for the notification processing chain.
    *
    * Updated Flow with Templating:
        Kafka Listener →
        Validation (fetch user details, preferences) →
        Prioritization →
        Templating (generate the message) →
        Save Notification Object →
        Publish to SQS (or Scheduled Task)

 */
@Component
public class NotificationProcessor {



    private final KafkaRecordToNotification kafkaRecordToNotification;
    private final Validator validationHandler;
    private final Prioritizer prioritizer;
    private final SqsQueueManager sqsQueueManager;


    //build the chain of responsibility

    public NotificationProcessor(KafkaRecordToNotification kafkaRecordToNotification,
                                 Validator validationHandler,
                                 Prioritizer prioritizer,
                                 SqsQueueManager sqsQueueManager) {
        this.kafkaRecordToNotification = kafkaRecordToNotification;
        this.validationHandler = validationHandler;
        this.prioritizer = prioritizer;
        this.sqsQueueManager = sqsQueueManager;

        // Set up the chain of responsibility
        validationHandler.setNext(prioritizer);
        prioritizer.setNext(sqsQueueManager);



    }

    public void process(String payload) {

        //map the kafka record to Notification object
        //Notification  = kafkaRecordToNotification.map(record);
        //call the first handler in the chain
        validationHandler.handle(Notification notification);
    }
}

