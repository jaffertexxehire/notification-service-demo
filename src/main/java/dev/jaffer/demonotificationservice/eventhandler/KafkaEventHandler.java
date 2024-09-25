package dev.jaffer.demonotificationservice.eventhandler;

import dev.jaffer.demonotificationservice.entity.Event;
import dev.jaffer.demonotificationservice.entity.Status;
import dev.jaffer.demonotificationservice.repositories.EventRepository;
import jakarta.transaction.Transactional;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class KafkaEventHandler {

    private EventRepository eventRepository;

    //listen to kafka topic
    public KafkaEventHandler(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @KafkaListener(topics = "notifications", groupId = "notificationservice")
    public void handleNotifications(ConsumerRecord<String, String> record,Acknowledgment acknowledgment) {
        String payload = record.value();
        Long eventId = Long.parseLong(record.key());
        System.out.println("Received event with ID: " + eventId);

        try {
            // Check if the event is already processed (deduplication)
            Optional<Event> existingEvent = eventRepository.findByEventId(eventId);
            if (existingEvent.isPresent()) {
                acknowledgment.acknowledge();
                return;
            }

            // Save to event table
            Event event = new Event();
            event.setEventId(eventId);
            event.setPayload(payload);
            event.setStatus(Status.PENDING);
            eventRepository.save(event);

            //kafka message acknowledgment
            acknowledgment.acknowledge();
        } catch (Exception e) {
            // Log the error and potentially trigger a retry mechanism
            // For simplicity, we're just printing the stack trace here
            e.printStackTrace();
            // You might want to implement a retry mechanism here
            // For example, you could send the failed event to a dead-letter queue
        }

    }


}
