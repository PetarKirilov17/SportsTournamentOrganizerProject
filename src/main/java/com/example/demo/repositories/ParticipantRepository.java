package com.example.demo.repositories;

import com.example.demo.entities.Participant;
import org.springframework.data.repository.CrudRepository;

public interface ParticipantRepository extends CrudRepository<Participant, Long> {
} 