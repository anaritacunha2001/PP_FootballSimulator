package main.model;

import com.ppstudios.footballmanager.api.contracts.player.IPlayerPosition;

public class PlayerPosition implements IPlayerPosition {
    private String description;

    public static final PlayerPosition GOALKEEPER = new PlayerPosition("GOALKEEPER");
    public static final PlayerPosition CENTER_BACK = new PlayerPosition("CENTER BACK");
    public static final PlayerPosition LEFT_BACK = new PlayerPosition("LEFT BACK");
    public static final PlayerPosition RIGHT_BACK = new PlayerPosition("RIGHT BACK");
    public static final PlayerPosition CENTRAL_MIDFIELDER = new PlayerPosition("CENTRAL MIDFIELDER");
    public static final PlayerPosition LEFT_MIDFIELDER = new PlayerPosition("LEFT MIDFIELDER");
    public static final PlayerPosition RIGHT_MIDFIELDER = new PlayerPosition("RIGHT MIDFIELDER");
    public static final PlayerPosition STRIKER = new PlayerPosition("STRIKER");
    public static final PlayerPosition LEFT_WINGER = new PlayerPosition("LEFT WINGER");
    public static final PlayerPosition RIGHT_WINGER = new PlayerPosition("RIGHT WINGER");

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
