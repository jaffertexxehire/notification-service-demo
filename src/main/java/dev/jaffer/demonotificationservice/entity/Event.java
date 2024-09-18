package dev.jaffer.demonotificationservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    Long eventId;

    @Column(columnDefinition = "TEXT")
    String payload;

    @Enumerated(EnumType.STRING)
    Status status;
}
