/*
 * Nome: Ana Rita Dias Cunha
 * Número: 8210440
 * Turma: T1
 *
 * Nome: Carlos Barbosa
 * Número: 8210417
 * Turma: T3
 */

package main.match;

import com.ppstudios.footballmanager.api.contracts.event.IGoalEvent;
import main.model.IExtendedPlayer;

/**
 * Evento que representa um golo marcado por um jogador durante o jogo.
 */
public class GoalEvent implements IGoalEvent {
    private final int minute;
    private final IExtendedPlayer player;

    /**
     * Construtor do evento de golo.
     *
     * @param minute Minuto em que o golo foi marcado.
     * @param player Jogador que marcou o golo.
     */
    public GoalEvent(int minute, IExtendedPlayer player) {
        this.minute = minute;
        this.player = player;
    }

    /**
     * Devolve o minuto do golo.
     */
    @Override
    public int getMinute() {
        return minute;
    }

    /**
     * Devolve o jogador que marcou o golo.
     */
    @Override
    public IExtendedPlayer getPlayer() {
        return player;
    }

    /**
     * Descrição textual do evento de golo.
     */
    @Override
    public String getDescription() {
        return "[" + minute + "]' Golo de " + player.getName();
    }

    /**
     * Exporta o evento de golo em formato JSON (simulado via consola).
     */
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
