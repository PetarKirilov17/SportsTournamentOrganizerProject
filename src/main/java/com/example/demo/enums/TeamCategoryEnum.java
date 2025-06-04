package com.example.demo.enums;

public enum TeamCategoryEnum {
    PROFESSIONAL("Professional"),
    AMATEUR("Amateur"),
    SEMI_PROFESSIONAL("Semi-Professional");

    private final String description;

    TeamCategoryEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
