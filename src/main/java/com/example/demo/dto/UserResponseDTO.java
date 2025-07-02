package com.example.demo.dto;

import com.example.demo.User;
import com.example.demo.entities.Participant;
import com.example.demo.enums.Role;

public class UserResponseDTO {
    private Integer id;
    private String name;
    private String email;
    private Role role;
    private Participant participant;

    // Constructor from User entity
    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.participant = user.getParticipant();
    }

    // Default constructor
    public UserResponseDTO() {}

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }
} 