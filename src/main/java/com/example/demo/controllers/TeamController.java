package com.example.demo.controllers;

import com.example.demo.entities.Team;
import com.example.demo.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeamController {

    @Autowired
    private TeamService teamService;


    // Example method to get all teams
     @GetMapping("/teams")
     public Iterable<Team> getAllTeams() {
         return this.teamService.getAll();
     }

    // Example method to create a new team
     @PostMapping("/teams")
     public Team createTeam(@RequestBody Team team) {
         return this.teamService.save(team);
     }

    // Additional methods for updating and deleting teams can be added here
}
