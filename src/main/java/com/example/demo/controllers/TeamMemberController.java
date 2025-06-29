package com.example.demo.controllers;

import com.example.demo.dto.ApiResponseDTO;
import com.example.demo.entities.TeamMember;
import com.example.demo.services.TeamMemberService;
import com.example.demo.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teams/{teamId}/members")
public class TeamMemberController {

    @Autowired
    private TeamMemberService teamMemberService;
    
    @Autowired
    private TeamService teamService;

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<TeamMember>>> getAllTeamMembers(@PathVariable Long teamId) {
        try {
            // Verify team exists
            teamService.getById(teamId);
            
            List<TeamMember> members = teamMemberService.findByTeamId(teamId);
            return ResponseEntity.ok(ApiResponseDTO.success(members, "Team members retrieved successfully"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponseDTO.error("Team not found: " + e.getMessage(), 404));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDTO.error("Failed to retrieve team members: " + e.getMessage(), 500));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<TeamMember>> getTeamMemberById(@PathVariable Long teamId, @PathVariable Long id) {
        try {
            // Verify team exists
            teamService.getById(teamId);
            
            TeamMember member = teamMemberService.getById(id);
            if (member == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponseDTO.error("Team member not found", 404));
            }
            
            // Verify the member belongs to the specified team
            if (!member.getTeam().getId().equals(teamId)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponseDTO.error("Team member does not belong to the specified team", 400));
            }
            
            return ResponseEntity.ok(ApiResponseDTO.success(member, "Team member retrieved successfully"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponseDTO.error("Team not found: " + e.getMessage(), 404));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDTO.error("Failed to retrieve team member: " + e.getMessage(), 500));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO<TeamMember>> createTeamMember(@PathVariable Long teamId, @RequestBody TeamMember teamMember) {
        try {
            // Verify team exists
            teamService.getById(teamId);
            
            // Set the team ID from the path variable
            teamMember.getTeam().setId(teamId);
            
            TeamMember createdMember = teamMemberService.save(teamMember);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponseDTO.success(createdMember, "Team member added successfully"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponseDTO.error("Team not found: " + e.getMessage(), 404));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDTO.error("Failed to add team member: " + e.getMessage(), 500));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<TeamMember>> updateTeamMember(@PathVariable Long teamId, @PathVariable Long id, @RequestBody TeamMember teamMember) {
        try {
            // Verify team exists
            teamService.getById(teamId);
            
            // Verify member exists and belongs to the team
            TeamMember existingMember = teamMemberService.getById(id);
            if (existingMember == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponseDTO.error("Team member not found", 404));
            }
            
            if (!existingMember.getTeam().getId().equals(teamId)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponseDTO.error("Team member does not belong to the specified team", 400));
            }
            
            // Set the ID and team ID
            teamMember.setId(id);
            teamMember.getTeam().setId(teamId);
            
            TeamMember updatedMember = teamMemberService.update(teamMember, id);
            return ResponseEntity.ok(ApiResponseDTO.success(updatedMember, "Team member updated successfully"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponseDTO.error("Team not found: " + e.getMessage(), 404));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDTO.error("Failed to update team member: " + e.getMessage(), 500));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<String>> deleteTeamMember(@PathVariable Long teamId, @PathVariable Long id) {
        try {
            // Verify team exists
            teamService.getById(teamId);
            
            // Verify member exists and belongs to the team
            TeamMember existingMember = teamMemberService.getById(id);
            if (existingMember == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponseDTO.error("Team member not found", 404));
            }
            
            if (!existingMember.getTeam().getId().equals(teamId)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponseDTO.error("Team member does not belong to the specified team", 400));
            }
            
            teamMemberService.deleteById(id);
            return ResponseEntity.ok(ApiResponseDTO.success("Team member removed successfully", "Team member with ID " + id + " has been removed"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponseDTO.error("Team not found: " + e.getMessage(), 404));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDTO.error("Failed to remove team member: " + e.getMessage(), 500));
        }
    }
} 