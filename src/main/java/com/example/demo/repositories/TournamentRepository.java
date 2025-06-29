package com.example.demo.repositories;

import com.example.demo.entities.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TournamentRepository extends JpaRepository<Tournament, Long> {
    
    // Derived query methods - using correct property types from Tournament entity
    Optional<Tournament> findByName(String name);
    Optional<Tournament> findByNameIgnoreCase(String name);
    List<Tournament> findByNameContainingIgnoreCase(String name);
    List<Tournament> findByStartDateAfter(LocalDate startDate);
    List<Tournament> findByEndDateBefore(LocalDate endDate);
    List<Tournament> findByStartDateBetween(LocalDate startDate, LocalDate endDate);
    boolean existsByName(String name);
    boolean existsByNameIgnoreCase(String name);
} 