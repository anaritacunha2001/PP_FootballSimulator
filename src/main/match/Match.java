package main.match;

import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;
import com.ppstudios.footballmanager.api.contracts.event.IEvent;
import com.ppstudios.footballmanager.api.contracts.data.IExporter;

public class Match implements IMatch {

    private ITeam homeTeam;
    private ITeam awayTeam;

    private final IClub homeClub;
    private final IClub awayClub;

    private final IEvent[] events;
    private int eventCount;

    private boolean played;
    private int round;

    public Match(IClub homeClub, IClub awayClub, int round) {
        if (homeClub == null || awayClub == null) {
            throw new IllegalArgumentException("Clubes não podem ser null.");
        }
        this.homeClub = homeClub;
        this.awayClub = awayClub;
        this.round = round;
        this.events = new IEvent[200];
        this.eventCount = 0;
        this.played = false;
    }

    @Override
    public IClub getHomeClub() {
        return homeClub;
    }

    @Override
    public IClub getAwayClub() {
        return awayClub;
    }

    @Override
    public ITeam getHomeTeam() {
        return homeTeam;
    }

    @Override
    public ITeam getAwayTeam() {
        return awayTeam;
    }

    @Override
    public void setTeam(ITeam team) {
        if (team == null) throw new IllegalArgumentException("Equipa não pode ser null.");

        if (homeTeam == null && team.getClub().equals(homeClub)) {
            homeTeam = team;
        } else if (awayTeam == null && team.getClub().equals(awayClub)) {
            awayTeam = team;
        } else {
            throw new IllegalStateException("Equipa não pertence a nenhum dos clubes do jogo ou já atribuída.");
        }
    }

    @Override
    public boolean isPlayed() {
        return played;
    }

    @Override
    public void setPlayed() {
        this.played = true;
    }

    @Override
    public int getRound() {
        return round;
    }

    @Override
    public ITeam getWinner() {
        int homeGoals = getTotalByEvent(GameEvent.class, homeClub);
        int awayGoals = getTotalByEvent(GameEvent.class, awayClub);

        if (homeGoals > awayGoals) return homeTeam;
        if (awayGoals > homeGoals) return awayTeam;
        return null; // empate
    }

    @Override
    public int getTotalByEvent(Class eventClass, IClub club) {
        int total = 0;
        for (int i = 0; i < eventCount; i++) {
            IEvent e = events[i];
            if (e != null && e.getClass().equals(eventClass)) {
                if (e instanceof GameEvent) {
                    GameEvent ge = (GameEvent) e;
                    if (ge.getTeam().getClub().equals(club)) {
                        total++;
                    }
                }
            }
        }
        return total;
    }

    @Override
    public boolean isValid() {
        return homeTeam != null && awayTeam != null &&
                homeTeam.getClub().equals(homeClub) &&
                awayTeam.getClub().equals(awayClub);
    }

    @Override
    public void addEvent(IEvent event) {
        if (event == null || eventCount >= events.length) return;
        events[eventCount++] = event;
    }

    @Override
    public IEvent[] getEvents() {
        IEvent[] result = new IEvent[eventCount];
        System.arraycopy(events, 0, result, 0, eventCount);
        return result;
    }

    @Override
    public int getEventCount() {
        return 0;
    }

    @Override
    public void exportToJson() {
        System.out.println("{");
        System.out.println("  \"homeClub\": \"" + homeClub.getName() + "\",");
        System.out.println("  \"awayClub\": \"" + awayClub.getName() + "\",");
        System.out.println("  \"score\": \"" + getTotalByEvent(GameEvent.class, homeClub) + " - " +
                getTotalByEvent(GameEvent.class, awayClub) + "\",");
        System.out.println("  \"round\": " + round + ",");
        System.out.println("  \"events\": [");

        for (int i = 0; i < eventCount; i++) {
            IEvent e = events[i];
            System.out.print("    { \"minute\": " + e.getMinute() + ", \"description\": \"" + e.getDescription() + "\" }");
            if (i < eventCount - 1) System.out.println(",");
            else System.out.println();
        }

        System.out.println("  ]");
        System.out.println("}");
    }

}
