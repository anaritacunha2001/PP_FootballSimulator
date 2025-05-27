/*
 * Nome: Ana Rita Dias Cunha
 * Número: XXXXX
 * Turma: XXXX
 */

package main.league;

import com.ppstudios.footballmanager.api.contracts.league.ISeason;
import com.ppstudios.footballmanager.api.contracts.league.IStanding;
import com.ppstudios.footballmanager.api.contracts.league.ISchedule;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;
import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.simulation.MatchSimulatorStrategy;

import main.match.GameEvent;
import main.match.Match;
import main.manager.Team;
import main.manager.Formation;

import java.io.Serializable;

/**
 * Representa uma época desportiva com clubes, equipas, calendário e simulação de jornadas.
 */
public class Season implements ISeason, Serializable {
    private static final long serialVersionUID = 1L;

    private final String name;
    private final int year;
    private final IClub[] clubs;
    private final ITeam[] teams;
    private final Standing[] standings;
    private int clubCount;

    private final int maxRounds;
    private final int maxTeams;

    private int currentRound;
    private MatchSimulatorStrategy simulator;

    private final int pointsPerWin  = 3;
    private final int pointsPerDraw = 1;
    private final int pointsPerLoss = 0;

    private Schedule schedule;

    private int indexOf(IClub club) {
        for (int i = 0; i < clubCount; i++) {
            if (clubs[i].equals(club)) {
                return i;
            }
        }
        return -1;
    }

    public Season(String name, int year, int maxTeams, int maxRounds) {
        if (name == null) {
            throw new IllegalArgumentException("Nome da época não pode ser null.");
        }
        this.name       = name;
        this.year       = year;
        this.maxTeams   = maxTeams;
        this.maxRounds  = maxRounds;
        this.clubs      = new IClub[maxTeams];
        this.teams      = new ITeam[maxTeams];
        this.standings  = new Standing[maxTeams];
        this.clubCount  = 0;
        this.currentRound = 0;
    }

    @Override
    public int getYear() {
        return year;
    }

    @Override
    public boolean addClub(IClub club) {
        return addClub(club, "4-4-2");
    }

    public boolean addClub(IClub club, String formacao) {
        if (club == null || clubCount >= maxTeams) {
            return false;
        }

        clubs[clubCount] = club;
        ITeam team = new Team(club, new Formation(formacao));
        teams[clubCount] = team;
        standings[clubCount] = new Standing(team);
        clubCount++;
        return true;
    }

    @Override
    public boolean removeClub(IClub club) {
        if (club == null) return false;

        for (int i = 0; i < clubCount; i++) {
            if (clubs[i].equals(club)) {
                for (int j = i; j < clubCount - 1; j++) {
                    clubs[j]     = clubs[j + 1];
                    teams[j]     = teams[j + 1];
                    standings[j] = standings[j + 1];
                }
                clubs[--clubCount]     = null;
                teams[clubCount]       = null;
                standings[clubCount]   = null;
                return true;
            }
        }
        return false;
    }

    @Override
    public void generateSchedule() {
        if (clubCount < 2) throw new IllegalStateException("É necessário pelo menos 2 clubes.");
        if (schedule != null && schedule.getAllMatches().length > 0)
            throw new IllegalStateException("Já existe um calendário gerado.");

        int totalRounds = clubCount - 1;
        int matchesPerRound = clubCount / 2;
        schedule = new Schedule(totalRounds, matchesPerRound);

        IMatch[][] rounds = new IMatch[totalRounds][matchesPerRound];
        for (int round = 0; round < totalRounds; round++) {
            int matchIndex = 0;
            for (int i = 0; i < clubCount / 2; i++) {
                int home = (round + i) % clubCount;
                int away = (clubCount - 1 - i + round) % clubCount;
                if (home == away) continue;

                Match m = new Match(clubs[home], clubs[away], round + 1);
                m.setTeam(teams[home]);
                m.setTeam(teams[away]);
                rounds[round][matchIndex++] = m;
            }
        }
        schedule.setInternalRounds(rounds);
    }

    @Override
    public IMatch[] getMatches() {
        return schedule != null ? schedule.getAllMatches() : new IMatch[0];
    }

    @Override
    public IMatch[] getMatches(int round) {
        return schedule != null ? schedule.getMatchesForRound(round) : new IMatch[0];
    }

