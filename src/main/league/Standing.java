package main.league;

import com.ppstudios.footballmanager.api.contracts.league.IStanding;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;

public class Standing implements IStanding {

    private final ITeam team;

    private int points;
    private int wins;
    private int draws;
    private int losses;
    private int goalsScored;
    private int goalsConceded;

    public Standing(ITeam team) {
        if (team == null) {
            throw new IllegalArgumentException("Equipa não pode ser null.");
        }
        this.team = team;
    }

    @Override
    public ITeam getTeam() {
        return team;
    }

    @Override
    public int getPoints() {
        return points;
    }

    @Override
    public void addPoints(int var1) {
        points += var1;
    }

    @Override
    public void addWin(int var1) {
        wins += var1;
    }

    @Override
    public void addDraw(int var1) {
        draws += var1;
    }

    @Override
    public void addLoss(int var1) {
        losses += var1;
    }

    @Override
    public int getWins() {
        return wins;
    }

    @Override
    public int getDraws() {
        return draws;
    }

    @Override
    public int getLosses() {
        return losses;
    }

    @Override
    public int getTotalMatches() {
        return wins + draws + losses;
    }

    @Override
    public int getGoalScored() {
        return goalsScored;
    }

    @Override
    public int getGoalsConceded() {
        return goalsConceded;
    }

    @Override
    public int getGoalDifference() {
        return goalsScored - goalsConceded;
    }

    public void addGoalsScored(int value) {
        goalsScored += value;
    }

    public void addGoalsConceded(int value) {
        goalsConceded += value;
    }

    public void registarResultado(int marcados, int sofridos) {
        addGoalsScored(marcados);
        addGoalsConceded(sofridos);

        if (marcados > sofridos) {
            addWin(1);
            addPoints(3);
        } else if (marcados == sofridos) {
            addDraw(1);
            addPoints(1);
        } else {
            addLoss(1);
        }
    }
}
