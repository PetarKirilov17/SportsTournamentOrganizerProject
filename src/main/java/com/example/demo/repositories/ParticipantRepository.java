package com.example.demo.repositories;

import com.example.demo.entities.Participant;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    
    // Using @Query with @EntityGraph for eager loading of team memberships
    @Query("SELECT p FROM Participant p WHERE p.id = :id")
    @EntityGraph(value = "Participant.withTeamMembers")
    Optional<Participant> findByIdWithTeamMembers(@Param("id") Long id);
    
    // Derived query methods examples (these can be used for simpler queries)
    Optional<Participant> findByEmail(String email);
    Optional<Participant> findByFirstNameAndLastName(String firstName, String lastName);
    Optional<Participant> findByEmailIgnoreCase(String email);
} 