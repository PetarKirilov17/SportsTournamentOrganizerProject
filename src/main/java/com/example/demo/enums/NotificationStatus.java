package com.example.demo.enums;

public enum NotificationStatus {
    PENDING("Pending"),
    SENT("Sent"),
    FAILED("Failed");

    private final String description;

    NotificationStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
} 