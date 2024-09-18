package dev.jaffer.demonotificationservice.mapper;

import dev.jaffer.demonotificationservice.entity.Notification;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class KafkaRecordToNotification {

    private final ObjectMapper objectMapper;

    public KafkaRecordToNotification(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Notification map(String payload, Long eventId) {
        try {
            // Deserialize the JSON payload into a Notification object
            Notification notification = objectMapper.readValue(payload, Notification.class);
            notification.setEventId(eventId);
            return notification;
        } catch (IOException e) {
            throw new RuntimeException("Error mapping Kafka record to Notification", e);
        }
    }
}
