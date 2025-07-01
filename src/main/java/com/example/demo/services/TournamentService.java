package com.example.demo.services;

import com.example.demo.dto.TeamLeaderboardDTO;
import com.example.demo.entities.Tournament;
import com.example.demo.repositories.TeamRepository;
import com.example.demo.repositories.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TournamentService extends BasicService<Tournament> {
    @Autowired
    private TournamentRepository tournamentRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Override
    protected JpaRepository<Tournament, Long> getRepository() {
        return tournamentRepository;
    }

    public List<TeamLeaderboardDTO> getLeaderboard(Long id) {
        return teamRepository.getLeaderboardForTournament(id);
    }
} 