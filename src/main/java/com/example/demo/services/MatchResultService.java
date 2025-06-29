package com.example.demo.services;

import com.example.demo.entities.MatchResult;
import com.example.demo.repositories.MatchResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class MatchResultService extends BasicService<MatchResult> {
    @Autowired
    private MatchResultRepository matchResultRepository;

    @Override
    protected JpaRepository<MatchResult, Long> getRepository() {
        return matchResultRepository;
    }
} 