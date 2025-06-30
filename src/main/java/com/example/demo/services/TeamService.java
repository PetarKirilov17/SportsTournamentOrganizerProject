package com.example.demo.services;

import com.example.demo.dto.TeamRequestDTO;
import com.example.demo.dto.TeamResponseDTO;
import com.example.demo.entities.Team;
import com.example.demo.entities.TeamMember;
import com.example.demo.repositories.TeamMemberRepository;
import com.example.demo.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TeamService extends BasicService<Team> {
    @Autowired
    private TeamRepository teamRepository;
    
    @Autowired
    private TeamMemberRepository teamMemberRepository;

    @Override
    protected JpaRepository<Team, Long> getRepository() {
        return teamRepository;
    }
    
    public TeamResponseDTO getTeamWithMembers(Long id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Team not found with id: " + id));
        return convertToDTO(team);
    }
    
    public List<TeamResponseDTO> getAllTeamsWithMembers() {
        Iterable<Team> teams = teamRepository.findAll();
        return StreamSupport.stream(teams.spliterator(), false)
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public Team updateTeam(TeamRequestDTO updateDTO, Long id) {
        Team existingTeam = teamRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Team not found with id: " + id));
        
        // Update only the fields that should be updated
        if (updateDTO.getName() != null && !updateDTO.getName().trim().isEmpty()) {
            // Check if the new name already exists for another team
            if (!existingTeam.getName().equalsIgnoreCase(updateDTO.getName()) && 
                teamRepository.existsByNameIgnoreCase(updateDTO.getName())) {
                throw new IllegalArgumentException("Team name already exists: " + updateDTO.getName());
            }
            existingTeam.setName(updateDTO.getName());
        }
        
        if (updateDTO.getCategory() != null) {
            existingTeam.setCategory(updateDTO.getCategory());
        }
        
        // Preserve the createdAt field and let Spring Data JPA handle updatedAt automatically
        return teamRepository.save(existingTeam);
    }
    
    public void deleteTeam(Long id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Team not found with id: " + id));
        
        // Check if team has members
        List<TeamMember> teamMembers = teamMemberRepository.findByTeamId(id);
        if (!teamMembers.isEmpty()) {
            throw new IllegalArgumentException("Cannot delete team with existing members. Remove all members first.");
        }
        
        // Check if team is registered in any tournaments
        // This would require a RegistrationRepository check, but for now we'll proceed
        // In a real application, you might want to check for active registrations
        
        teamRepository.deleteById(id);
    }
    
    public boolean existsByName(String name) {
        return teamRepository.existsByNameIgnoreCase(name);
    }
    
    public List<Team> findByCategory(com.example.demo.enums.TeamCategoryEnum category) {
        return teamRepository.findByCategory(category);
    }
    
    public List<Team> findByNameContaining(String name) {
        return teamRepository.findByNameContainingIgnoreCase(name);
    }
    
    private TeamResponseDTO convertToDTO(Team team) {
        TeamResponseDTO dto = new TeamResponseDTO();
        dto.setId(team.getId());
        dto.setName(team.getName());
        dto.setCategory(team.getCategory());
        dto.setCreatedAt(team.getCreatedAt());
        dto.setUpdatedAt(team.getUpdatedAt());
        
        // Get team members
        List<TeamMember> teamMembers = teamMemberRepository.findByTeamId(team.getId());
        if (teamMembers != null && !teamMembers.isEmpty()) {
            List<TeamResponseDTO.TeamMemberDTO> memberDTOs = teamMembers.stream()
                    .map(tm -> {
                        TeamResponseDTO.TeamMemberDTO memberDTO = new TeamResponseDTO.TeamMemberDTO();
                        memberDTO.setParticipantId(tm.getParticipant().getId());
                        memberDTO.setFirstName(tm.getParticipant().getFirstName());
                        memberDTO.setLastName(tm.getParticipant().getLastName());
                        memberDTO.setEmail(tm.getParticipant().getEmail());
                        memberDTO.setRole(tm.getRole());
                        memberDTO.setJerseyNumber(tm.getJerseyNumber());
                        memberDTO.setAddedAt(tm.getAddedAt());
                        return memberDTO;
                    })
                    .collect(Collectors.toList());
            dto.setTeamMembers(memberDTOs);
        }
        
        return dto;
    }
}
