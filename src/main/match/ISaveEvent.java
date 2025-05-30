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
import com.ppstudios.footballmanager.api.contracts.data.IExporter;
import main.model.IExtendedPlayer;

/**
 * Interface que representa um evento de defesa durante um jogo.
 * Pode representar tanto uma defesa bem-sucedida como falhada.
 */
public interface ISaveEvent extends IEvent, IExporter {

    /**
     * Devolve o guarda-redes envolvido no evento.
     */
    IExtendedPlayer getGoalkeeper();

    /**
     * Devolve o jogador atacante que rematou.
     */
    IExtendedPlayer getAttacker();

    /**
     * Indica se a defesa foi bem-sucedida.
     */
    boolean wasSuccessful();
}