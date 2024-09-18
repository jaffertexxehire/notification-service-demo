package dev.jaffer.demonotificationservice.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.util.List;

@Entity
public class User {

    @Id
    private Long id;

    private Long userId;

    private String email;

    @ManyToMany
    private List<UserPreference> preferences; // could be List<String> as well

}
