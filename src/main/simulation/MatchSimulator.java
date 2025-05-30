/*
 * Nome: Ana Rita Dias Cunha
 * Número: 8210440
 * Turma: T1
 *
 * Nome: Carlos Barbosa
 * Número: 8210417
 * Turma: T3
 */

package main.simulation;

import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.simulation.MatchSimulatorStrategy;
import main.match.GameEvent;

import java.util.Random;

/**
 * Implementação da estratégia de simulação de um jogo de futebol.
 * Gera aleatoriamente entre 0 e 4 golos por equipa e associa eventos do tipo "GOAL" ao jogo.
 */
public class MatchSimulator implements MatchSimulatorStrategy {
    private final Random rnd = new Random();

    /**
     * Simula um jogo:
     * - Gera um número aleatório de golos (0 a 4) para cada equipa.
     * - Escolhe aleatoriamente jogadores marcadores.
     * - Gera eventos do tipo "GOAL" com descrição e minuto aleatório.
     * - Marca o jogo como "jogado".
     */
    @Override
    public void simulate(IMatch match) {
        int homeGoals = rnd.nextInt(5); // Golos da equipa da casa (0 a 4)
        int awayGoals = rnd.nextInt(5); // Golos da equipa visitante (0 a 4)

        // Obtem os jogadores de cada equipa
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
