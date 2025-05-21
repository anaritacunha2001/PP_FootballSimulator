package main.match;

import com.ppstudios.footballmanager.api.contracts.event.IEvent;
import com.ppstudios.footballmanager.api.contracts.data.IExporter;
import main.model.IExtendedPlayer;

public interface ITackleEvent extends IEvent, IExporter {
    IExtendedPlayer getDefender();
    IExtendedPlayer getOpponent();
    boolean wasSuccessful();
}
