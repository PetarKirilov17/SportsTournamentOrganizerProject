package com.example.demo.controllers;

import com.example.demo.dto.ParticipantResponseDTO;
import com.example.demo.dto.UpdateParticipantDTO;
import com.example.demo.entities.Participant;
import com.example.demo.services.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/participants")
public class ParticipantController {

    @Autowired
    private ParticipantService participantService;

    @GetMapping
    public ResponseEntity<List<ParticipantResponseDTO>> getAllParticipants() {
        List<ParticipantResponseDTO> participants = participantService.getAllParticipantsWithTeams();
        return ResponseEntity.ok(participants);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParticipantResponseDTO> getParticipantById(@PathVariable Long id) {
        try {
            ParticipantResponseDTO participant = participantService.getParticipantWithTeams(id);
            return ResponseEntity.ok(participant);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Participant> createParticipant(@RequestBody Participant participant) {
        Participant createdParticipant = participantService.save(participant);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdParticipant);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Participant> updateParticipant(@PathVariable Long id, @RequestBody UpdateParticipantDTO updateDTO) {
        try {
            Participant updatedParticipant = participantService.updateParticipant(updateDTO, id);
            return ResponseEntity.ok(updatedParticipant);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public void deleteParticipant(@PathVariable Long id) {
        participantService.deleteById(id);
    }

    @GetMapping("/team/{teamId}")
    public ResponseEntity<List<ParticipantResponseDTO>> getParticipantsByTeamId(@PathVariable Long teamId) {
        List<ParticipantResponseDTO> participants = participantService.getParticipantsByTeamId(teamId);
        return ResponseEntity.ok(participants);
    }
} 