package dev.jaffer.demonotificationservice.schedulers;


import dev.jaffer.demonotificationservice.NotificationProcessor;
import dev.jaffer.demonotificationservice.entity.Event;
import dev.jaffer.demonotificationservice.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class EventScheduler {


    private EventRepository eventRepository;


    private NotificationProcessor notificationProcessor;

    public EventScheduler(EventRepository eventRepository, NotificationProcessor notificationProcessor) {
        this.eventRepository = eventRepository;
        this.notificationProcessor = notificationProcessor;
    }

    @Scheduled(fixedDelayString = "${event.scheduler.fixed.delay:2000}")
    //@Transactional
    public void schedulePendingEvents() {
        List<Event> pendingEvents = eventRepository.findByStatusPending();

        //scheduler will process all pending events
        for (Event event : pendingEvents) {
            notificationProcessor.process(event.getPayload(), event.getEventId());

        }
    }
}
