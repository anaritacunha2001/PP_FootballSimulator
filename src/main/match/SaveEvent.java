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
 * Representa um evento de defesa durante um jogo de futebol.
 * Contém informação sobre o minuto, guarda-redes, atacante e sucesso da defesa.
 */
public class SaveEvent implements ISaveEvent {

    private final int minute;
    private final IExtendedPlayer goalkeeper;
    private final IExtendedPlayer attacker;
    private final boolean success;

    /**
     * Construtor do evento de defesa.
     * @param minute Minuto em que ocorreu o evento
     * @param goalkeeper Guarda-redes que tentou a defesa
     * @param attacker Jogador que rematou
     * @param success Verdadeiro se a defesa teve sucesso
     */
    public SaveEvent(int minute, IExtendedPlayer goalkeeper, IExtendedPlayer attacker, boolean success) {
        this.minute = minute;
        this.goalkeeper = goalkeeper;
        this.attacker = attacker;
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
     * Devolve o guarda-redes envolvido.
     */
    @Override
    public IExtendedPlayer getGoalkeeper() {
        return goalkeeper;
    }

    /**
     * Devolve o jogador atacante.
     */
    @Override
    public IExtendedPlayer getAttacker() {
        return attacker;
    }

    /**
     * Indica se a defesa foi bem-sucedida.
     */
    @Override
    public boolean wasSuccessful() {
        return success;
    }

    /**
     * Descrição textual do evento.
     */
    @Override
    public String getDescription() {
        return String.format("[%d'] %s rematou e %s %s a defesa.",
                minute,
                attacker.getName(),
                goalkeeper.getName(),
                success ? "fez" : "falhou"
        );
    }

    /**
     * Exporta o evento em formato JSON para a consola.
     */
    @Override
    public void exportToJson() {
        String json = String.format(
                "{\"type\": \"save\", \"minute\": %d, \"goalkeeper\": \"%s\", \"attacker\": \"%s\", \"success\": %b}",
                minute,
                goalkeeper.getName(),
                attacker.getName(),
                success
        );
        System.out.println(json);
    }
}
