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
import com.ppstudios.footballmanager.api.contracts.data.IExporter;

import java.io.Serializable;

/**
 * Representa um evento genérico de jogo como golo, remate, etc.
 * Implementa IEvent e IExporter para permitir exportação e integração com o simulador.
 */
public class GameEvent implements IEvent, IExporter, Serializable {
    private static final long serialVersionUID = 1L;

    private final int minute;
    private final String description;
    private final String type;
    private final IPlayer player;
    private final ITeam team;

    /**
     * Construtor do evento de jogo.
     *
     * @param minute      Minuto do jogo em que ocorreu o evento
     * @param description Descrição do evento
     * @param type        Tipo do evento (ex: GOAL, FOUL)
     * @param player      Jogador envolvido
     * @param team        Equipa do jogador
     */
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

    /** Devolve o minuto do evento. */
    @Override public int getMinute()                       { return minute; }

    /** Devolve a descrição textual do evento. */
    @Override public String getDescription()               { return description; }

    /** Devolve o tipo do evento (ex: GOAL, FOUL). */
    public String getType()                                { return type; }

    /** Devolve o jogador envolvido no evento. */
    public IPlayer getPlayer()                             { return player; }

    /** Devolve a equipa do jogador. */
    public ITeam getTeam()                                 { return team; }

    /**
     * Exporta os dados do evento em formato JSON (simulado via consola).
     */
    @Override
    public void exportToJson() {
        System.out.println("{");
        System.out.println("  \"minute\": " + minute + ",");
        System.out.println("  \"description\": \"" + description + "\"");
        System.out.println("}");
    }

    /**
     * Representa o evento como string com formato "[minuto] descrição".
     */
    @Override
    public String toString() {
        return "[" + minute + "]' " + description;
    }
}
