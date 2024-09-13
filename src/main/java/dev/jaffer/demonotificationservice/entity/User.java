package dev.jaffer.demonotificationservice.entity;


import java.util.List;

public class User {

    @Id
    private Long id;

    private Long userId;

    private String email;

    private List<Integer> preferences; // could be List<String> as well

}
