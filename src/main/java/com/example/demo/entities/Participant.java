package com.example.demo.entities;

import com.example.demo.enums.TeamCategoryEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@NamedEntityGraph(
    name = "Participant.withTeamMembers",
    attributeNodes = {
        @NamedAttributeNode("teamMembers"),
        @NamedAttributeNode(value = "teamMembers", subgraph = "teamMembers.team")
    },
    subgraphs = {
        @NamedSubgraph(
            name = "teamMembers.team",
            attributeNodes = {
                @NamedAttributeNode("team")
            }
        )
    }
)
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonProperty("first_name")
    @Column(name = "first_name")
    private String firstName;

    @JsonProperty("last_name")
    @Column(name = "last_name")
    private String lastName;

    private String email;

    @Enumerated
    private TeamCategoryEnum category;

    @OneToMany(mappedBy = "participant", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<TeamMember> teamMembers;

    @JsonProperty("created_at")
    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
} 