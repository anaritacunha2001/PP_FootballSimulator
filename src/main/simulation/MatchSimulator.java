package main.simulation;

import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.simulation.MatchSimulatorStrategy;
import main.match.GameEvent;

import java.util.Random;

/**
 * Simula um jogo lançando entre 0 e 4 golos para cada equipa,
 * gerando um GameEvent de tipo "GOAL" em minutos aleatórios.
 */
public class MatchSimulator implements MatchSimulatorStrategy {
    private final Random rnd = new Random();

    /**
     * Simula um único jogo:
     *  - Decide aleatoriamente entre 0 e 4 golos para cada equipa.
     *  - Sorteia um jogador de cada equipa para marcar cada golo.
     *  - Regista cada golo como GameEvent no minuto aleatório.
     *  - Marca o jogo como jogado.
     */
    @Override
    public void simulate(IMatch match) {
        int homeGoals = rnd.nextInt(5); // 0 a 4
        int awayGoals = rnd.nextInt(5); // 0 a 4

        // retira os plantéis das equipas da casa e visitante
        IPlayer[] homePlayers = match.getHomeTeam().getPlayers();
        IPlayer[] awayPlayers = match.getAwayTeam().getPlayers();

        // Gera eventos de golo para a equipa da casa
        for (int i = 0; i < homeGoals; i++) {
            int minute = rnd.nextInt(90) + 1;
            IPlayer scorer = homePlayers[rnd.nextInt(homePlayers.length)];
            match.addEvent(new GameEvent(
                    minute,
                    scorer.getName() + " marcou",
                    "GOAL",
                    scorer,
                    match.getHomeTeam()
            ));
        }

        // Gera eventos de golo para a equipa visitante
        for (int i = 0; i < awayGoals; i++) {
            int minute = rnd.nextInt(90) + 1;
            IPlayer scorer = awayPlayers[rnd.nextInt(awayPlayers.length)];
            match.addEvent(new GameEvent(
                    minute,
                    scorer.getName() + " marcou",
                    "GOAL",
                    scorer,
                    match.getAwayTeam()
            ));
        }

        // Marca o jogo como jogado
        match.setPlayed();
    }
}
