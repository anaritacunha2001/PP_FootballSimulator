package main.model;

import com.ppstudios.footballmanager.api.contracts.player.IPlayerPosition;

public class PlayerPosition implements IPlayerPosition {
    private String description;

    public static final PlayerPosition GOALKEEPER = new PlayerPosition("GOALKEEPER");
    public static final PlayerPosition DEFENDER = new PlayerPosition("DEFENDER");
    public static final PlayerPosition MIDFIELDER = new PlayerPosition("MIDFIELDER");
    public static final PlayerPosition STRIKER = new PlayerPosition("STRIKER");

    public PlayerPosition(String description) {
        this.description = description != null ? description : "Unknown";
    }

    @Override
    public String getDescription() {
        return description;
    }

    public String getName() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerPosition that = (PlayerPosition) o;
        return description.equalsIgnoreCase(that.description);
    }

    @Override
    public int hashCode() {
        return description.toLowerCase().hashCode();
    }

    @Override
    public String toString() {
        return getDescription();
    }
}
