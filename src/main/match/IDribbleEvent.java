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
 * Interface que representa um evento de drible durante o jogo.
 * Estende IEvent e IExporter para suporte a exportação e integração com o motor de eventos.
 */
public interface IDribbleEvent extends IEvent, IExporter {

    /**
     * Devolve o jogador atacante que tentou o drible.
     */
    IExtendedPlayer getAttacker();

    /**
     * Devolve o jogador defensor envolvido no drible.
     */
    IExtendedPlayer getDefender();

    /**
     * Indica se o drible foi bem-sucedido.
     */
    boolean wasSuccessful();
}
