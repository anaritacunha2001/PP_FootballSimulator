package main.simulation;

import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;
import com.ppstudios.footballmanager.api.contracts.simulation.MatchSimulatorStrategy;
import main.match.DribbleEvent;
import main.match.GoalEvent;
import main.match.Match;
import main.match.SaveEvent;
import main.match.SprintEvent;
import main.match.TackleEvent;
import main.model.IExtendedPlayer;
import main.model.PlayerPosition;
import main.strategy.PlayerSelector;

import java.util.Random;

public class MatchSimulator implements MatchSimulatorStrategy {
    private final Random random = new Random();

    @Override
    public void simulate(IMatch match) {
        if (!(match instanceof Match)) return;

        Match m = (Match) match;
        ITeam home = m.getHomeTeam();
        ITeam away = m.getAwayTeam();
        PlayerSelector selector = new PlayerSelector();

        int golos = 0, eventos = 0;
        for (int minute = 1; minute <= 90; minute++) {
            if (random.nextDouble() < 0.9) { // Garantir muitos eventos
                boolean homeAttacks = random.nextBoolean();
                ITeam attackTeam = homeAttacks ? home : away;
                ITeam defendTeam = homeAttacks ? away : home;

                IExtendedPlayer attacker = (IExtendedPlayer) selector.selectPlayer(attackTeam.getClub(), PlayerPosition.STRIKER);
                IExtendedPlayer defender = (IExtendedPlayer) selector.selectPlayer(defendTeam.getClub(), PlayerPosition.DEFENDER);
                IExtendedPlayer goalkeeper = (IExtendedPlayer) selector.selectPlayer(defendTeam.getClub(), PlayerPosition.GOALKEEPER);

                if (attacker == null || defender == null || goalkeeper == null) continue;

                SprintEvent sprint = new SprintEvent(minute, attacker, defender, attacker);
                m.addEvent(sprint);
                eventos++;

                TackleEvent tackle = new TackleEvent(minute, defender, attacker, false);
                m.addEvent(tackle);
                eventos++;

                DribbleEvent dribble = new DribbleEvent(minute, attacker, defender, true);
                m.addEvent(dribble);
                eventos++;

                SaveEvent save = new SaveEvent(minute, goalkeeper, attacker, false);
                m.addEvent(save);
                eventos++;

                // Golo garantido!
                GoalEvent goal = new GoalEvent(minute, attacker);
                m.addEvent(goal);
                golos++;
            }
        }
        m.setPlayed();
        System.out.println("DEBUG: Total de eventos neste jogo: " + eventos + " | Golos: " + golos);
    }
}
