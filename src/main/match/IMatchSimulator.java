/*
 * Nome: Ana Rita Dias Cunha
 * Número: 8210440
 * Turma: T1
 *
 * Nome: Carlos Barbosa
 * Número: 8210417
 * Turma: T3
 */

package com.ppstudios.footballmanager.api.contracts.match;

import com.ppstudios.footballmanager.api.contracts.match.IMatch;

/**
 * Interface que define um simulador de partidas de futebol (IMatch).
 * Deve gerar eventos (ex: golos, faltas) através do método IMatch.addEvent(...)
 */
public interface IMatchSimulator {

    /**
     * Simula o jogo especificado, adicionando eventos ao objeto IMatch.
     *
     * @param match Jogo a ser simulado.
     */
    void simulateMatch(IMatch match);
}