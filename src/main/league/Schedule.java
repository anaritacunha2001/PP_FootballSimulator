/*
 * Nome: Ana Rita Dias Cunha
 * Número: 8210440
 * Turma: T1
 *
 * Nome: Carlos Barbosa
 * Número: 8210417
 * Turma: <Turma do colega de grupo>
 */

package main.league;

import com.ppstudios.footballmanager.api.contracts.league.ISchedule;
import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;

/**
 * Implementa o calendário (schedule) de uma época desportiva,
 * organizando os jogos por rondas.
 */
public class Schedule implements ISchedule {

    private final IMatch[][] rounds;
    private final int numberOfRounds;
    private final int matchesPerRound;

    /**
     * Construtor da classe Schedule.
     *
     * @param numberOfRounds   Número total de rondas.
     * @param matchesPerRound  Número de jogos por ronda.
     */
    public Schedule(int numberOfRounds, int matchesPerRound) {
        this.numberOfRounds = numberOfRounds;
        this.matchesPerRound = matchesPerRound;
        this.rounds = new IMatch[numberOfRounds][matchesPerRound];
    }

    /**
     * Devolve todos os jogos do calendário.
     */
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

        IMatch[] result = new IMatch[index];
        System.arraycopy(all, 0, result, 0, index);
        return result;
    }

    /**
     * Devolve os jogos de uma determinada ronda.
     *
     * @param round Número da ronda (0-based).
     */
    @Override
    public IMatch[] getMatchesForRound(int round) {
        if (round < 0 || round >= numberOfRounds) return new IMatch[0];
        return rounds[round];
    }

    /**
     * Devolve os jogos de uma equipa durante toda a época.
     *
     * @param team Equipa cujos jogos queremos obter.
     */
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

    /**
     * Devolve o número total de rondas.
     */
    @Override
    public int getNumberOfRounds() {
        return numberOfRounds;
    }

    /**
     * Atribui uma equipa aos jogos de uma determinada ronda.
     *
     * @param team  Equipa a associar.
     * @param round Número da ronda (0-based).
     */
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

    /**
     * Exporta o calendário para o formato JSON simulado (output para consola).
     */
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

    /**
     * Define internamente os jogos para todas as rondas.
     *
     * @param rounds Array bidimensional com os jogos por ronda.
     */
    void setInternalRounds(IMatch[][] rounds) {
        for (int i = 0; i < rounds.length; i++) {
            for (int j = 0; j < rounds[i].length; j++) {
                this.rounds[i][j] = rounds[i][j];
            }
        }
    }

}