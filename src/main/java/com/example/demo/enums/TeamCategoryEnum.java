package com.example.demo.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TeamCategoryEnum {
    PROFESSIONAL("Professional"),
    AMATEUR("Amateur"),
    YOUTH("Youth");

    private final String description;

    TeamCategoryEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @JsonValue
    public String getValue() {
        return this.name();
    }

    @JsonCreator
    public static TeamCategoryEnum fromString(String value) {
        if (value == null) {
            return null;
        }
        
        // Try exact match first
        try {
            return TeamCategoryEnum.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            // Try case-insensitive match with description
            for (TeamCategoryEnum category : TeamCategoryEnum.values()) {
                if (category.getDescription().equalsIgnoreCase(value)) {
                    return category;
                }
            }
            // Try case-insensitive match with enum name
            for (TeamCategoryEnum category : TeamCategoryEnum.values()) {
                if (category.name().equalsIgnoreCase(value)) {
                    return category;
                }
            }
            throw new IllegalArgumentException("Unknown TeamCategoryEnum value: " + value);
        }
    }
}
