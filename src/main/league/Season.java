package main.league;

import com.ppstudios.footballmanager.api.contracts.league.ISeason;
import com.ppstudios.footballmanager.api.contracts.league.IStanding;
import com.ppstudios.footballmanager.api.contracts.league.ISchedule;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.simulation.MatchSimulatorStrategy;
import main.match.GameEvent;
import main.match.Match;
import main.simulation.MatchSimulatorStrategy;

public class Season implements ISeason {

    private final String name;
    private final int year;
    private final IClub[] clubs;
    private final Standing[] standings;
    private int clubCount;

    private final int maxRounds;
    private final int maxTeams;

    private int currentRound;
    private MatchSimulatorStrategy simulator;

    private final int pointsPerWin = 3;
    private final int pointsPerDraw = 1;
    private final int pointsPerLoss = 0;

    private Schedule schedule;

    public Season(String name, int year, int maxTeams, int maxRounds) {
        this.name = name;
        this.year = year;
        this.maxTeams = maxTeams;
        this.maxRounds = maxRounds;
        this.clubs = new IClub[maxTeams];
        this.standings = new Standing[maxTeams];
        this.clubCount = 0;
        this.currentRound = 0;
    }

    @Override
    public int getYear() {
        return year;
    }

    @Override
    public boolean addClub(IClub club) {
        if (club == null || clubCount >= maxTeams) return false;

        clubs[clubCount] = club;
        standings[clubCount] = new Standing(null);
        clubCount++;
        return true;
    }

    @Override
    public boolean removeClub(IClub club) {
        if (club == null) return false;

        for (int i = 0; i < clubCount; i++) {
            if (clubs[i].equals(club)) {
                for (int j = i; j < clubCount - 1; j++) {
                    clubs[j] = clubs[j + 1];
                    standings[j] = standings[j + 1];
                }
                clubs[--clubCount] = null;
                standings[clubCount] = null;
                return true;
            }
        }
        return false;
    }

    @Override
    public void generateSchedule() {
        if (clubCount < 2) {
            throw new IllegalStateException("É necessário pelo menos 2 clubes.");
        }

        if (schedule != null && schedule.getAllMatches().length > 0) {
            throw new IllegalStateException("Já existe um calendário.");
        }

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

                rounds[round][matchIndex++] = new Match(clubs[home], clubs[away], round + 1);
            }
        }

        schedule.setInternalRounds(rounds); // já adicionaste este método em Schedule
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
        for (IMatch match : matches) {
            if (match != null && !match.isPlayed()) {
                simulator.simulate(match);
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
                match.getTotalByEvent(GameEvent.class, match.getHomeClub()) +
                " - " +
                match.getTotalByEvent(GameEvent.class, match.getAwayClub()) + " " +
                match.getAwayClub().getName();
    }

    @Override
    public void setMatchSimulator(MatchSimulatorStrategy matchSimulatorStrategy) {
        if (matchSimulatorStrategy != null) {
            this.simulator = (MatchSimulatorStrategy) matchSimulatorStrategy;
        }
    }

    @Override
    public IStanding[] getLeagueStandings() {
        return standings;
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
}
