package com.example.demo.repositories;

import com.example.demo.entities.MatchResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MatchResultRepository extends JpaRepository<MatchResult, Long> {
    
    // Derived query methods - using correct property names from MatchResult entity
    Optional<MatchResult> findByMatchId(Long matchId);
    List<MatchResult> findByHomeScoreGreaterThan(Integer score);
    List<MatchResult> findByAwayScoreGreaterThan(Integer score);
    List<MatchResult> findByHomeScoreLessThan(Integer score);
    List<MatchResult> findByAwayScoreLessThan(Integer score);
    List<MatchResult> findByHomeScoreAndAwayScore(Integer homeScore, Integer awayScore);
    boolean existsByMatchId(Long matchId);
} 