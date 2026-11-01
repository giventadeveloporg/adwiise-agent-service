package com.adwiiseagent.service;

import com.adwiiseagent.domain.League;
import com.adwiiseagent.domain.LeagueClub;
import com.adwiiseagent.domain.LeagueMatch;
import com.adwiiseagent.domain.LeagueStanding;
import com.adwiiseagent.errors.BadRequestAlertException;
import com.adwiiseagent.repository.LeagueClubRepository;
import com.adwiiseagent.repository.LeagueMatchRepository;
import com.adwiiseagent.repository.LeagueRepository;
import com.adwiiseagent.repository.LeagueStandingRepository;
import com.adwiiseagent.service.dto.LeagueStandingRowDTO;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LeagueTableService {

    private final LeagueRepository leagueRepository;
    private final LeagueClubRepository leagueClubRepository;
    private final LeagueMatchRepository leagueMatchRepository;
    private final LeagueStandingRepository leagueStandingRepository;

    public LeagueTableService(
        LeagueRepository leagueRepository,
        LeagueClubRepository leagueClubRepository,
        LeagueMatchRepository leagueMatchRepository,
        LeagueStandingRepository leagueStandingRepository
    ) {
        this.leagueRepository = leagueRepository;
        this.leagueClubRepository = leagueClubRepository;
        this.leagueMatchRepository = leagueMatchRepository;
        this.leagueStandingRepository = leagueStandingRepository;
    }

    @Transactional(readOnly = true)
    public List<LeagueStandingRowDTO> table(Long leagueId) {
        League league = leagueRepository
            .findById(leagueId)
            .orElseThrow(() -> new BadRequestAlertException("League not found", "league", "idnotfound"));
        if ("MANUAL".equals(league.getStandingsSource())) {
            return manualTable(league);
        }
        return calculatedTable(league);
    }

    public List<LeagueStandingRowDTO> copyCalculated(Long leagueId) {
        League league = leagueRepository
            .findById(leagueId)
            .orElseThrow(() -> new BadRequestAlertException("League not found", "league", "idnotfound"));
        List<LeagueStandingRowDTO> rows = calculatedTable(league);
        leagueStandingRepository.deleteByLeagueId(leagueId);
        leagueStandingRepository.flush();
        int position = 1;
        for (LeagueStandingRowDTO row : rows) {
            LeagueStanding standing = new LeagueStanding();
            standing.setTenantId(league.getTenantId());
            standing.setLeagueId(league.getId());
            standing.setLeagueClubId(row.getLeagueClubId());
            standing.setPlayed(nz(row.getPlayed()));
            standing.setWon(nz(row.getWon()));
            standing.setDrawn(nz(row.getDrawn()));
            standing.setLost(nz(row.getLost()));
            standing.setGoalsFor(nz(row.getGoalsFor()));
            standing.setGoalsAgainst(nz(row.getGoalsAgainst()));
            standing.setPoints(nz(row.getPoints()));
            standing.setPosition(position++);
            leagueStandingRepository.save(standing);
        }
        league.setStandingsSource("MANUAL");
        leagueRepository.save(league);
        return manualTable(league);
    }

    private List<LeagueStandingRowDTO> calculatedTable(League league) {
        List<LeagueClub> clubs = leagueClubRepository.findByLeagueIdOrderByDisplayOrderAscNameAsc(league.getId());
        Map<Long, Acc> byClub = new HashMap<>();
        for (LeagueClub club : clubs) {
            byClub.put(club.getId(), new Acc(club));
        }
        int win = nz(league.getPointsForWin(), 3);
        int draw = nz(league.getPointsForDraw(), 1);
        int loss = nz(league.getPointsForLoss(), 0);
        for (LeagueMatch match : leagueMatchRepository.findByLeagueIdAndStatus(league.getId(), "PLAYED")) {
            if (match.getHomeScore() == null || match.getAwayScore() == null) {
                continue;
            }
            Acc home = byClub.get(match.getHomeClubId());
            Acc away = byClub.get(match.getAwayClubId());
            if (home == null || away == null) {
                continue;
            }
            home.played++;
            away.played++;
            home.goalsFor += match.getHomeScore();
            home.goalsAgainst += match.getAwayScore();
            away.goalsFor += match.getAwayScore();
            away.goalsAgainst += match.getHomeScore();
            if (match.getHomeScore() > match.getAwayScore()) {
                home.won++;
                home.points += win;
                away.lost++;
                away.points += loss;
            } else if (match.getHomeScore() < match.getAwayScore()) {
                away.won++;
                away.points += win;
                home.lost++;
                home.points += loss;
            } else {
                home.drawn++;
                away.drawn++;
                home.points += draw;
                away.points += draw;
            }
        }
        List<LeagueStandingRowDTO> rows = new ArrayList<>();
        for (Acc acc : byClub.values()) {
            rows.add(acc.toRow("CALCULATED", null));
        }
        rows.sort(calculatedOrder());
        assignPositions(rows);
        return rows;
    }

    private List<LeagueStandingRowDTO> manualTable(League league) {
        List<LeagueClub> clubs = leagueClubRepository.findByLeagueIdOrderByDisplayOrderAscNameAsc(league.getId());
        Map<Long, LeagueStanding> stored = new HashMap<>();
        boolean anyPosition = false;
        for (LeagueStanding standing : leagueStandingRepository.findByLeagueId(league.getId())) {
            stored.put(standing.getLeagueClubId(), standing);
            if (standing.getPosition() != null) {
                anyPosition = true;
            }
        }
        List<LeagueStandingRowDTO> rows = new ArrayList<>();
        for (LeagueClub club : clubs) {
            LeagueStanding standing = stored.get(club.getId());
            Acc acc = new Acc(club);
            if (standing != null) {
                acc.played = nz(standing.getPlayed());
                acc.won = nz(standing.getWon());
                acc.drawn = nz(standing.getDrawn());
                acc.lost = nz(standing.getLost());
                acc.goalsFor = nz(standing.getGoalsFor());
                acc.goalsAgainst = nz(standing.getGoalsAgainst());
                acc.points = nz(standing.getPoints());
            }
            rows.add(acc.toRow("MANUAL", standing == null ? null : standing.getPosition()));
        }
        if (anyPosition) {
            rows.sort(
                Comparator
                    .comparing((LeagueStandingRowDTO row) -> row.getPosition() == null ? Integer.MAX_VALUE : row.getPosition())
                    .thenComparing(row -> row.getClubName() == null ? "" : row.getClubName(), String.CASE_INSENSITIVE_ORDER)
            );
        } else {
            rows.sort(calculatedOrder());
            assignPositions(rows);
        }
        return rows;
    }

    private Comparator<LeagueStandingRowDTO> calculatedOrder() {
        return (a, b) -> {
            int points = Integer.compare(nz(b.getPoints()), nz(a.getPoints()));
            if (points != 0) {
                return points;
            }
            int gd = Integer.compare(nz(b.getGoalDifference()), nz(a.getGoalDifference()));
            if (gd != 0) {
                return gd;
            }
            int goals = Integer.compare(nz(b.getGoalsFor()), nz(a.getGoalsFor()));
            if (goals != 0) {
                return goals;
            }
            String left = a.getClubName() == null ? "" : a.getClubName();
            String right = b.getClubName() == null ? "" : b.getClubName();
            return left.compareToIgnoreCase(right);
        };
    }

    private void assignPositions(List<LeagueStandingRowDTO> rows) {
        int position = 1;
        for (LeagueStandingRowDTO row : rows) {
            row.setPosition(position++);
        }
    }

    private int nz(Integer value) {
        return value == null ? 0 : value;
    }

    private int nz(Integer value, int fallback) {
        return value == null ? fallback : value;
    }

    private final class Acc {

        private final LeagueClub club;
        private int played;
        private int won;
        private int drawn;
        private int lost;
        private int goalsFor;
        private int goalsAgainst;
        private int points;

        private Acc(LeagueClub club) {
            this.club = club;
        }

        private LeagueStandingRowDTO toRow(String source, Integer position) {
            LeagueStandingRowDTO row = new LeagueStandingRowDTO();
            row.setLeagueClubId(club.getId());
            row.setClubName(club.getName());
            row.setShortName(club.getShortName());
            row.setLogoUrl(club.getLogoUrl());
            row.setHomeClub(Boolean.TRUE.equals(club.getIsHomeClub()));
            row.setPlayed(played);
            row.setWon(won);
            row.setDrawn(drawn);
            row.setLost(lost);
            row.setGoalsFor(goalsFor);
            row.setGoalsAgainst(goalsAgainst);
            row.setGoalDifference(goalsFor - goalsAgainst);
            row.setPoints(points);
            row.setPosition(position);
            row.setSource(source);
            return row;
        }
    }
}
