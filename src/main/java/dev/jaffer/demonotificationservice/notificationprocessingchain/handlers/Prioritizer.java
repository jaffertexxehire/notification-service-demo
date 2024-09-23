package dev.jaffer.demonotificationservice.notificationprocessingchain.handlers;

import dev.jaffer.demonotificationservice.entity.Notification;
import dev.jaffer.demonotificationservice.notificationprocessingchain.AbstractNotificationHandler;
import org.springframework.stereotype.Component;


import dev.jaffer.demonotificationservice.entity.Notification;
import org.springframework.stereotype.Component;

@Component
public class Prioritizer extends AbstractNotificationHandler {

    @Override
    public void handle(Notification notification) {
        try {
            // If priority already set, skip this step and forward to next handler
            if (notification.getPriority() != null) {
                System.out.println("Priority already set for notification " + notification.getEventId() + ". Skipping prioritization.");
                passToNext(notification);
                return;
            }
            else{

                if(notification.getNotificationType().contains("USER")) {
                    ///set priority to high
                }else if(notification.getUserIds().size()>1){ //bulk notification
                    //set priority to low

                }
                else{
                    //set priority to medium
                }
            }

            // If priority not set, set the priority based on notification type and other factors
            NotificationPriority priority = determinePriority(notification);
            notification.setPriority(priority.name());

            System.out.println("Set priority to " + priority + " for notification " + notification.getEventId());

            // Pass the event to the next handler in the chain
            passToNext(notification);
        } catch (Exception e) {
            System.err.println("Error in Prioritizer while handling notification " + notification.getEventId()+ ": " + e.getMessage());
            e.printStackTrace();
            // You might want to implement more sophisticated error handling here
            // For example, you could set a default priority and continue, or stop the chain
        }
    }

    private NotificationPriority determinePriority(Notification notification) {
        // Implement your prioritization logic here
        // This is a simple example; you should replace it with your actual logic
        switch (notification.getNotificationType()) {
            case "URGENT_ACTION_REQUIRED":
                return NotificationPriority.HIGH;
            case "IMPORTANT_UPDATE":
                return NotificationPriority.MEDIUM;
            default:
                return NotificationPriority.LOW;
        }
    }
}
