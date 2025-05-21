package main.match;

import com.ppstudios.footballmanager.api.contracts.event.IEvent;
import com.ppstudios.footballmanager.api.contracts.data.IExporter;
import main.model.IExtendedPlayer;

public interface ISprintEvent extends IEvent, IExporter {
    IExtendedPlayer getPlayer1();
    IExtendedPlayer getPlayer2();
    IExtendedPlayer getWinner();
}
