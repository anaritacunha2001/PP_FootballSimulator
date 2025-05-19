package main.simulation;

import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.simulation.MatchSimulatorStrategy;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;
import main.match.Match;
import main.match.GameEvent;
import main.model.IExtendedPlayer;
import main.model.PlayerPosition;
import main.strategy.PlayerSelector;

import java.util.Random;

public class MatchSimulatorStrategyImpl implements MatchSimulatorStrategy {

    private final Random random = new Random();

    @Override
    public void simulate(IMatch match) {
        if (!(match instanceof Match)) return;

        Match m = (Match) match;
        ITeam home = m.getHomeTeam();
        ITeam away = m.getAwayTeam();
        PlayerSelector selector = new PlayerSelector();

        for (int minute = 1; minute <= 90; minute++) {
            if (random.nextDouble() < 0.2) {
                boolean homeAttacks = random.nextBoolean();
                ITeam attackTeam = homeAttacks ? home : away;
                ITeam defendTeam = homeAttacks ? away : home;

                IPlayer attacker = selector.selectPlayer(attackTeam.getClub(), PlayerPosition.STRIKER);
                IPlayer goalkeeper = selector.selectPlayer(defendTeam.getClub(), PlayerPosition.GOALKEEPER);

                if (!(attacker instanceof IExtendedPlayer) || !(goalkeeper instanceof IExtendedPlayer)) continue;

                int shooting = ((IExtendedPlayer) attacker).getShooting();
                int reflexes = ((IExtendedPlayer) goalkeeper).getReflexes();

                int diff = shooting - reflexes + random.nextInt(20); // variação

                if (diff > 10) {
                    String desc = attacker.getName() + " marcou aos " + minute + " minutos!";
                    GameEvent goal = new GameEvent(minute, desc, "GOAL", attacker, attackTeam);
                    m.addEvent(goal);
                }
            }
        }

        m.setPlayed();
    }
}
