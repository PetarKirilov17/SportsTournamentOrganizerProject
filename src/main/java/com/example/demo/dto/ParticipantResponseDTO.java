package com.example.demo.dto;

import com.example.demo.enums.TeamCategoryEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantResponseDTO {
    private Long id;
    
    @JsonProperty("first_name")
    private String firstName;
    
    @JsonProperty("last_name")
    private String lastName;
    
    private String email;
    private TeamCategoryEnum category;
    
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
    
    @JsonProperty("team_memberships")
    private List<TeamMembershipDTO> teamMemberships;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TeamMembershipDTO {
        private Long teamId;
        private String teamName;
        private TeamCategoryEnum teamCategory;
        private String role;
        
        @JsonProperty("jersey_number")
        private Integer jerseyNumber;
        
        @JsonProperty("added_at")
        private LocalDateTime addedAt;
    }
} 