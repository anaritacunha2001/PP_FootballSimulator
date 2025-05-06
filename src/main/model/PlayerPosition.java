package main.model;

import com.ppstudios.footballmanager.api.contracts.player.IPlayerPosition;

public class PlayerPosition implements IPlayerPosition {

    private String description;

    public PlayerPosition(String description) {
        this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }

    // Este método é extra (não está na interface)
    public String getName() {
        return description;
    }
}
