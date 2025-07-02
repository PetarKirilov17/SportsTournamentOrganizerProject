package com.example.demo.services;

import com.example.demo.dto.ParticipantResponseDTO;
import com.example.demo.dto.UpdateParticipantDTO;
import com.example.demo.entities.Participant;
import com.example.demo.repositories.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ParticipantService extends BasicService<Participant> {
    @Autowired
    private ParticipantRepository participantRepository;

    @Override
    protected JpaRepository<Participant, Long> getRepository() {
        return participantRepository;
    }

    public ParticipantResponseDTO getParticipantWithTeams(Long id) {
        Participant participant = participantRepository.findByIdWithTeamMembers(id)
                .orElseThrow(() -> new IllegalArgumentException("Participant not found with id: " + id));
        return convertToDTO(participant);
    }

    public List<ParticipantResponseDTO> getAllParticipantsWithTeams() {
        List<Participant> participants = participantRepository.findAllWithTeamMembers();
        return participants.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ParticipantResponseDTO> getParticipantsByTeamId(Long teamId) {
        List<Participant> participants = participantRepository.findByTeamId(teamId);
        return participants.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Participant updateParticipant(UpdateParticipantDTO updateDTO, Long id) {
        Participant existingParticipant = participantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Participant not found with id: " + id));
        
        // Update only the fields that should be updated
        if (updateDTO.getFirstName() != null) {
            existingParticipant.setFirstName(updateDTO.getFirstName());
        }
        if (updateDTO.getLastName() != null) {
            existingParticipant.setLastName(updateDTO.getLastName());
        }
        if (updateDTO.getEmail() != null) {
            existingParticipant.setEmail(updateDTO.getEmail());
        }
        if (updateDTO.getCategory() != null) {
            existingParticipant.setCategory(updateDTO.getCategory());
        }
        
        // Preserve the createdAt field and let Spring Data JPA handle updatedAt automatically
        return participantRepository.save(existingParticipant);
    }

    @Transactional
    public void deleteById(Long id) {
        participantRepository.deleteById(id);
    }

    private ParticipantResponseDTO convertToDTO(Participant participant) {
        ParticipantResponseDTO dto = new ParticipantResponseDTO();
        dto.setId(participant.getId());
        dto.setFirstName(participant.getFirstName());
        dto.setLastName(participant.getLastName());
        dto.setEmail(participant.getEmail());
        dto.setCategory(participant.getCategory());
        dto.setCreatedAt(participant.getCreatedAt());
        dto.setUpdatedAt(participant.getUpdatedAt());

        if (participant.getTeamMembers() != null) {
            List<ParticipantResponseDTO.TeamMembershipDTO> teamMemberships = participant.getTeamMembers().stream()
                    .map(tm -> {
                        ParticipantResponseDTO.TeamMembershipDTO membership = new ParticipantResponseDTO.TeamMembershipDTO();
                        membership.setTeamId(tm.getTeam().getId());
                        membership.setTeamName(tm.getTeam().getName());
                        membership.setTeamCategory(tm.getTeam().getCategory());
                        membership.setRole(tm.getRole());
                        membership.setJerseyNumber(tm.getJerseyNumber());
                        membership.setAddedAt(tm.getAddedAt());
                        return membership;
                    })
                    .collect(Collectors.toList());
            dto.setTeamMemberships(teamMemberships);
        }

        return dto;
    }
} 