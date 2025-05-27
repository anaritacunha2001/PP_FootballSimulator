package main.match;

import com.ppstudios.footballmanager.api.contracts.event.IEvent;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;

/**
 * Evento de falta cometida durante o jogo.
 */
public class FoulEvent implements IEvent {

    private final int minute;
    private final IPlayer foulingPlayer;
    private final IPlayer fouledPlayer;
    private final ITeam team;
    private final String card; // "NONE", "YELLOW", "RED"

    public FoulEvent(int minute, IPlayer foulingPlayer, IPlayer fouledPlayer, ITeam team, String card) {
        this.minute = minute;
        this.foulingPlayer = foulingPlayer;
        this.fouledPlayer = fouledPlayer;
        this.team = team;
        this.card = card;
    }

    @Override
    public int getMinute() {
        return minute;
    }

    @Override
    public String getDescription() {
        return "Falta de " + foulingPlayer.getName() + " sobre " + fouledPlayer.getName() +
                ("NONE".equals(card) ? "" : " [Cartão: " + card + "]");
    }

    public IPlayer getFoulingPlayer() {
        return foulingPlayer;
    }

    public IPlayer getFouledPlayer() {
        return fouledPlayer;
    }

    public ITeam getTeam() {
        return team;
    }

    public String getCard() {
        return card;
    }

    @Override
    public void exportToJson() {
        System.out.println("{\"minute\": " + minute +
                ", \"type\": \"FOUL\"" +
                ", \"foulingPlayer\": \"" + foulingPlayer.getName() + "\"" +
                ", \"fouledPlayer\": \"" + fouledPlayer.getName() + "\"" +
                ", \"card\": \"" + card + "\"}");
    }

    @Override
    public String toString() {
        return "[" + minute + "] Falta de " + foulingPlayer.getName() +
                " sobre " + fouledPlayer.getName() +
                ("NONE".equals(card) ? "" : " [Cartão: " + card + "]");
    }
}
