package dev.jaffer.demonotificationservice.eventhandler;

public class KafkaEventHandler {



    //listen to kafka topic

    //extract eventId and payload from kafka record
    //String eventId = record.key();
    //String payload = record.value();

    // Check if the event is already processed deduplication

    //save to event table

    // then Acknowledge Kafka message after successful save.
}
