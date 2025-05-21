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

        for (int minute = 1; minute <= 90; minute++) {
            if (random.nextDouble() < 0.2) {
                boolean homeAttacks = random.nextBoolean();
                ITeam attackTeam = homeAttacks ? home : away;
                ITeam defendTeam = homeAttacks ? away : home;

                IExtendedPlayer attacker = (IExtendedPlayer) selector.selectPlayer(attackTeam.getClub(), PlayerPosition.STRIKER);
                IExtendedPlayer defender = (IExtendedPlayer) selector.selectPlayer(defendTeam.getClub(), PlayerPosition.DEFENDER);
                IExtendedPlayer goalkeeper = (IExtendedPlayer) selector.selectPlayer(defendTeam.getClub(), PlayerPosition.GOALKEEPER);

                if (attacker == null || defender == null || goalkeeper == null) continue;

                // Sprint
                IExtendedPlayer sprintWinner = (attacker.getSpeed() > defender.getSpeed()) ? attacker : defender;
                SprintEvent sprint = new SprintEvent(minute, attacker, defender, sprintWinner);
                m.addEvent(sprint);
                if (!sprintWinner.equals(attacker)) continue;

                // Tackle
                boolean tackleSuccess = defender.getTackling() + random.nextInt(10) > attacker.getDribbling();
                TackleEvent tackle = new TackleEvent(minute, defender, attacker, tackleSuccess);
                m.addEvent(tackle);
                if (tackleSuccess) continue;

                // Dribble
                boolean dribbleSuccess = attacker.getDribbling() + random.nextInt(10) > defender.getTackling();
                DribbleEvent dribble = new DribbleEvent(minute, attacker, defender, dribbleSuccess);
                m.addEvent(dribble);
                if (!dribbleSuccess) continue;

                // Save
                boolean saveSuccess = goalkeeper.getReflexes() + random.nextInt(10) > attacker.getShooting();
                SaveEvent save = new SaveEvent(minute, goalkeeper, attacker, saveSuccess);
                m.addEvent(save);
                if (!saveSuccess) {
                    GoalEvent goal = new GoalEvent(minute, attacker);
                    m.addEvent(goal);
                }
            }
        }

        m.setPlayed();

        System.out.println("Golos Casa: " + m.getTotalByEvent(GoalEvent.class, m.getHomeClub()));
        System.out.println("Golos Fora: " + m.getTotalByEvent(GoalEvent.class, m.getAwayClub()));
    }
}
