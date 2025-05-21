package main.match;

import com.ppstudios.footballmanager.api.contracts.event.IEvent;
import com.ppstudios.footballmanager.api.contracts.data.IExporter;
import main.model.IExtendedPlayer;

public interface IDribbleEvent extends IEvent, IExporter {
    IExtendedPlayer getAttacker();
    IExtendedPlayer getDefender();
    boolean wasSuccessful();
}
