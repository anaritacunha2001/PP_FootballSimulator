/*
 * Nome: Ana Rita Dias Cunha
 * Número: 8210440
 * Turma: T1
 *
 * Nome: Carlos Barbosa
 * Número: 8210417
 * Turma: <Turma do colega de grupo>
 */

package main.match;

import main.model.IExtendedPlayer;

/**
 * Representa um evento de corrida (sprint) entre dois jogadores durante o jogo.
 * Contém os jogadores envolvidos, o vencedor e o minuto em que ocorreu.
 */
public class SprintEvent implements ISprintEvent {

    private final int minute;
    private final IExtendedPlayer player1;
    private final IExtendedPlayer player2;
    private final IExtendedPlayer winner;

    /**
     * Construtor do evento de sprint.
     * @param minute Minuto do jogo em que ocorreu
     * @param player1 Primeiro jogador
     * @param player2 Segundo jogador
     * @param winner Jogador que venceu o sprint
     */
    public SprintEvent(int minute, IExtendedPlayer player1, IExtendedPlayer player2, IExtendedPlayer winner) {
        this.minute = minute;
        this.player1 = player1;
        this.player2 = player2;
        this.winner = winner;
    }

    /**
     * Devolve o minuto em que ocorreu o sprint.
     */
    @Override
    public int getMinute() {
        return minute;
    }

    /**
     * Devolve o primeiro jogador envolvido.
     */
    @Override
    public IExtendedPlayer getPlayer1() {
        return player1;
    }

    /**
     * Devolve o segundo jogador envolvido.
     */
    @Override
    public IExtendedPlayer getPlayer2() {
        return player2;
    }

    /**
     * Devolve o jogador vencedor do sprint.
     */
    @Override
    public IExtendedPlayer getWinner() {
        return winner;
    }

    /**
     * Devolve uma descrição textual do evento de sprint.
     */
    @Override
    public String getDescription() {
        return String.format("[%d'] Corrida entre %s e %s. %s ficou com a bola.",
                minute,
                player1.getName(),
                player2.getName(),
                winner.getName()
        );
    }

    /**
     * Exporta o evento em formato JSON para a consola.
     */
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
