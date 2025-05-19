package main.strategy;

import com.ppstudios.footballmanager.api.contracts.team.IClub;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.player.IPlayerPosition;
import com.ppstudios.footballmanager.api.contracts.team.IPlayerSelector;

public class PlayerSelector implements IPlayerSelector {

    @Override
    public IPlayer selectPlayer(IClub club, IPlayerPosition position) {
        if (club == null || position == null) {
            throw new IllegalArgumentException("Clube ou posição não podem ser null.");
        }

        for (IPlayer player : club.getPlayers()) {
            if (player != null && player.getPosition().getDescription().equalsIgnoreCase(position.getDescription())) {
                return player;
            }
        }

        return null;
    }
}
