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

import com.ppstudios.footballmanager.api.contracts.event.IEvent;
import com.ppstudios.footballmanager.api.contracts.event.IEventManager;

/**
 * Gere uma lista de eventos ocorridos durante o jogo.
 * Permite adicionar eventos e consultar os eventos registados.
 */
public class EventManager implements IEventManager {

    private final IEvent[] events;
    private int count;

    /**
     * Construtor do gestor de eventos.
     * Inicializa um array com capacidade para 200 eventos.
     */
    public EventManager() {
        this.events = new IEvent[200];
        this.count = 0;
    }

    /**
     * Adiciona um evento ao array de eventos.
     *
     * @param event Evento a ser adicionado.
     */
    @Override
    public void addEvent(IEvent event) {
        if (event != null && count < events.length) {
            events[count++] = event;
        }
    }

    /**
     * Devolve todos os eventos registados até ao momento.
     *
     * @return Array de eventos.
     */
    @Override
    public IEvent[] getEvents() {
        IEvent[] result = new IEvent[count];
        System.arraycopy(events, 0, result, 0, count);
        return result;
    }

    /**
     * Devolve o número total de eventos registados.
     */
    @Override
    public int getEventCount() {
        return count;
    }
}
