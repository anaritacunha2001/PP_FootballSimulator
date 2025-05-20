package main.league;

import com.ppstudios.footballmanager.api.contracts.league.ISchedule;
import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;

public class Schedule implements ISchedule {

    private final IMatch[][] rounds;
    private final int numberOfRounds;
    private final int matchesPerRound;

    public Schedule(int numberOfRounds, int matchesPerRound) {
        this.numberOfRounds = numberOfRounds;
        this.matchesPerRound = matchesPerRound;
        this.rounds = new IMatch[numberOfRounds][matchesPerRound];
    }

    @Override
    public IMatch[] getAllMatches() {
        int total = numberOfRounds * matchesPerRound;
        IMatch[] all = new IMatch[total];
        int index = 0;

        for (int r = 0; r < numberOfRounds; r++) {
            for (int m = 0; m < matchesPerRound; m++) {
                if (rounds[r][m] != null) {
                    all[index++] = rounds[r][m];
                }
            }
        }

        // Ajustar tamanho
        IMatch[] result = new IMatch[index];
        System.arraycopy(all, 0, result, 0, index);
        return result;
    }

    @Override
    public IMatch[] getMatchesForRound(int round) {
        if (round < 0 || round >= numberOfRounds) return new IMatch[0];
        return rounds[round];
    }

    @Override
    public IMatch[] getMatchesForTeam(ITeam team) {
        IMatch[] found = new IMatch[numberOfRounds * matchesPerRound];
        int count = 0;

        for (int r = 0; r < numberOfRounds; r++) {
            for (int m = 0; m < matchesPerRound; m++) {
                IMatch match = rounds[r][m];
                if (match != null && (match.getHomeTeam().equals(team) || match.getAwayTeam().equals(team))) {
                    found[count++] = match;
                }
            }
        }

        IMatch[] result = new IMatch[count];
        System.arraycopy(found, 0, result, 0, count);
        return result;
    }

    @Override
    public int getNumberOfRounds() {
        return numberOfRounds;
    }

    @Override
    public void setTeam(ITeam team, int round) {
        if (team == null || round < 0 || round >= numberOfRounds) return;

        for (int i = 0; i < matchesPerRound; i++) {
            IMatch match = rounds[round][i];
            if (match != null) {
                match.setTeam(team);
            }
        }
    }

    @Override
    public void exportToJson() {
        System.out.println("{");
        System.out.println("  \"rounds\": " + numberOfRounds + ",");
        System.out.println("  \"matches\": [");

        for (int r = 0; r < numberOfRounds; r++) {
            for (int m = 0; m < matchesPerRound; m++) {
                IMatch match = rounds[r][m];
                if (match != null) {
                    System.out.println("    \"" + match.getHomeClub().getName() + " vs " + match.getAwayClub().getName() + "\",");
                }
            }
        }

        System.out.println("  ]");
        System.out.println("}");
    }


    void setInternalRounds(IMatch[][] rounds) {
        for (int i = 0; i < rounds.length; i++) {
            for (int j = 0; j < rounds[i].length; j++) {
                this.rounds[i][j] = rounds[i][j];
            }
        }
    }

}

