/*
 * Nome: Ana Rita Dias Cunha
 * Número: 8210440
 * Turma: T1
 *
 * Nome: Carlos Barbosa
 * Número: 8210417
 * Turma: T3
 */

package main.match;

import main.model.IExtendedPlayer;

/**
 * Representa um evento de desarme (tackle) entre dois jogadores.
 * Contém o minuto, o defensor, o adversário e se o desarme teve sucesso.
 */
public class TackleEvent implements ITackleEvent {

    private final int minute;
    private final IExtendedPlayer defender;
    private final IExtendedPlayer opponent;
    private final boolean success;

    /**
     * Construtor do evento de desarme.
     * @param minute Minuto em que ocorreu o desarme
     * @param defender Jogador que realizou o desarme
     * @param opponent Jogador adversário sobre o qual foi feito o desarme
     * @param success Indica se o desarme foi bem-sucedido
     */
    public TackleEvent(int minute, IExtendedPlayer defender, IExtendedPlayer opponent, boolean success) {
        this.minute = minute;
        this.defender = defender;
        this.opponent = opponent;
        this.success = success;
    }

    /**
     * Devolve o minuto do evento.
     */
    @Override
    public int getMinute() {
        return minute;
    }

    /**
     * Devolve o jogador que realizou o desarme.
     */
    @Override
    public IExtendedPlayer getDefender() {
        return defender;
    }

    /**
     * Devolve o jogador adversário.
     */
    @Override
    public IExtendedPlayer getOpponent() {
        return opponent;
    }

    /**
     * Indica se o desarme teve sucesso.
     */
    @Override
    public boolean wasSuccessful() {
        return success;
    }

    /**
     * Devolve uma descrição textual do evento de desarme.
     */
    @Override
    public String getDescription() {
        return String.format("[%d'] %s tentou desarmar %s e %s.",
                minute,
                defender.getName(),
                opponent.getName(),
                success ? "teve sucesso" : "falhou"
        );
    }

    /**
     * Exporta o evento em formato JSON para a consola.
     */
    @Override
    public void exportToJson() {
        String json = String.format(
                "{\"type\": \"tackle\", \"minute\": %d, \"defender\": \"%s\", \"opponent\": \"%s\", \"success\": %b}",
                minute,
                defender.getName(),
                opponent.getName(),
                success
        );
        System.out.println(json);
    }
}
