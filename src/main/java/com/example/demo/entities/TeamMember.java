package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class TeamMember {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonProperty("team_id")
    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @JsonProperty("participant_id")
    @ManyToOne
    @JoinColumn(name = "participant_id", nullable = false)
    private Participant participant;

    private String role;

    @JsonProperty("jersey_number")
    @Column(name = "jersey_number")
    private Integer jerseyNumber;

    @JsonProperty("added_at")
    @CreatedDate
    @Column(name = "added_at")
    private LocalDateTime addedAt;
} 