package main.match;

import main.model.IExtendedPlayer;

public class DribbleEvent implements IDribbleEvent {
    private final int minute;
    private final IExtendedPlayer attacker;
    private final IExtendedPlayer defender;
    private final boolean success;

    public DribbleEvent(int minute, IExtendedPlayer attacker, IExtendedPlayer defender, boolean success) {
        this.minute = minute;
        this.attacker = attacker;
        this.defender = defender;
        this.success = success;
    }

    @Override
    public int getMinute() {
        return minute;
    }

    @Override
    public IExtendedPlayer getAttacker() {
        return attacker;
    }

    @Override
    public IExtendedPlayer getDefender() {
        return defender;
    }

    @Override
    public boolean wasSuccessful() {
        return success;
    }

    @Override
    public String getDescription() {
        return String.format("[%d'] %s tentou driblar %s e %s.",
                minute,
                attacker.getName(),
                defender.getName(),
                success ? "conseguiu" : "falhou"
        );
    }

    @Override
    public void exportToJson() {
        String json = String.format(
                "{\"type\": \"dribble\", \"minute\": %d, \"attacker\": \"%s\", \"defender\": \"%s\", \"success\": %b}",
                minute,
                attacker.getName(),
                defender.getName(),
                success
        );
        System.out.println(json);
    }
}