    @Override
    public void simulateRound() {
        if (simulator == null || schedule == null || isSeasonComplete()) return;

        IMatch[] matches = schedule.getMatchesForRound(currentRound);
        System.out.println("\n--- Resultados da Ronda " + (currentRound + 1) + " ---");

        for (IMatch im : matches) {
            if (im != null && !im.isPlayed()) {
                simulator.simulate(im);
                IClub homeClub = im.getHomeClub();
                IClub awayClub = im.getAwayClub();
                int homeGoals = im.getTotalByEvent(GameEvent.class, homeClub);
                int awayGoals = im.getTotalByEvent(GameEvent.class, awayClub);
                standings[indexOf(homeClub)].registarResultado(homeGoals, awayGoals);
                standings[indexOf(awayClub)].registarResultado(awayGoals, homeGoals);
                System.out.println(displayMatchResult(im));
            }
        }

        currentRound++;
    }

    @Override
    public void simulateSeason() {
        while (!isSeasonComplete()) {
            simulateRound();
        }
    }

    @Override
    public int getCurrentRound() {
        return currentRound;
    }

    @Override
    public boolean isSeasonComplete() {
        return schedule != null && currentRound >= schedule.getNumberOfRounds();
    }

    @Override
    public void resetSeason() {
        currentRound = 0;
        schedule = null;
    }

    @Override
    public String displayMatchResult(IMatch match) {
        if (match == null) return "Jogo inválido.";
        return match.getHomeClub().getName() + " " +
                match.getTotalByEvent(GameEvent.class, match.getHomeClub()) + " - " +
                match.getTotalByEvent(GameEvent.class, match.getAwayClub()) + " " +
                match.getAwayClub().getName();
    }

    @Override
    public void setMatchSimulator(MatchSimulatorStrategy matchSimulatorStrategy) {
        if (matchSimulatorStrategy != null) this.simulator = matchSimulatorStrategy;
    }

    @Override
    public IStanding[] getLeagueStandings() {
        IStanding[] result = new IStanding[clubCount];
        System.arraycopy(standings, 0, result, 0, clubCount);
        return result;
    }

    @Override
    public ISchedule getSchedule() {
        return schedule;
    }

    @Override
    public int getPointsPerWin() {
        return pointsPerWin;
    }

    @Override
    public int getPointsPerDraw() {
        return pointsPerDraw;
    }

    @Override
    public int getPointsPerLoss() {
        return pointsPerLoss;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getMaxTeams() {
        return maxTeams;
    }

    @Override
    public int getMaxRounds() {
        return maxRounds;
    }

    @Override
    public int getCurrentMatches() {
        int count = 0;
        for (int i = 0; i < currentRound; i++) {
            count += schedule.getMatchesForRound(i).length;
        }
        return count;
    }

    @Override
    public int getNumberOfCurrentTeams() {
        return clubCount;
    }

    @Override
    public IClub[] getCurrentClubs() {
        IClub[] result = new IClub[clubCount];
        System.arraycopy(clubs, 0, result, 0, clubCount);
        return result;
    }

    @Override
    public void exportToJson() {
        System.out.println("{ \"season\": \"" + name + "\", \"year\": " + year + " }");
    }

    public void printFullSchedule() {
        if (schedule == null) {
            System.out.println("Calendário não gerado ainda.");
            return;
        }
        for (int round = 0; round < schedule.getNumberOfRounds(); round++) {
            System.out.println("Ronda " + (round + 1) + ":");
            for (IMatch match : getMatches(round)) {
                if (match != null)
                    System.out.println("  " + match.getHomeClub().getName() + " vs " + match.getAwayClub().getName());
            }
            System.out.println();
        }
    }

    public void simulateAndPrintSeason() {
        if (simulator == null) {
            System.out.println("Simulador não configurado.");
            return;
        }
        if (schedule == null) {
            System.out.println("Calendário não gerado.");
            return;
        }

        System.out.println("Início da simulação da época: " + name + " (" + year + ")");
        while (!isSeasonComplete()) {
            simulateRound();
            int roundJustPlayed = getCurrentRound() - 1;
            System.out.println("Resultados da Ronda " + (roundJustPlayed + 1) + ":");
            for (IMatch match : getMatches(roundJustPlayed)) {
                System.out.println("  " + displayMatchResult(match));
            }
            System.out.println();
        }
        System.out.println("Época terminada.");
    }
}
