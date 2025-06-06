package com.example.demo.controllers;

import com.example.demo.entities.TeamMember;
import com.example.demo.services.TeamMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teams/{teamId}/members")
public class TeamMemberController {

    @Autowired
    private TeamMemberService teamMemberService;

    @GetMapping
    public Iterable<TeamMember> getAllTeamMembers(@PathVariable Long teamId) {
        return teamMemberService.getAll();
    }

    @GetMapping("/{id}")
    public TeamMember getTeamMemberById(@PathVariable Long id) {
        return teamMemberService.getById(id);
    }

    @PostMapping
    public TeamMember createTeamMember(@RequestBody TeamMember teamMember) {
        return teamMemberService.save(teamMember);
    }

    @PutMapping("/{id}")
    public TeamMember updateTeamMember(@PathVariable Long id, @RequestBody TeamMember teamMember) {
        return teamMemberService.update(teamMember, id);
    }

    @DeleteMapping("/{id}")
    public void deleteTeamMember(@PathVariable Long id) {
        teamMemberService.deleteById(id);
    }
} 