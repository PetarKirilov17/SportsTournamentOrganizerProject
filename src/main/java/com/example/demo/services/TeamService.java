package com.example.demo.services;

import com.example.demo.entities.Team;
import com.example.demo.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class TeamService extends BasicService<Team> {
    @Autowired
    private TeamRepository teamRepository;

    @Override
    protected CrudRepository<Team, Long> getRepository() {
        return teamRepository;
    }

}
