package com.example.demo.controllers;

import com.example.demo.entities.Participant;
import com.example.demo.services.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/participants")
public class ParticipantController {

    @Autowired
    private ParticipantService participantService;

    @GetMapping
    public Iterable<Participant> getAllParticipants() {
        return participantService.getAll();
    }

    @GetMapping("/{id}")
    public Participant getParticipantById(@PathVariable Long id) {
        return participantService.getById(id);
    }

    @PostMapping
    public Participant createParticipant(@RequestBody Participant participant) {
        return participantService.save(participant);
    }

    @PutMapping("/{id}")
    public Participant updateParticipant(@PathVariable Long id, @RequestBody Participant participant) {
        return participantService.update(participant, id);
    }

    @DeleteMapping("/{id}")
    public void deleteParticipant(@PathVariable Long id) {
        participantService.deleteById(id);
    }
} 