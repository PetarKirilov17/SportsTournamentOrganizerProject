package com.example.demo.enums;

public enum NotificationType {
    SCHEDULE("Schedule"),
    RESULT("Result"),
    UPDATE("Update");

    private final String description;

    NotificationType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
} 