package dev.jaffer.demonotificationservice.services;

import dev.jaffer.demonotificationservice.entity.Event;
import dev.jaffer.demonotificationservice.entity.Status;
import dev.jaffer.demonotificationservice.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public boolean isEventPending(Long eventId) {
        return eventRepository.findByEventId(eventId).map(event -> event.getStatus() == Status.PENDING).orElse(false);
    }

    @Transactional
    public void markEventAsProcessed(Long eventId) {
        eventRepository.findByEventId(eventId).ifPresent(event -> {
            event.setStatus(Status.COMPLETED);
            eventRepository.save(event);
        });
    }
}