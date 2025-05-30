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
 * Representa um evento de drible ocorrido durante um jogo.
 * Guarda o minuto, atacante, defensor e se o drible teve sucesso.
 */
public class DribbleEvent implements IDribbleEvent {
    private final int minute;
    private final IExtendedPlayer attacker;
    private final IExtendedPlayer defender;
    private final boolean success;

    /**
     * Construtor do evento de drible.
     *
     * @param minute   Minuto do jogo em que o evento ocorreu
     * @param attacker Jogador que tentou o drible
     * @param defender Jogador que tentou defender
     * @param success  Indica se o drible teve sucesso
     */
    public DribbleEvent(int minute, IExtendedPlayer attacker, IExtendedPlayer defender, boolean success) {
        this.minute = minute;
        this.attacker = attacker;
        this.defender = defender;
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
     * Devolve o jogador atacante.
     */
    @Override
    public IExtendedPlayer getAttacker() {
        return attacker;
    }

    /**
     * Devolve o jogador defensor.
     */
    @Override
    public IExtendedPlayer getDefender() {
        return defender;
    }

    /**
     * Indica se o drible foi bem-sucedido.
     */
    @Override
    public boolean wasSuccessful() {
        return success;
    }

    /**
     * Gera uma descrição textual do evento.
     */
    @Override
    public String getDescription() {
        return String.format("[%d'] %s tentou driblar %s e %s.",
                minute,
                attacker.getName(),
                defender.getName(),
                success ? "conseguiu" : "falhou"
        );
    }

    /**
     * Exporta o evento em formato JSON (simulado via consola).
     */
    @Override
    public void exportToJson() {
        String json = String.format(
                "{\"type\": \"dribble\", \"minute\": %d, \"attacker\": \"%s\", \"defender\": \"%s\", \"success\": %b}",
                minute,
                attacker.getName(),
                defender.getName(),
                success
        );
        System.out.println(json);
    }
}
