package com.example.demo.controllers;

import com.example.demo.controllers.requests.RegistrationRequest;
import com.example.demo.entities.Registration;
import com.example.demo.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tournaments/{tournamentId}/registrations")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @GetMapping
    public List<Registration> getRegistrationsByTournamentId(@PathVariable Long tournamentId) {
        return registrationService.getRegistrationsByTournamentId(tournamentId);
    }

    @PostMapping
    public Registration createRegistration(@PathVariable Long tournamentId, @RequestBody RegistrationRequest request) {
        return registrationService.createRegistration(tournamentId, request.getTeamId(), request.getStatus());
    }
} 