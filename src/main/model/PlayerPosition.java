/*
 * Nome: Ana Rita Dias Cunha
 * Número: 8210440
 * Turma: T1
 *
 * Nome: Carlos Barbosa
 * Número: 8210417
 * Turma: T3
 */

package main.model;

import com.ppstudios.footballmanager.api.contracts.player.IPlayerPosition;

/**
 * Representa uma posição de jogador no campo, como guarda-redes, defesa, médio ou avançado.
 * Implementa a interface IPlayerPosition.
 */
public class PlayerPosition implements IPlayerPosition {
    private String description;

    // Constantes de posições predefinidas
    public static final PlayerPosition GOALKEEPER = new PlayerPosition("GOALKEEPER");
    public static final PlayerPosition DEFENDER = new PlayerPosition("DEFENDER");
    public static final PlayerPosition MIDFIELDER = new PlayerPosition("MIDFIELDER");
    public static final PlayerPosition STRIKER = new PlayerPosition("STRIKER");

    /**
     * Construtor da posição, recebe a descrição textual da posição.
     * Se a descrição for null, é atribuído "Unknown".
     */
    public PlayerPosition(String description) {
        this.description = description != null ? description : "Unknown";
    }

    /**
     * Devolve a descrição da posição (ex: "GOALKEEPER").
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Método auxiliar para obter o nome da posição (igual à descrição).
     */
    public String getName() {
        return description;
    }

    /**
     * Compara esta posição com outra, ignorando diferenças entre maiúsculas e minúsculas.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerPosition that = (PlayerPosition) o;
        return description.equalsIgnoreCase(that.description);
    }

    /**
     * Gera um hashCode com base na descrição, ignorando maiúsculas/minúsculas.
     */
    @Override
    public int hashCode() {
        return description.toLowerCase().hashCode();
    }

    /**
     * Retorna a descrição da posição em formato String.
     */
    @Override
    public String toString() {
        return getDescription();
    }
}
