package com.example.demo.controllers;

import com.example.demo.entities.Tournament;
import com.example.demo.services.TournamentService;
import com.example.demo.dto.TeamLeaderboardDTO;
import com.example.demo.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tournaments")
public class TournamentController {

    @Autowired
    private TournamentService tournamentService;

    @GetMapping
    public Iterable<Tournament> getAllTournaments() {
        return tournamentService.getAll();
    }

    @GetMapping("/{id}")
    public Tournament getTournamentById(@PathVariable Long id) {
        return tournamentService.getById(id);
    }

    @PostMapping
    public Tournament createTournament(@RequestBody Tournament tournament) {
        return tournamentService.save(tournament);
    }

    @PutMapping("/{id}")
    public Tournament updateTournament(@PathVariable Long id, @RequestBody Tournament tournament) {
        return tournamentService.update(tournament, id);
    }

    @DeleteMapping("/{id}")
    public void deleteTournament(@PathVariable Long id) {
        tournamentService.deleteById(id);
    }

    @GetMapping("/{id}/leaderboard")
    public List<TeamLeaderboardDTO> getLeaderboard(@PathVariable Long id) {
        return tournamentService.getLeaderboard(id);
    }
} 