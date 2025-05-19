package main.model;

import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.player.IPlayerPosition;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import com.ppstudios.footballmanager.api.contracts.team.IPlayerSelector;

public class Club implements IClub {

    private String name;
    private String code;
    private String country;
    private int foundedYear;
    private String stadiumName;
    private String logo;
    private IPlayer[] players;
    private int playerCount;

    public Club(String name, String code, String country, int foundedYear, String stadiumName, String logo, IPlayer[] players) {
        this.name = name;
        this.code = code;
        this.country = country;
        this.foundedYear = foundedYear;
        this.stadiumName = stadiumName;
        this.logo = logo;
        this.players = players;
        this.playerCount = players != null ? players.length : 0;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public IPlayer[] getPlayers() {
        return this.players;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getCountry() {
        return this.country;
    }

    @Override
    public int getFoundedYear() {
        return this.foundedYear;
    }

    @Override
    public String getStadiumName() {
        return this.stadiumName;
    }

    @Override
    public String getLogo() {
        return this.logo;
    }

    @Override
    public void addPlayer(IPlayer player) {
        if (player == null) {
            throw new IllegalArgumentException("Jogador não pode ser null.");
        }
        if (playerCount >= players.length) {
            throw new IllegalStateException("O clube está cheio.");
        }
        if (isPlayer(player)) {
            throw new IllegalArgumentException("Jogador já existe no clube.");
        }
        players[playerCount++] = player;
    }


    @Override
    public boolean isPlayer(IPlayer player) {
        if (player == null) {
            throw new IllegalArgumentException("Jogador não pode ser null.");
        }

        for (int i = 0; i < playerCount; i++) {
            if (players[i] != null && players[i].equals(player)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void removePlayer(IPlayer player) {
        if (player == null) {
            throw new IllegalArgumentException("Jogador não pode ser null.");
        }

        if (!isPlayer(player)) {
            throw new IllegalArgumentException("Jogador não pertence ao clube.");
        }

        for (int i = 0; i < playerCount; i++) {
            if (players[i] != null && players[i].equals(player)) {
                for (int j = i; j < playerCount - 1; j++) {
                    players[j] = players[j + 1];
                }
                players[playerCount - 1] = null;
                playerCount--;
                return;
            }
        }
    }


    @Override
    public int getPlayerCount() {
        return this.playerCount;
    }

    @Override
    public IPlayer selectPlayer(IPlayerSelector selector, IPlayerPosition position) {
        if (selector == null || position == null) {
            throw new IllegalArgumentException("Selector e posição não podem ser null.");
        }

        return selector.selectPlayer(this, position);
    }

    @Override
    public boolean isValid() {
        if (players == null || players.length == 0) {
            throw new IllegalStateException("O clube está vazio.");
        }

        if (playerCount == 0) {
            throw new IllegalStateException("O clube não tem jogadores.");
        }

        if (playerCount < 16) {
            throw new IllegalStateException("O clube deve ter pelo menos 16 jogadores.");
        }

        int gkCount = 0;
        for (int i = 0; i < playerCount; i++) {
            if (players[i] != null && players[i].getPosition().getDescription().equalsIgnoreCase("GOALKEEPER")) {
                gkCount++;
            }
        }

        if (gkCount == 0) {
            throw new IllegalStateException("O clube não tem guarda-redes.");
        }

        return true;
    }



    @Override
    public void exportToJson() {
        System.out.println("{\\\"name\\\":\\\"" + name + "\\\",\\\"code\\\":\\\"" + code + "\\\"}");
    }
}
