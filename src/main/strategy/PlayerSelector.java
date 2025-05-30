/*
 * Nome: Ana Rita Dias Cunha
 * Número: 8210440
 * Turma: T1
 *
 * Nome: Carlos Barbosa
 * Número: 8210417
 * Turma: <Turma do colega de grupo>
 */

package main.strategy;

import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.player.IPlayerPosition;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import com.ppstudios.footballmanager.api.contracts.team.IPlayerSelector;

/**
 * Classe que implementa a seleção de um jogador de um clube com base na posição.
 * Prioriza jogadores que correspondam à descrição da posição fornecida.
 */
public class PlayerSelector implements IPlayerSelector {

    /**
     * Seleciona um jogador do clube para a posição indicada.
     * Se não encontrar jogador para a posição, retorna o primeiro jogador não-nulo.
     *
     * @param club Clube ao qual pertencem os jogadores
     * @param position Posição desejada
     * @return Jogador correspondente ou primeiro jogador válido
     */
    @Override
    public IPlayer selectPlayer(IClub club, IPlayerPosition position) {
        if (club == null || position == null) return null;

        IPlayer[] players = club.getPlayers();

        // Primeira tentativa: procurar jogador com a posição exata
        for (IPlayer p : players) {
            if (p != null && p.getPosition() != null) {
                String desc = p.getPosition().getDescription();
                String wanted = position.getDescription();

                // Linha de debug opcional
                System.out.println("DEBUG: " + p.getName() + " posição=" + desc + " procurada=" + wanted);

                if (desc.equalsIgnoreCase(wanted)) {
                    return p;
                }
            }
        }

        // Segunda tentativa: devolver o primeiro jogador válido
        for (IPlayer p : players) {
            if (p != null) return p;
        }

        return null;
    }
}
