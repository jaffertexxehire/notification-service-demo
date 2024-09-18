package dev.jaffer.demonotificationservice.repositories;

import dev.jaffer.demonotificationservice.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    Optional<Event> findByEventId(Long eventId);

    @Query("SELECT e FROM Event e WHERE e.status = 'PENDING'")
    List<Event> findByStatusPending();


//    @Query("SELECT CASE WHEN e.status = 'PENDING' THEN true ELSE false END FROM Event e WHERE e.eventId = :eventId")
//    boolean isEventPending(Long eventId);
//
//    @Modifying
//    @Query("UPDATE Event e SET e.status= 'COMPLETED' WHERE e.eventId = :eventId")
//    void markEventAsProcessed(Long eventId);
}
