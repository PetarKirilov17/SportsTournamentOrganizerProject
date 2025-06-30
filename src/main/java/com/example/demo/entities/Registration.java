package com.example.demo.entities;

import com.example.demo.enums.RegistrationStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Registration {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tournament_id", nullable = false)
    @JsonIgnore
    private Tournament tournament;

    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @JsonProperty("registered_at")
    @CreatedDate
    @Column(name = "registered_at")
    private LocalDateTime registeredAt;

    @Enumerated(EnumType.STRING)
    private RegistrationStatus status;

    @JsonProperty("invitation_sent")
    @Column(name = "invitation_sent")
    private LocalDateTime invitationSent;
} 