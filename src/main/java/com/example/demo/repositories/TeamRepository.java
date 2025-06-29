package com.example.demo.repositories;

import com.example.demo.User;
import com.example.demo.entities.Team;
import com.example.demo.enums.TeamCategoryEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {
    
    // Derived query methods
    Optional<Team> findByName(String name);
    Optional<Team> findByNameIgnoreCase(String name);
    List<Team> findByCategory(TeamCategoryEnum category);
    List<Team> findByNameContainingIgnoreCase(String name);
    boolean existsByName(String name);
    boolean existsByNameIgnoreCase(String name);
}
