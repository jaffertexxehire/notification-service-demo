package dev.jaffer.demonotificationservice.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String name;

    private String email;

    private String phoneNumber;

    //must be eager loading
    @ManyToMany
    private List<UserChannelPreference> preferences; // when user sign up notification is received default preferences are set

}
