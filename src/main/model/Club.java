package main.model;

import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.player.IPlayerPosition;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import com.ppstudios.footballmanager.api.contracts.team.IPlayerSelector;

import java.io.IOException;

public class Club implements IClub {

    private final int CLUB_DEFAULT_SIZE = 30;

    protected String name;
    protected String country;
    protected String code;
    protected int foundedYear;
    protected String stadiumName;
    protected String logo;
    protected IPlayer[] iPlayers;
    protected int numberOfPlayers;


    // NESTE ASSUMIMOS QUE O CLUBE É CRIADO ANTES DA IMPORTAÇAO DOS JOGADORES
    public Club(String name, String country, String code, int foundedYear, String stadiumName, String logo) {
        this.name = name;
        this.country = country;
        this.code = code;
        this.foundedYear = foundedYear;
        this.stadiumName = stadiumName;
        this.logo = logo;
        this.iPlayers = new IPlayer[CLUB_DEFAULT_SIZE];
        this.numberOfPlayers = 0;
    }

    // NESTE JA RECEBEMOS OS JOGADORES IMPORTADOS E SO CRIAMOS O CLUBE DEPOIS
    public Club(String name, String country, String code, int foundedYear, String stadiumName, String logo, IPlayer[] iPlayers, int numberOfPlayers) {
        this.name = name;
        this.country = country;
        this.code = code;
        this.foundedYear = foundedYear;
        this.stadiumName = stadiumName;
        this.logo = logo;
        this.iPlayers = iPlayers;
        this.numberOfPlayers = numberOfPlayers;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public IPlayer[] getPlayers() {
        return this.iPlayers;
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
    public void addPlayer(IPlayer iPlayer) {
        if (iPlayers.length == this.numberOfPlayers) {
            expandCapacity();
        }
        this.iPlayers[this.numberOfPlayers] = iPlayer;
        this.numberOfPlayers++;
    }

    @Override
    public boolean isPlayer(IPlayer iPlayer) {
        for (int i = 0; i < iPlayers.length; i++) {
            if (iPlayers[i] == iPlayer) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void removePlayer(IPlayer iPlayer) {
        for (int i = 0; i < iPlayers.length; i++) {
            if (iPlayers[i] == iPlayer) {
                for (int j = i; j<iPlayers.length; j++) {
                    iPlayers[j] = iPlayers[j + 1];
                }
                iPlayers[iPlayers.length - 1] = null;
                this.numberOfPlayers--;
            }
        }
    }

    @Override
    public int getPlayerCount() {
        return this.numberOfPlayers;
    }

    @Override
    public IPlayer selectPlayer(IPlayerSelector iPlayerSelector, IPlayerPosition iPlayerPosition) {
        return null;
    }

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public void exportToJson() throws IOException {
    }

    private void expandCapacity(){
        IPlayer[] newArray = new IPlayer[iPlayers.length*2];
        System.arraycopy(iPlayers, 0, newArray, 0, iPlayers.length);
        iPlayers = newArray;
    }
}
