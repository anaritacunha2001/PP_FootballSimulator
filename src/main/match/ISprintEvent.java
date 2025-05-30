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
 * Interface que representa um evento de sprint entre dois jogadores.
 * Permite identificar os intervenientes e o vencedor do sprint.
 */
public interface ISprintEvent extends IEvent, IExporter {

    /**
     * Devolve o primeiro jogador participante no sprint.
     */
    IExtendedPlayer getPlayer1();

    /**
     * Devolve o segundo jogador participante no sprint.
     */
    IExtendedPlayer getPlayer2();

    /**
     * Devolve o jogador que venceu o sprint.
     */
    IExtendedPlayer getWinner();
}
