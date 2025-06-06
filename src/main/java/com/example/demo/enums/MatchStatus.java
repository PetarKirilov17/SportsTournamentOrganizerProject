package com.example.demo.enums;

public enum MatchStatus {
    SCHEDULED("Scheduled"),
    LIVE("Live"),
    COMPLETED("Completed"),
    POSTPONED("Postponed"),
    CANCELLED("Cancelled");

    private final String description;

    MatchStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
} 