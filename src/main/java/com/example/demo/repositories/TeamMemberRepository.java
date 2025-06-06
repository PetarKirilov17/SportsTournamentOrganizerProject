package com.example.demo.repositories;

import com.example.demo.entities.TeamMember;
import org.springframework.data.repository.CrudRepository;

public interface TeamMemberRepository extends CrudRepository<TeamMember, Long> {
} 