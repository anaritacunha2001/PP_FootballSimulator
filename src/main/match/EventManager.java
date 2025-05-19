package main.match;

import com.ppstudios.footballmanager.api.contracts.event.IEvent;
import com.ppstudios.footballmanager.api.contracts.event.IEventManager;

public class EventManager implements IEventManager {

    private final IEvent[] events;
    private int count;

    public EventManager() {
        this.events = new IEvent[200]; // limite arbitrário
        this.count = 0;
    }

    @Override
    public void addEvent(IEvent event) {
        if (event != null && count < events.length) {
            events[count++] = event;
        }
    }

    @Override
    public IEvent[] getEvents() {
        IEvent[] result = new IEvent[count];
        System.arraycopy(events, 0, result, 0, count);
        return result;
    }

    @Override
    public int getEventCount() {
        return count;
    }
}
