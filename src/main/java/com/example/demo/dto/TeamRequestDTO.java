package com.example.demo.dto;

import com.example.demo.enums.TeamCategoryEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamRequestDTO {
    
    private String name;
    
    private TeamCategoryEnum category;
} 