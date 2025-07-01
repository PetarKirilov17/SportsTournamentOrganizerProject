package com.example.demo.services;

import com.example.demo.entities.Match;
import com.example.demo.entities.Team;
import com.example.demo.entities.Tournament;
import com.example.demo.entities.Venue;
import com.example.demo.enums.MatchStatus;
import com.example.demo.repositories.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MatchService extends BasicService<Match> {
    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    private TournamentService tournamentService;
    @Autowired
    private TeamService teamService;
    @Autowired
    private VenueService venueService;

    @Override
    protected JpaRepository<Match, Long> getRepository() {
        return matchRepository;
    }

    public List<Match> getAllMatchesByTournamentId(Long tournamentId) {
        return matchRepository.findByTournamentId(tournamentId);
    }

    public Match saveMatch(Long tournamentId, Long homeTeamId, Long awayTeamId, Long venueId, MatchStatus status, LocalDateTime scheduledAt, Integer homeScore, Integer awayScore) {
        Tournament tournament = tournamentService.getById(tournamentId);
        if (tournament == null) {
            throw new IllegalArgumentException("Tournament with id " + tournamentId + " not found");
        }
        Team homeTeam = teamService.getById(homeTeamId);
        if (homeTeam == null) {
            throw new IllegalArgumentException("Home team with id " + homeTeamId + " not found");
        }
        Team awayTeam = teamService.getById(awayTeamId);
        if (awayTeam == null) {
            throw new IllegalArgumentException("Away team with id " + awayTeamId + " not found");
        }
        Venue venue = venueService.getById(venueId);
        if (venue == null) {
            throw new IllegalArgumentException("Venue with id " + venueId + " not found");
        }
        Match match = new Match();
        match.setTournament(tournament);
        match.setHomeTeam(homeTeam);
        match.setAwayTeam(awayTeam);
        match.setVenue(venue);
        match.setStatus(status != null ? status : MatchStatus.SCHEDULED);
        match.setScheduledAt(scheduledAt);
        match.setHomeScore(homeScore != null ? homeScore : 0);
        match.setAwayScore(awayScore != null ? awayScore : 0);
        return getRepository().save(match);
    }

    public Match updateMatch(Long tournamentId, Long id, Long homeTeamId, Long awayTeamId, Long venueId, MatchStatus status, LocalDateTime scheduledAt, Integer homeScore, Integer awayScore) {
        Tournament tournament = tournamentService.getById(tournamentId);
        if (tournament == null) {
            throw new IllegalArgumentException("Tournament with id " + tournamentId + " not found");
        }
        Match match = getById(id);
        if (match == null || !match.getTournament().getId().equals(tournamentId)) {
            throw new IllegalArgumentException("Match with id " + id + " not found for tournament " + tournamentId);
        }
        Team homeTeam = teamService.getById(homeTeamId);
        if (homeTeam == null) {
            throw new IllegalArgumentException("Home team with id " + homeTeamId + " not found");
        }
        Team awayTeam = teamService.getById(awayTeamId);
        if (awayTeam == null) {
            throw new IllegalArgumentException("Away team with id " + awayTeamId + " not found");
        }
        Venue venue = venueService.getById(venueId);
        if (venue == null) {
            throw new IllegalArgumentException("Venue with id " + venueId + " not found");
        }
        match.setHomeTeam(homeTeam);
        match.setAwayTeam(awayTeam);
        match.setVenue(venue);
        match.setStatus(status != null ? status : MatchStatus.SCHEDULED);
        match.setScheduledAt(scheduledAt);
        match.setHomeScore(homeScore != null ? homeScore : 0);
        match.setAwayScore(awayScore != null ? awayScore : 0);
        return getRepository().save(match);
    }
}