package com.example.demo.dto;

import com.example.demo.enums.MatchStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MatchRequestDTO {
    @JsonProperty("home_team_id")
    private Long homeTeamId;

    @JsonProperty("away_team_id")
    private Long awayTeamId;

    @JsonProperty("venue_id")
    private Long venueId;

//    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @JsonProperty("scheduled_at")
    private LocalDateTime scheduledAt;

    private MatchStatus status;

    @JsonProperty("home_score")
    private Integer homeScore;

    @JsonProperty("away_score")
    private Integer awayScore;
}