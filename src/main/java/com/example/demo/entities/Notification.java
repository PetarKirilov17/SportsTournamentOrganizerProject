package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.example.demo.enums.RecipientType;
import com.example.demo.enums.NotificationType;
import com.example.demo.enums.NotificationStatus;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonProperty("tournament_id")
    @ManyToOne
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

    @JsonProperty("match_id")
    @ManyToOne
    @JoinColumn(name = "match_id")
    private Match match;

    @JsonProperty("recipient_type")
    @Enumerated(EnumType.STRING)
    @Column(name = "recipient_type")
    private RecipientType recipientType;

    @JsonProperty("recipient_id")
    @Column(name = "recipient_id")
    private Long recipientId;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    private String message;

    @JsonProperty("sent_at")
    @CreatedDate
    @Column(name = "sent_at")
    private LocalDateTime sentAt;

    @Enumerated(EnumType.STRING)
    private NotificationStatus status;
} 