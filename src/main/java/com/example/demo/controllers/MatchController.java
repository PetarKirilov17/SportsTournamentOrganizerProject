package com.example.demo.controllers;

import com.example.demo.dto.MatchRequestDTO;
import com.example.demo.entities.Match;
import com.example.demo.services.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tournaments/{tournamentId}/matches")
public class MatchController {
    @Autowired
    private MatchService matchService;

    @GetMapping
    public ResponseEntity<List<Match>> getAllMatches(@PathVariable Long tournamentId) {
        List<Match> matches = matchService.getAllMatchesByTournamentId(tournamentId);
        return ResponseEntity.ok(matches);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Match> getMatchById(@PathVariable Long tournamentId, @PathVariable Long id) {
        Match match = matchService.getById(id);
        if (match != null && match.getTournament().getId().equals(tournamentId)) {
            return ResponseEntity.ok(match);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Match> createMatch(@PathVariable Long tournamentId,
                                             @RequestBody MatchRequestDTO matchRequest
    ) {


        Match createdMatch = matchService.saveMatch(
                tournamentId,
                matchRequest.getHomeTeamId(),
                matchRequest.getAwayTeamId(),
                matchRequest.getVenueId(),
                matchRequest.getStatus(),
                matchRequest.getScheduledAt(),
                matchRequest.getHomeScore(),
                matchRequest.getAwayScore()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(createdMatch);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Match> updateMatch(@PathVariable Long tournamentId,
                                             @PathVariable Long id,
                                             @RequestBody MatchRequestDTO matchRequest
    ) {
        Match updatedMatch = matchService.updateMatch(
                tournamentId,
                id,
                matchRequest.getHomeTeamId(),
                matchRequest.getAwayTeamId(),
                matchRequest.getVenueId(),
                matchRequest.getStatus(),
                matchRequest.getScheduledAt(),
                matchRequest.getHomeScore(),
                matchRequest.getAwayScore()
        );

        return ResponseEntity.ok(updatedMatch);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatch(@PathVariable Long tournamentId, @PathVariable Long id) {
        try {
            matchService.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
} 