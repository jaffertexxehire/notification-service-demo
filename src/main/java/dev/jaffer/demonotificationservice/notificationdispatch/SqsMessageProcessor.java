package dev.jaffer.demonotificationservice.notificationdispatch;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.jaffer.demonotificationservice.entity.Notification;
import org.springframework.stereotype.Component;

@Component
public class SqsMessageProcessor {

    private ObjectMapper objectMapper;
    private NotificationDispatcher notificationDispatcher;

    public SqsMessageProcessor(ObjectMapper objectMapper, NotificationDispatcher notificationDispatcher) {
        this.objectMapper = objectMapper;
        this.notificationDispatcher = notificationDispatcher;
    }

    public void processMessage(String message) {
        try {
            // Convert the message to DispatchPayload
            DispatchPayload dispatchPayload = objectMapper.readValue(message, DispatchPayload.class);
            notificationDispatcher.dispatch(dispatchPayload);
        } catch (Exception e) {
            System.err.println("Error processing message: " + e.getMessage());
        }
    }
}
