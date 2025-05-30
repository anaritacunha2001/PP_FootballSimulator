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
 * Interface que representa um evento de desarme (tackle) durante um jogo.
 * Define os métodos para aceder aos jogadores envolvidos e ao sucesso do evento.
 */
public interface ITackleEvent extends IEvent, IExporter {

    /**
     * Devolve o jogador que realizou o desarme.
     */
    IExtendedPlayer getDefender();

    /**
     * Devolve o jogador adversário sobre o qual foi feito o desarme.
     */
    IExtendedPlayer getOpponent();

    /**
     * Indica se o desarme foi bem-sucedido.
     */
    boolean wasSuccessful();
}
