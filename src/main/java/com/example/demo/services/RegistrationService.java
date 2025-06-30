package com.example.demo.services;

import com.example.demo.entities.Registration;
import com.example.demo.entities.Team;
import com.example.demo.entities.Tournament;
import com.example.demo.enums.RegistrationStatus;
import com.example.demo.repositories.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistrationService extends BasicService<Registration> {
    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private TournamentService tournamentService;

    @Autowired
    private TeamService teamService;

    @Override
    protected JpaRepository<Registration, Long> getRepository() {
        return registrationRepository;
    }

    public List<Registration> getRegistrationsByTournamentId(Long tournamentId) {
        return registrationRepository.findAllByTournamentId(tournamentId);
    }

    public Registration createRegistration(Long tournamentId, Long teamId, RegistrationStatus status) {
        Tournament tournament = tournamentService.getById(tournamentId);
        if (tournament == null) {
            throw new IllegalArgumentException("Tournament with id " + tournamentId + " not found");
        }

        Team team = teamService.getById(teamId);
        if (team == null) {
            throw new IllegalArgumentException("Team with id " + teamId + " not found");
        }

        Registration registration = new Registration();
        registration.setTournament(tournament);
        registration.setTeam(team);
        registration.setStatus(status != null ? status : RegistrationStatus.REGISTERED);

        return getRepository().save(registration);
    }
} 