package com.example.demo.enums;

public enum RecipientType {
    TEAM("Team"),
    PARTICIPANT("Participant");

    private final String description;

    RecipientType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
} 