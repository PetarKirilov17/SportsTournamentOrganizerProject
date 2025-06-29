package com.example.demo.repositories;

import com.example.demo.entities.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
    
    // Derived query methods
    List<TeamMember> findByTeamId(Long teamId);
    List<TeamMember> findByParticipantId(Long participantId);
    Optional<TeamMember> findByTeamIdAndParticipantId(Long teamId, Long participantId);
    List<TeamMember> findByRole(String role);
    List<TeamMember> findByRoleIgnoreCase(String role);
    List<TeamMember> findByTeamIdAndRole(Long teamId, String role);
    boolean existsByTeamIdAndParticipantId(Long teamId, Long participantId);
} 