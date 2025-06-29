package com.example.demo.services;

import com.example.demo.entities.TeamMember;
import com.example.demo.repositories.TeamMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class TeamMemberService extends BasicService<TeamMember> {
    @Autowired
    private TeamMemberRepository teamMemberRepository;

    @Override
    protected JpaRepository<TeamMember, Long> getRepository() {
        return teamMemberRepository;
    }
} 