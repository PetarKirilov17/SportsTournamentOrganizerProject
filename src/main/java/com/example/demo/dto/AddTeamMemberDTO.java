package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddTeamMemberDTO {
    
    @JsonProperty("participant_id")
    private Long participantId;
    
    private String role;
    
    @JsonProperty("jersey_number")
    private Integer jerseyNumber;
} 