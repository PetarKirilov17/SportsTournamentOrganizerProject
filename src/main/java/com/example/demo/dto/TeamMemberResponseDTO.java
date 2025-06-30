package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamMemberResponseDTO {
    
    private Long id;
    
    @JsonProperty("participant_id")
    private Long participantId;
    
    @JsonProperty("first_name")
    private String firstName;
    
    @JsonProperty("last_name")
    private String lastName;
    
    private String email;
    
    private String role;
    
    @JsonProperty("jersey_number")
    private Integer jerseyNumber;
    
    @JsonProperty("added_at")
    private LocalDateTime addedAt;
} 