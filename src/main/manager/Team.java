/*
 * Nome: Ana Rita Dias Cunha
 * Número: 8210440
 * Turma: T1
 *
 * Nome: Carlos Barbosa
 * Número: 8210417
 * Turma: <Turma do colega de grupo>
 */

package main.manager;

import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.player.IPlayerPosition;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import com.ppstudios.footballmanager.api.contracts.team.IFormation;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;
import main.model.IExtendedPlayer;

import java.io.IOException;
import java.io.Serializable;

/**
 * Representa uma equipa com 11 jogadores titulares,
 * associada a um clube e uma formação tática.
 */
public class Team implements ITeam, Serializable {
    private static final long serialVersionUID = 1L;

    private static final int TEAM_SIZE = 11;

    private IClub club;
    private IFormation formation;
    private IPlayer[] starters;
    private int starterCount;

    /**
     * Construtor da classe Team.
     *
     * @param club      Clube a que a equipa pertence.
     * @param formation Formação tática inicial da equipa.
     */
    public Team(IClub club, IFormation formation) {
        this.club = club;
        this.formation = formation;
        this.starters = new IPlayer[TEAM_SIZE];
        this.starterCount = 0;
    }

    @Override
    public IClub getClub() {
        return club;
    }

    @Override
    public IFormation getFormation() {
        return formation;
    }

    @Override
    public void setFormation(IFormation formation) {
        if (formation == null) {
            throw new IllegalArgumentException("Formação não pode ser null.");
        }
        this.formation = formation;
    }

    @Override
    public IPlayer[] getPlayers() {
        return starters;
    }

    /**
     * Adiciona um jogador à equipa titular.
     *
     * @param player Jogador a adicionar.
     */
    @Override
    public void addPlayer(IPlayer player) {
        if (player == null) {
            throw new IllegalArgumentException("Jogador não pode ser null.");
        }
        if (starterCount >= TEAM_SIZE) {
            throw new IllegalStateException("Equipa já tem 11 titulares.");
        }
        if (formation == null) {
            throw new IllegalStateException("A equipa não tem formação definida.");
        }
        if (!club.isPlayer(player)) {
            throw new IllegalStateException("Jogador não pertence ao clube.");
        }
        for (int i = 0; i < starterCount; i++) {
            if (starters[i] != null && starters[i].equals(player)) {
                throw new IllegalArgumentException("Jogador já está na equipa.");
            }
        }
        starters[starterCount++] = player;
    }

    /**
     * Conta quantos jogadores existem para uma dada posição.
     *
     * @param position Posição a verificar.
     */
    @Override
    public int getPositionCount(IPlayerPosition position) {
        if (position == null) {
            throw new IllegalArgumentException("Posição não pode ser null.");
        }

        int count = 0;
        for (int i = 0; i < starterCount; i++) {
            if (starters[i] != null &&
                    starters[i].getPosition().getDescription().equals(position.getDescription())) {
                count++;
            }
        }
        return count;
    }

    /**
     * Verifica se a posição é válida para a formação atual.
     *
     * @param position Posição a validar.
     * @return true se for válida (sempre true neste caso).
     */
    @Override
    public boolean isValidPositionForFormation(IPlayerPosition position) {
        return true; // lógica simplificada
    }

    /**
     * Calcula a força total da equipa com base nos atributos dos jogadores.
     *
     * @return valor médio ponderado dos atributos.
     */
    @Override
    public int getTeamStrength() {
        int total = 0;
        for (int i = 0; i < starterCount; i++) {
            if (starters[i] != null) {
                total += starters[i].getPassing();
                total += starters[i].getShooting();
                total += starters[i].getSpeed();
                total += starters[i].getStamina();

                IExtendedPlayer p = (IExtendedPlayer) starters[i];
                total += p.getDribbling();
                total += p.getTackling();
                total += p.getReflexes();
            }
        }
        return total / (starterCount * 7);
    }

    /**
     * Exporta os dados da equipa (simulação via consola).
     */
    @Override
    public void exportToJson() throws IOException {
        System.out.println("{\"club\": \"" + club.getName() +
                "\", \"formation\": \"" + formation.getDisplayName() + "\"}");
    }
}
