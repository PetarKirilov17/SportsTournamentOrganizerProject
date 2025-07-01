package com.example.demo.dto;

import com.example.demo.enums.RegistrationStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationRequestDTO {
    @JsonProperty("team_id")
    private Long teamId;

    private RegistrationStatus status;
} 