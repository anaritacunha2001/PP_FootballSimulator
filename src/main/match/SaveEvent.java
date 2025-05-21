package main.match;

import main.model.IExtendedPlayer;

public class SaveEvent implements ISaveEvent {
    private final int minute;
    private final IExtendedPlayer goalkeeper;
    private final IExtendedPlayer attacker;
    private final boolean success;

    public SaveEvent(int minute, IExtendedPlayer goalkeeper, IExtendedPlayer attacker, boolean success) {
        this.minute = minute;
        this.goalkeeper = goalkeeper;
        this.attacker = attacker;
        this.success = success;
    }

    @Override
    public int getMinute() {
        return minute;
    }

    @Override
    public IExtendedPlayer getGoalkeeper() {
        return goalkeeper;
    }

    @Override
    public IExtendedPlayer getAttacker() {
        return attacker;
    }

    @Override
    public boolean wasSuccessful() {
        return success;
    }

    @Override
    public String getDescription() {
        return String.format("[%d'] %s rematou e %s %s a defesa.",
                minute,
                attacker.getName(),
                goalkeeper.getName(),
                success ? "fez" : "falhou"
        );
    }

    @Override
    public void exportToJson() {
        String json = String.format(
                "{\"type\": \"save\", \"minute\": %d, \"goalkeeper\": \"%s\", \"attacker\": \"%s\", \"success\": %b}",
                minute,
                goalkeeper.getName(),
                attacker.getName(),
                success
        );
        System.out.println(json);
    }
}
