package com.example.demo.controllers.requests;

import com.example.demo.enums.RegistrationStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationRequest {
    @JsonProperty("team_id")
    private Long teamId;

    private RegistrationStatus status;
} 