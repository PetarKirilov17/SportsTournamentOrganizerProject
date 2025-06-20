package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class MatchResult {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "match_id", nullable = false)
    private Match match;

    @JsonProperty("home_score")
    @Column(name = "home_score")
    private Integer homeScore;

    @JsonProperty("away_score")
    @Column(name = "away_score")
    private Integer awayScore;

    @JsonProperty("updated_at")
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
} 