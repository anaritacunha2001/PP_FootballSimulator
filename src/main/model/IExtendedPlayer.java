/*
 * Nome: Ana Rita Dias Cunha
 * Número: 8210440
 * Turma: T1
 *
 * Nome: Carlos Barbosa
 * Número: 8210417
 * Turma: <Turma do colega de grupo>
 */

package main.model;

import com.ppstudios.footballmanager.api.contracts.player.IPlayer;

/**
 * Interface que representa um jogador com atributos adicionais
 * como reflexos, capacidade de desarme e drible.
 */
public interface IExtendedPlayer extends IPlayer {

    /**
     * Devolve o valor de reflexos do jogador.
     */
    int getReflexes();

    /**
     * Devolve o valor de desarme do jogador.
     */
    int getTackling();

    /**
     * Devolve o valor de drible do jogador.
     */
    int getDribbling();
}
