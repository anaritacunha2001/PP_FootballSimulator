package main.match;

import com.ppstudios.footballmanager.api.contracts.event.IEvent;
import com.ppstudios.footballmanager.api.contracts.data.IExporter;
import main.model.IExtendedPlayer;

public interface ISaveEvent extends IEvent, IExporter {
    IExtendedPlayer getGoalkeeper();
    IExtendedPlayer getAttacker();
    boolean wasSuccessful();
}
