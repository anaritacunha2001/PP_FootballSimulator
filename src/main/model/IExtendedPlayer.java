package main.model;

import com.ppstudios.footballmanager.api.contracts.player.IPlayer;

public interface IExtendedPlayer extends IPlayer {
    int getReflexes();
    int getTackling();
    int getDribbling();
}
