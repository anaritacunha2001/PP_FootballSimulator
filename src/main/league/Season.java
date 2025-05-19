package main.league;

import com.ppstudios.footballmanager.api.contracts.league.ISeason;
import com.ppstudios.footballmanager.api.contracts.league.IStanding;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.league.ISchedule;
import main.match.Match;
import main.match.GameEvent;
import main.simulation.MatchSimulatorStrategyImpl;

public class Season implements ISeason {

    private final String name;
    private final int year;
    private final IClub[] clubs;
    private final Standing[] standings;
    private int clubCount;

    private final Match[][] matches;
    private int currentRound;

    private final int maxRounds;
    private final int maxTeams;

    private MatchSimulatorStrategyImpl simulator;

    private final int pointsPerWin = 3;
    private final int pointsPerDraw = 1;
    private final int pointsPerLoss = 0;

    public Season(String name, int year, int maxTeams, int maxRounds) {
        this.name = name;
        this.year = year;
        this.maxTeams = maxTeams;
        this.maxRounds = maxRounds;
        this.clubs = new IClub[maxTeams];
        this.standings = new Standing[maxTeams];
        this.matches = new Match[maxRounds][maxTeams]; // simplificado
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
        standings[clubCount] = new Standing(null); // ITeam será atribuído mais tarde
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
        // Geração simplificada
        // Podes implementar round-robin depois
    }

    @Override
    public IMatch[] getMatches() {
        int total = 0;
        for (int r = 0; r < maxRounds; r++) {
            for (int m = 0; m < maxTeams; m++) {
                if (matches[r][m] != null) total++;
            }
        }

        IMatch[] all = new IMatch[total];
        int idx = 0;
        for (int r = 0; r < maxRounds; r++) {
            for (int m = 0; m < maxTeams; m++) {
                if (matches[r][m] != null) {
                    all[idx++] = matches[r][m];
                }
            }
        }
        return all;
    }

    @Override
    public IMatch[] getMatches(int round) {
        if (round < 0 || round >= maxRounds) return new IMatch[0];
        return matches[round];
    }

    @Override
    public void simulateRound() {
        if (simulator == null || currentRound >= maxRounds) return;

        Match[] roundMatches = matches[currentRound];
        for (Match match : roundMatches) {
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
        return currentRound >= maxRounds;
    }

    @Override
    public void resetSeason() {
        for (int r = 0; r < maxRounds; r++) {
            for (int m = 0; m < maxTeams; m++) {
                matches[r][m] = null;
            }
        }
        currentRound = 0;
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
    public void setMatchSimulator(com.ppstudios.footballmanager.api.contracts.simulation.MatchSimulatorStrategy matchSimulatorStrategy) {

    }

   /*@Override
    public void setMatchSimulator(MatchSimulatorStrategy simulator) {
        this.simulator = simulator;
    }*/

    @Override
    public IStanding[] getLeagueStandings() {
        return standings;
    }

    @Override
    public ISchedule getSchedule() {
        // A criar depois
        return null;
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
        for (int r = 0; r < currentRound; r++) {
            for (int m = 0; m < maxTeams; m++) {
                if (matches[r][m] != null) count++;
            }
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

