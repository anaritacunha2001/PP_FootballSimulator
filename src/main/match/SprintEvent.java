package main.match;

import main.model.IExtendedPlayer;

public class SprintEvent implements ISprintEvent {
    private final int minute;
    private final IExtendedPlayer player1;
    private final IExtendedPlayer player2;
    private final IExtendedPlayer winner;

    public SprintEvent(int minute, IExtendedPlayer player1, IExtendedPlayer player2, IExtendedPlayer winner) {
        this.minute = minute;
        this.player1 = player1;
        this.player2 = player2;
        this.winner = winner;
    }

    @Override
    public int getMinute() {
        return minute;
    }

    @Override
    public IExtendedPlayer getPlayer1() {
        return player1;
    }

    @Override
    public IExtendedPlayer getPlayer2() {
        return player2;
    }

    @Override
    public IExtendedPlayer getWinner() {
        return winner;
    }

    @Override
    public String getDescription() {
        return String.format("[%d'] Corrida entre %s e %s. %s ficou com a bola.",
                minute,
                player1.getName(),
                player2.getName(),
                winner.getName()
        );
    }

    @Override
    public void exportToJson() {
        String json = String.format(
                "{\"type\": \"sprint\", \"minute\": %d, \"player1\": \"%s\", \"player2\": \"%s\", \"winner\": \"%s\"}",
                minute,
                player1.getName(),
                player2.getName(),
                winner.getName()
        );
        System.out.println(json);
    }
}
