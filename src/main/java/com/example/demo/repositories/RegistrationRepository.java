package com.example.demo.repositories;

import com.example.demo.entities.Registration;
import com.example.demo.enums.RegistrationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    
    // Derived query methods
    List<Registration> findByTeamId(Long teamId);
    List<Registration> findByTournamentId(Long tournamentId);
    Optional<Registration> findByTeamIdAndTournamentId(Long teamId, Long tournamentId);
    List<Registration> findByStatus(RegistrationStatus status);
    List<Registration> findByRegisteredAtAfter(LocalDateTime date);
    List<Registration> findByRegisteredAtBefore(LocalDateTime date);
    List<Registration> findByRegisteredAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    boolean existsByTeamIdAndTournamentId(Long teamId, Long tournamentId);
    List<Registration> findAllByTournamentId(Long tournamentId);
} 