package dev.jaffer.demonotificationservice.notificationprocessingchain;

import dev.jaffer.demonotificationservice.entity.Notification;

public interface NotificationHandler {
    void handle(Notification notification);
    void setNext(NotificationHandler nextHandler);
}
