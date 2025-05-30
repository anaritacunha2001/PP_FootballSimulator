package main.match;

import com.ppstudios.footballmanager.api.contracts.event.IEvent;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;
import com.ppstudios.footballmanager.api.contracts.data.IExporter;

import java.io.Serializable;

public class GameEvent implements IEvent, IExporter, Serializable {
    private static final long serialVersionUID = 1L;

    private final int minute;
    private final String description;
    private final String type;
    private final IPlayer player;
    private final ITeam team;

    public GameEvent(int minute, String description, String type, IPlayer player, ITeam team) {
        if (player == null || team == null) {
            throw new IllegalArgumentException("Evento inválido: dados incompletos.");
        }
        this.minute = minute;
        this.description = description;
        this.type = type.toUpperCase();
        this.player = player;
        this.team = team;
    }

    @Override public int getMinute()                       { return minute; }
    @Override public String getDescription()               { return description; }
    /** IEvent doesn’t declare getType(), but your simulator needs it: */
    public String getType()                                { return type; }
    public IPlayer getPlayer()                             { return player; }
    public ITeam getTeam()                                 { return team; }

    @Override
    public void exportToJson() {
        System.out.println("{");
        System.out.println("  \"minute\": " + minute + ",");
        System.out.println("  \"description\": \"" + description + "\"");
        System.out.println("}");
    }

    @Override
    public String toString() {
        return "[" + minute + "'] " + description;
    }
}
