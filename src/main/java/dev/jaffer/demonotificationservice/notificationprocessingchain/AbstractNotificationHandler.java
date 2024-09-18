package dev.jaffer.demonotificationservice.notificationprocessingchain;

import dev.jaffer.demonotificationservice.entity.Notification;

public abstract class AbstractNotificationHandler implements NotificationHandler {

    protected NotificationHandler nextHandler;

    //usd for setting the next handler in the chain
    @Override
    public void setNext(NotificationHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    //used for passing the event to the next handler in the chain
    protected void passToNext(Notification notification) {
        if (nextHandler != null) {
            nextHandler.handle(notification);
        }
    }
}

