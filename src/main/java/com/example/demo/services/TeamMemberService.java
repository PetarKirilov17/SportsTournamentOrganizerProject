package com.example.demo.services;

import com.example.demo.dto.AddTeamMemberDTO;
import com.example.demo.dto.TeamMemberResponseDTO;
import com.example.demo.dto.UpdateTeamMemberDTO;
import com.example.demo.entities.Participant;
import com.example.demo.entities.Team;
import com.example.demo.entities.TeamMember;
import com.example.demo.repositories.ParticipantRepository;
import com.example.demo.repositories.TeamMemberRepository;
import com.example.demo.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamMemberService extends BasicService<TeamMember> {
    @Autowired
    private TeamMemberRepository teamMemberRepository;
    
    @Autowired
    private TeamRepository teamRepository;
    
    @Autowired
    private ParticipantRepository participantRepository;

    @Override
    protected JpaRepository<TeamMember, Long> getRepository() {
        return teamMemberRepository;
    }
    
    public List<TeamMember> findByTeamId(Long teamId) {
        return teamMemberRepository.findByTeamId(teamId);
    }
    
    public List<TeamMemberResponseDTO> getTeamMembersByTeamId(Long teamId) {
        List<TeamMember> teamMembers = teamMemberRepository.findByTeamId(teamId);
        return teamMembers.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public TeamMemberResponseDTO getTeamMemberById(Long teamId, Long memberId) {
        TeamMember teamMember = teamMemberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Team member not found with id: " + memberId));
        
        // Verify the member belongs to the specified team
        if (!teamMember.getTeam().getId().equals(teamId)) {
            throw new IllegalArgumentException("Team member does not belong to the specified team");
        }
        
        return convertToDTO(teamMember);
    }
    
    public TeamMemberResponseDTO addParticipantToTeam(Long teamId, AddTeamMemberDTO addDTO) {
        // Verify team exists
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new IllegalArgumentException("Team not found with id: " + teamId));
        
        // Verify participant exists
        Participant participant = participantRepository.findById(addDTO.getParticipantId())
                .orElseThrow(() -> new IllegalArgumentException("Participant not found with id: " + addDTO.getParticipantId()));
        
        // Check if participant is already a member of this team
        if (teamMemberRepository.existsByTeamIdAndParticipantId(teamId, addDTO.getParticipantId())) {
            throw new IllegalArgumentException("Participant is already a member of this team");
        }
        
        // Check if jersey number is already taken in this team (if provided)
        if (addDTO.getJerseyNumber() != null) {
            List<TeamMember> existingMembers = teamMemberRepository.findByTeamId(teamId);
            boolean jerseyNumberTaken = existingMembers.stream()
                    .anyMatch(member -> addDTO.getJerseyNumber().equals(member.getJerseyNumber()));
            
            if (jerseyNumberTaken) {
                throw new IllegalArgumentException("Jersey number " + addDTO.getJerseyNumber() + " is already taken in this team");
            }
        }
        
        // Create new team member
        TeamMember teamMember = new TeamMember();
        teamMember.setTeam(team);
        teamMember.setParticipant(participant);
        teamMember.setRole(addDTO.getRole() != null ? addDTO.getRole() : "player");
        teamMember.setJerseyNumber(addDTO.getJerseyNumber());
        
        TeamMember savedMember = teamMemberRepository.save(teamMember);
        return convertToDTO(savedMember);
    }
    
    public TeamMemberResponseDTO updateTeamMember(Long teamId, Long memberId, UpdateTeamMemberDTO updateDTO) {
        // Verify team exists
        teamRepository.findById(teamId)
                .orElseThrow(() -> new IllegalArgumentException("Team not found with id: " + teamId));
        
        // Get existing team member
        TeamMember existingMember = teamMemberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Team member not found with id: " + memberId));
        
        // Verify the member belongs to the specified team
        if (!existingMember.getTeam().getId().equals(teamId)) {
            throw new IllegalArgumentException("Team member does not belong to the specified team");
        }
        
        // Update role if provided
        if (updateDTO.getRole() != null && !updateDTO.getRole().trim().isEmpty()) {
            existingMember.setRole(updateDTO.getRole());
        }
        
        // Update jersey number if provided
        if (updateDTO.getJerseyNumber() != null) {
            // Check if the new jersey number is already taken by another member in the same team
            List<TeamMember> otherMembers = teamMemberRepository.findByTeamId(teamId);
            boolean jerseyNumberTaken = otherMembers.stream()
                    .filter(member -> !member.getId().equals(memberId)) // Exclude current member
                    .anyMatch(member -> updateDTO.getJerseyNumber().equals(member.getJerseyNumber()));
            
            if (jerseyNumberTaken) {
                throw new IllegalArgumentException("Jersey number " + updateDTO.getJerseyNumber() + " is already taken in this team");
            }
            
            existingMember.setJerseyNumber(updateDTO.getJerseyNumber());
        }
        
        TeamMember updatedMember = teamMemberRepository.save(existingMember);
        return convertToDTO(updatedMember);
    }
    
    public void removeParticipantFromTeam(Long teamId, Long memberId) {
        // Verify team exists
        teamRepository.findById(teamId)
                .orElseThrow(() -> new IllegalArgumentException("Team not found with id: " + teamId));
        
        // Get existing team member
        TeamMember existingMember = teamMemberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Team member not found with id: " + memberId));
        
        // Verify the member belongs to the specified team
        if (!existingMember.getTeam().getId().equals(teamId)) {
            throw new IllegalArgumentException("Team member does not belong to the specified team");
        }
        
        teamMemberRepository.deleteById(memberId);
    }
    
    public void removeParticipantFromTeamByParticipantId(Long teamId, Long participantId) {
        // Verify team exists
        teamRepository.findById(teamId)
                .orElseThrow(() -> new IllegalArgumentException("Team not found with id: " + teamId));
        
        // Verify participant exists
        participantRepository.findById(participantId)
                .orElseThrow(() -> new IllegalArgumentException("Participant not found with id: " + participantId));
        
        // Find the team member record
        TeamMember teamMember = teamMemberRepository.findByTeamIdAndParticipantId(teamId, participantId)
                .orElseThrow(() -> new IllegalArgumentException("Participant is not a member of this team"));
        
        teamMemberRepository.deleteById(teamMember.getId());
    }
    
    public boolean isParticipantInTeam(Long teamId, Long participantId) {
        return teamMemberRepository.existsByTeamIdAndParticipantId(teamId, participantId);
    }
    
    public List<TeamMemberResponseDTO> getParticipantsByRole(Long teamId, String role) {
        List<TeamMember> teamMembers = teamMemberRepository.findByTeamIdAndRole(teamId, role);
        return teamMembers.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    private TeamMemberResponseDTO convertToDTO(TeamMember teamMember) {
        TeamMemberResponseDTO dto = new TeamMemberResponseDTO();
        dto.setId(teamMember.getId());
        dto.setParticipantId(teamMember.getParticipant().getId());
        dto.setFirstName(teamMember.getParticipant().getFirstName());
        dto.setLastName(teamMember.getParticipant().getLastName());
        dto.setEmail(teamMember.getParticipant().getEmail());
        dto.setRole(teamMember.getRole());
        dto.setJerseyNumber(teamMember.getJerseyNumber());
        dto.setAddedAt(teamMember.getAddedAt());
        return dto;
    }
} 