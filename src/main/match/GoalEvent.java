package main.match;

import com.ppstudios.footballmanager.api.contracts.event.IGoalEvent;
import main.model.IExtendedPlayer;

public class GoalEvent implements IGoalEvent {
    private final int minute;
    private final IExtendedPlayer player;

    public GoalEvent(int minute, IExtendedPlayer player) {
        this.minute = minute;
        this.player = player;
    }

    @Override
    public int getMinute() {
        return minute;
    }

    @Override
    public IExtendedPlayer getPlayer() {
        return player;
    }

    @Override
    public String getDescription() {
        return "[" + minute + "'] Golo de " + player.getName();
    }

    @Override
    public void exportToJson() {
        String json = String.format(
                "{\"type\": \"goal\", \"minute\": %d, \"player\": \"%s\"}",
                minute,
                player.getName()
        );
        System.out.println(json);
    }
}
