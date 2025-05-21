package main.match;

import com.ppstudios.footballmanager.api.contracts.event.IEvent;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;

import java.io.IOException;

public class GameEvent implements IEvent {

    private final int minute;
    private final String description;

    private final String type;
    private final IPlayer player;
    private final ITeam team;

    public GameEvent(int minute, String description, String type, IPlayer player, ITeam team) {
        if (description == null || player == null || team == null) {
            throw new IllegalArgumentException("Evento inválido: dados incompletos.");
        }
        this.minute = minute;
        this.description = description;
        this.type = type != null ? type.toUpperCase() : "UNKNOWN";
        this.player = player;
        this.team = team;
    }

    @Override
    public int getMinute() {
        return minute;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public IPlayer getPlayer() {
        return player;
    }

    public ITeam getTeam() {
        return team;
    }

    @Override
    public String toString() {
        return "[" + minute + "'] " + description;
    }

    @Override
    public void exportToJson() {
        System.out.println("{");
        System.out.println("  \"minute\": " + getMinute() + ",");
        System.out.println("  \"description\": \"" + getDescription() + "\"");
        System.out.println("}");
    }

}

