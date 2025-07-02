package com.example.demo.controllers;

import com.example.demo.dto.AddTeamMemberDTO;
import com.example.demo.dto.ApiResponseDTO;
import com.example.demo.dto.ParticipantResponseDTO;
import com.example.demo.dto.TeamMemberResponseDTO;
import com.example.demo.dto.UpdateTeamMemberDTO;
import com.example.demo.services.TeamMemberService;
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

    @GetMapping
    public ResponseEntity<List<TeamMemberResponseDTO>> getAllTeamMembers(@PathVariable Long teamId) {
        if (teamId == null) {
            return ResponseEntity.badRequest().build();
        }
        List<TeamMemberResponseDTO> members = teamMemberService.getTeamMembersByTeamId(teamId);
        return ResponseEntity.ok(members);
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<ApiResponseDTO<TeamMemberResponseDTO>> getTeamMemberById(@PathVariable Long teamId, @PathVariable Long memberId) {
        try {
            TeamMemberResponseDTO member = teamMemberService.getTeamMemberById(teamId, memberId);
            return ResponseEntity.ok(ApiResponseDTO.success(member, "Team member retrieved successfully"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponseDTO.error("Team member not found: " + e.getMessage(), 404));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDTO.error("Failed to retrieve team member: " + e.getMessage(), 500));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO<TeamMemberResponseDTO>> addParticipantToTeam(@PathVariable Long teamId, @RequestBody AddTeamMemberDTO addDTO) {
        try {
            // Validate input
            if (addDTO.getParticipantId() == null) {
                return ResponseEntity.badRequest()
                        .body(ApiResponseDTO.error("Participant ID is required", 400));
            }
            
            TeamMemberResponseDTO addedMember = teamMemberService.addParticipantToTeam(teamId, addDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponseDTO.success(addedMember, "Participant added to team successfully"));
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponseDTO.error("Resource not found: " + e.getMessage(), 404));
            } else {
                return ResponseEntity.badRequest()
                        .body(ApiResponseDTO.error("Invalid request: " + e.getMessage(), 400));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDTO.error("Failed to add participant to team: " + e.getMessage(), 500));
        }
    }

    @PutMapping("/{memberId}")
    public ResponseEntity<ApiResponseDTO<TeamMemberResponseDTO>> updateTeamMember(@PathVariable Long teamId, @PathVariable Long memberId, @RequestBody UpdateTeamMemberDTO updateDTO) {
        try {
            TeamMemberResponseDTO updatedMember = teamMemberService.updateTeamMember(teamId, memberId, updateDTO);
            return ResponseEntity.ok(ApiResponseDTO.success(updatedMember, "Team member updated successfully"));
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponseDTO.error("Team member not found: " + e.getMessage(), 404));
            } else {
                return ResponseEntity.badRequest()
                        .body(ApiResponseDTO.error("Invalid update request: " + e.getMessage(), 400));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDTO.error("Failed to update team member: " + e.getMessage(), 500));
        }
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<ApiResponseDTO<String>> removeParticipantFromTeam(@PathVariable Long teamId, @PathVariable Long memberId) {
        try {
            teamMemberService.removeParticipantFromTeam(teamId, memberId);
            return ResponseEntity.ok(ApiResponseDTO.success("Participant removed successfully", "Participant has been removed from the team"));
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponseDTO.error("Team member not found: " + e.getMessage(), 404));
            } else {
                return ResponseEntity.badRequest()
                        .body(ApiResponseDTO.error("Cannot remove participant: " + e.getMessage(), 400));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDTO.error("Failed to remove participant: " + e.getMessage(), 500));
        }
    }
    
    @DeleteMapping("/participant/{participantId}")
    public ResponseEntity<ApiResponseDTO<String>> removeParticipantFromTeamByParticipantId(@PathVariable Long teamId, @PathVariable Long participantId) {
        try {
            teamMemberService.removeParticipantFromTeamByParticipantId(teamId, participantId);
            return ResponseEntity.ok(ApiResponseDTO.success("Participant removed successfully", "Participant has been removed from the team"));
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponseDTO.error("Resource not found: " + e.getMessage(), 404));
            } else {
                return ResponseEntity.badRequest()
                        .body(ApiResponseDTO.error("Cannot remove participant: " + e.getMessage(), 400));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDTO.error("Failed to remove participant: " + e.getMessage(), 500));
        }
    }
    
    @GetMapping("/role/{role}")
    public ResponseEntity<ApiResponseDTO<List<TeamMemberResponseDTO>>> getParticipantsByRole(@PathVariable Long teamId, @PathVariable String role) {
        try {
            List<TeamMemberResponseDTO> members = teamMemberService.getParticipantsByRole(teamId, role);
            return ResponseEntity.ok(ApiResponseDTO.success(members, "Participants retrieved by role successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDTO.error("Failed to retrieve participants by role: " + e.getMessage(), 500));
        }
    }
    
    @GetMapping("/check/{participantId}")
    public ResponseEntity<ApiResponseDTO<Boolean>> isParticipantInTeam(@PathVariable Long teamId, @PathVariable Long participantId) {
        try {
            boolean isInTeam = teamMemberService.isParticipantInTeam(teamId, participantId);
            return ResponseEntity.ok(ApiResponseDTO.success(isInTeam, "Participant membership check completed"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDTO.error("Failed to check participant membership: " + e.getMessage(), 500));
        }
    }
    
    @GetMapping("/available")
    public ResponseEntity<ApiResponseDTO<List<ParticipantResponseDTO>>> getAvailableParticipantsForTeam(@PathVariable Long teamId) {
        try {
            List<ParticipantResponseDTO> availableParticipants = teamMemberService.getAvailableParticipantsForTeam(teamId);
            return ResponseEntity.ok(ApiResponseDTO.success(availableParticipants, "Available participants retrieved successfully"));
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponseDTO.error("Team not found: " + e.getMessage(), 404));
            } else {
                return ResponseEntity.badRequest()
                        .body(ApiResponseDTO.error("Invalid request: " + e.getMessage(), 400));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDTO.error("Failed to retrieve available participants: " + e.getMessage(), 500));
        }
    }
} 