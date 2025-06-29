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
public class TeamResponseDTO {
    private Long id;
    private String name;
    private TeamCategoryEnum category;
    
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
    
    @JsonProperty("team_members")
    private List<TeamMemberDTO> teamMembers;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TeamMemberDTO {
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
} 