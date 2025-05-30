/*
 * Nome: Ana Rita Dias Cunha
 * Número: 8210440
 * Turma: T1
 *
 * Nome: Carlos Barbosa
 * Número: 8210417
 * Turma: T3
 */

package main.manager;

import com.ppstudios.footballmanager.api.contracts.team.IFormation;

import java.io.Serializable;

/**
 * Representa uma formação tática de uma equipa de futebol.
 * Permite calcular a vantagem tática contra outra formação.
 */
public class Formation implements IFormation, Serializable {
    private static final long serialVersionUID = 1L;

    private String displayName;

    /**
     * Construtor da classe Formation.
     * Valida se a formação fornecida é suportada.
     *
     * @param displayName Nome da formação (ex: "4-4-2").
     */
    public Formation(String displayName) {
        if (!displayName.equals("4-3-3") &&
                !displayName.equals("4-4-2") &&
                !displayName.equals("3-5-2")) {
            throw new IllegalArgumentException("Formação inválida: " + displayName);
        }
        this.displayName = displayName;
    }

    /**
     * Calcula a vantagem tática desta formação em relação à formação adversária.
     *
     * @param opponent Formação adversária.
     * @return 1 se tem vantagem, -1 se tem desvantagem, 0 se for neutra.
     */
    @Override
    public int getTacticalAdvantage(IFormation opponent) {
        if (opponent == null) {
            throw new IllegalArgumentException("Formação adversária não pode ser null.");
        }

        String other = opponent.getDisplayName();

        if (this.displayName.equals("4-3-3") && other.equals("4-4-2")) {
            return 1;
        } else if (this.displayName.equals("4-4-2") && other.equals("4-3-3")) {
            return -1;
        } else if (this.displayName.equals("3-5-2") && other.equals("4-4-2")) {
            return 1;
        } else if (this.displayName.equals("4-4-2") && other.equals("3-5-2")) {
            return -1;
        } else {
            return 0;
        }
    }

    /**
     * Devolve o nome da formação.
     */
    @Override
    public String getDisplayName() {
        return displayName;
    }
}
