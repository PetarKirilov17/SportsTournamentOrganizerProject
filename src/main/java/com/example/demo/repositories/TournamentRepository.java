package com.example.demo.repositories;

import com.example.demo.entities.Tournament;
import org.springframework.data.repository.CrudRepository;

public interface TournamentRepository extends CrudRepository<Tournament, Long> {
} 