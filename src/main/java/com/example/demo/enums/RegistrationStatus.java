package com.example.demo.enums;

import lombok.Getter;

@Getter
public enum RegistrationStatus {
    INVITED("Invited"),
    REGISTERED("Registered"),
    DECLINED("Declined"),
    CANCELLED("Cancelled");

    private final String description;

    RegistrationStatus(String description) {
        this.description = description;
    }

}