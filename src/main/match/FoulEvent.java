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

    /**
     * Construtor do evento de falta.
     *
     * @param minute        Minuto da falta
     * @param foulingPlayer Jogador que cometeu a falta
     * @param fouledPlayer  Jogador que sofreu a falta
     * @param team          Equipa à qual pertence o jogador que cometeu a falta
     * @param card          Tipo de cartão atribuído ("NONE", "YELLOW", "RED")
     */
    public FoulEvent(int minute, IPlayer foulingPlayer, IPlayer fouledPlayer, ITeam team, String card) {
        this.minute = minute;
        this.foulingPlayer = foulingPlayer;
        this.fouledPlayer = fouledPlayer;
        this.team = team;
        this.card = card;
    }

    /**
     * Devolve o minuto da falta.
     */
    @Override
    public int getMinute() {
        return minute;
    }

    /**
     * Devolve uma descrição textual do evento.
     */
    @Override
    public String getDescription() {
        return "Falta de " + foulingPlayer.getName() + " sobre " + fouledPlayer.getName() +
                ("NONE".equals(card) ? "" : " [Cartão: " + card + "]");
    }

    /**
     * Devolve o jogador que cometeu a falta.
     */
    public IPlayer getFoulingPlayer() {
        return foulingPlayer;
    }

    /**
     * Devolve o jogador que sofreu a falta.
     */
    public IPlayer getFouledPlayer() {
        return fouledPlayer;
    }

    /**
     * Devolve a equipa do jogador que cometeu a falta.
     */
    public ITeam getTeam() {
        return team;
    }

    /**
     * Devolve o tipo de cartão atribuído no evento.
     */
    public String getCard() {
        return card;
    }

    /**
     * Exporta os dados do evento em formato JSON (simulado via consola).
     */
    @Override
    public void exportToJson() {
        System.out.println("{\"minute\": " + minute +
                ", \"type\": \"FOUL\"" +
                ", \"foulingPlayer\": \"" + foulingPlayer.getName() + "\"" +
                ", \"fouledPlayer\": \"" + fouledPlayer.getName() + "\"" +
                ", \"card\": \"" + card + "\"}");
    }

    /**
     * Representação textual alternativa do evento.
     */
    @Override
    public String toString() {
        return "[" + minute + "] Falta de " + foulingPlayer.getName() +
                " sobre " + fouledPlayer.getName() +
                ("NONE".equals(card) ? "" : " [Cartão: " + card + "]");
    }
}
