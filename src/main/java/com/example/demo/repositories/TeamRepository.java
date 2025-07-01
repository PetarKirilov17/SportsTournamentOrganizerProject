package com.example.demo.repositories;

import com.example.demo.entities.Team;
import com.example.demo.enums.TeamCategoryEnum;
import com.example.demo.dto.TeamLeaderboardDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    @Query("""
        select new com.example.demo.dto.TeamLeaderboardDTO(t.id, t.name, count(distinct m.id))
        from Team t
        join Registration r on r.team.id = t.id and r.tournament.id = :tournamentId
        left join Match m on (
            (m.homeTeam.id = t.id or m.awayTeam.id = t.id)
            and m.status = 'COMPLETED'
            and m.tournament.id = :tournamentId
            and (
                (m.homeTeam.id = t.id and m.homeScore > m.awayScore) or
                (m.awayTeam.id = t.id and m.awayScore > m.homeScore)
            )
        )
        group by t.id, t.name
        order by count(m.id) desc
    """)
    List<TeamLeaderboardDTO> getLeaderboardForTournament(@Param("tournamentId") Long tournamentId);
}
