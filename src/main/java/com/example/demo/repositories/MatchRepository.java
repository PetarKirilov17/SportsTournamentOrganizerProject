package com.example.demo.repositories;

import com.example.demo.entities.Match;
import com.example.demo.enums.MatchStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MatchRepository extends JpaRepository<Match, Long> {
    
    // Derived query methods - using correct property names from Match entity
    List<Match> findByTournamentId(Long tournamentId);
    List<Match> findByVenueId(Long venueId);
    List<Match> findByStatus(MatchStatus status);
    List<Match> findByScheduledAtAfter(LocalDateTime time);
    List<Match> findByScheduledAtBefore(LocalDateTime time);
    List<Match> findByScheduledAtBetween(LocalDateTime startTime, LocalDateTime endTime);
    List<Match> findByTournamentIdAndStatus(Long tournamentId, MatchStatus status);
    List<Match> findByVenueIdAndStatus(Long venueId, MatchStatus status);
    Optional<Match> findByTournamentIdAndScheduledAt(Long tournamentId, LocalDateTime scheduledAt);

}