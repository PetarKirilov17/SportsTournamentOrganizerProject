package com.example.demo.services;

import com.example.demo.entities.Tournament;
import com.example.demo.repositories.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class TournamentService extends BasicService<Tournament> {
    @Autowired
    private TournamentRepository tournamentRepository;

    @Override
    protected JpaRepository<Tournament, Long> getRepository() {
        return tournamentRepository;
    }
} 