package main.simulation;

import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.player.IPlayerPosition;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;
import main.match.GameEvent;
import main.match.Match;
import main.model.IExtendedPlayer;
import main.model.PlayerPosition;
import main.strategy.PlayerSelector;

import java.util.Random;

public class MatchSimulatorStrategy {

    private final Random random = new Random();

    public void simulate(IMatch match) {
        if (!(match instanceof Match)) return;

        Match m = (Match) match;
        ITeam home = m.getHomeTeam();
        ITeam away = m.getAwayTeam();
        IClub homeClub = home.getClub();
        IClub awayClub = away.getClub();

        PlayerSelector selector = new PlayerSelector();

        for (int minute = 1; minute <= 90; minute++) {
            // 20% de probabilidade de haver um lance
            if (random.nextDouble() < 0.2) {
                boolean homeAttack = random.nextBoolean();
                ITeam attackTeam = homeAttack ? home : away;
                ITeam defendTeam = homeAttack ? away : home;
                IClub attackClub = attackTeam.getClub();

                // Atacante aleatório da equipa atacante (STRIKER)
                IPlayer attacker = selector.selectPlayer(attackTeam.getClub(), PlayerPosition.STRIKER);
                if (!(attacker instanceof IExtendedPlayer)) continue;

                IExtendedPlayer ext = (IExtendedPlayer) attacker;
                int shooting = ext.getShooting();

                // Guarda-redes da equipa defensora
                IPlayer goalkeeper = selector.selectPlayer(defendTeam.getClub(), PlayerPosition.GOALKEEPER);
                if (!(goalkeeper instanceof IExtendedPlayer)) continue;

                IExtendedPlayer extGK = (IExtendedPlayer) goalkeeper;
                int reflexes = extGK.getReflexes();

                int diff = shooting - reflexes + random.nextInt(20); // variação
                if (diff > 10) {
                    // Golo!
                    String desc = attacker.getName() + " marcou aos " + minute + " minutos!";
                    GameEvent goal = new GameEvent(minute, desc, "GOAL", attacker, attackTeam);
                    m.addEvent(goal);
                }
            }
        }

        m.setPlayed();
    }
}

