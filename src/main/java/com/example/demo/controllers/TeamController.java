package com.example.demo.controllers;

import com.example.demo.dto.ApiResponseDTO;
import com.example.demo.dto.TeamRequestDTO;
import com.example.demo.dto.TeamResponseDTO;
import com.example.demo.entities.Team;
import com.example.demo.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teams")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @GetMapping
    public ResponseEntity<List<TeamResponseDTO>> getAllTeams() {
        List<TeamResponseDTO> teams = teamService.getAllTeamsWithMembers();
        return ResponseEntity.ok(teams);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<TeamResponseDTO>> getTeamById(@PathVariable Long id) {
        try {
            TeamResponseDTO team = teamService.getTeamWithMembers(id);
            return ResponseEntity.ok(ApiResponseDTO.success(team, "Team retrieved successfully"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponseDTO.error("Team not found: " + e.getMessage(), 404));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDTO.error("Failed to retrieve team: " + e.getMessage(), 500));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO<Team>> createTeam(@RequestBody TeamRequestDTO teamRequest) {
        try {
            // Validate input
            if (teamRequest.getName() == null || teamRequest.getName().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(ApiResponseDTO.error("Team name is required", 400));
            }
            
            if (teamRequest.getCategory() == null) {
                return ResponseEntity.badRequest()
                        .body(ApiResponseDTO.error("Team category is required", 400));
            }
            
            // Check if team name already exists
            if (teamService.existsByName(teamRequest.getName())) {
                return ResponseEntity.badRequest()
                        .body(ApiResponseDTO.error("Team name already exists: " + teamRequest.getName(), 400));
            }
            
            // Create team entity
            Team team = new Team();
            team.setName(teamRequest.getName());
            team.setCategory(teamRequest.getCategory());
            
            Team createdTeam = teamService.save(team);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponseDTO.success(createdTeam, "Team created successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDTO.error("Failed to create team: " + e.getMessage(), 500));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Team>> updateTeam(@PathVariable Long id, @RequestBody TeamRequestDTO teamRequest) {
        try {
            Team updatedTeam = teamService.updateTeam(teamRequest, id);
            return ResponseEntity.ok(ApiResponseDTO.success(updatedTeam, "Team updated successfully"));
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponseDTO.error("Team not found: " + e.getMessage(), 404));
            } else {
                return ResponseEntity.badRequest()
                        .body(ApiResponseDTO.error("Invalid update request: " + e.getMessage(), 400));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDTO.error("Failed to update team: " + e.getMessage(), 500));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<String>> deleteTeam(@PathVariable Long id) {
        try {
            teamService.deleteTeam(id);
            return ResponseEntity.ok(ApiResponseDTO.success("Team deleted successfully", "Team with ID " + id + " has been deleted"));
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponseDTO.error("Team not found: " + e.getMessage(), 404));
            } else {
                return ResponseEntity.badRequest()
                        .body(ApiResponseDTO.error("Cannot delete team: " + e.getMessage(), 400));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDTO.error("Failed to delete team: " + e.getMessage(), 500));
        }
    }
    
    @GetMapping("/category/{category}")
    public ResponseEntity<ApiResponseDTO<List<Team>>> getTeamsByCategory(@PathVariable String category) {
        try {
            com.example.demo.enums.TeamCategoryEnum categoryEnum = com.example.demo.enums.TeamCategoryEnum.fromString(category);
            List<Team> teams = teamService.findByCategory(categoryEnum);
            return ResponseEntity.ok(ApiResponseDTO.success(teams, "Teams retrieved by category successfully"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponseDTO.error("Invalid category: " + e.getMessage(), 400));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDTO.error("Failed to retrieve teams by category: " + e.getMessage(), 500));
        }
    }
    
    @GetMapping("/search")
    public ResponseEntity<ApiResponseDTO<List<Team>>> searchTeamsByName(@RequestParam String name) {
        try {
            List<Team> teams = teamService.findByNameContaining(name);
            return ResponseEntity.ok(ApiResponseDTO.success(teams, "Teams search completed successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDTO.error("Failed to search teams: " + e.getMessage(), 500));
        }
    }
}
