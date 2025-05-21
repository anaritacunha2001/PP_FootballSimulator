package main.match;

import main.model.IExtendedPlayer;

public class TackleEvent implements ITackleEvent {
    private final int minute;
    private final IExtendedPlayer defender;
    private final IExtendedPlayer opponent;
    private final boolean success;

    public TackleEvent(int minute, IExtendedPlayer defender, IExtendedPlayer opponent, boolean success) {
        this.minute = minute;
        this.defender = defender;
        this.opponent = opponent;
        this.success = success;
    }

    @Override
    public int getMinute() {
        return minute;
    }

    @Override
    public IExtendedPlayer getDefender() {
        return defender;
    }

    @Override
    public IExtendedPlayer getOpponent() {
        return opponent;
    }

    @Override
    public boolean wasSuccessful() {
        return success;
    }

    @Override
    public String getDescription() {
        return String.format("[%d'] %s tentou desarmar %s e %s.",
                minute,
                defender.getName(),
                opponent.getName(),
                success ? "teve sucesso" : "falhou"
        );
    }

    @Override
    public void exportToJson() {
        String json = String.format(
                "{\"type\": \"tackle\", \"minute\": %d, \"defender\": \"%s\", \"opponent\": \"%s\", \"success\": %b}",
                minute,
                defender.getName(),
                opponent.getName(),
                success
        );
        System.out.println(json);
    }
}
