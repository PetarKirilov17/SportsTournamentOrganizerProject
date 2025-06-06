package com.example.demo.services;

import com.example.demo.entities.Match;
import com.example.demo.repositories.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class MatchService extends BasicService<Match> {
    @Autowired
    private MatchRepository matchRepository;

    @Override
    protected CrudRepository<Match, Long> getRepository() {
        return matchRepository;
    }
} 