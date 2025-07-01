package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.example.demo.enums.MatchStatus;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tournament_id", nullable = false)
    private Tournament tournament;

    @JsonProperty("home_team")
    @ManyToOne
    @JoinColumn(name = "home_team_id", nullable = false)
    private Team homeTeam;

    @JsonProperty("away_team")
    @ManyToOne
    @JoinColumn(name = "away_team_id", nullable = false)
    private Team awayTeam;

    @ManyToOne
    @JoinColumn(name = "venue_id", nullable = false)
    private Venue venue;

    @JsonProperty("scheduled_at")
    @Column(name = "scheduled_at")
    private LocalDateTime scheduledAt;

    @JsonProperty("home_score")
    @Column(name = "home_score")
    private Integer homeScore;

    @JsonProperty("away_score")
    @Column(name = "away_score")
    private Integer awayScore;

    @Enumerated(EnumType.STRING)
    private MatchStatus status;

    @JsonProperty("created_at")
    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
} 