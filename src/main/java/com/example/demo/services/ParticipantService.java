package com.example.demo.services;

import com.example.demo.entities.Participant;
import com.example.demo.repositories.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class ParticipantService extends BasicService<Participant> {
    @Autowired
    private ParticipantRepository participantRepository;

    @Override
    protected CrudRepository<Participant, Long> getRepository() {
        return participantRepository;
    }
} 