package main.manager;

import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.player.IPlayerPosition;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import com.ppstudios.footballmanager.api.contracts.team.IFormation;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;
import main.model.Club;

import java.io.IOException;

public class Team extends Club implements ITeam{


    private IFormation formation;
    private int teamStrength;

    public Team(String name, String country, String code, int foundedYear, String stadiumName, String logo, IPlayer[] iPlayers, int numberOfPlayers, int teamStrength) {
        super(name, country, code, foundedYear, stadiumName, logo, iPlayers, numberOfPlayers);
    }


    //nao sei como vou dar o getClub
    @Override
    public IClub getClub() {
        return null;
    }

    @Override
    public IFormation getFormation() {
        return this.formation;
    }

    @Override
    public IPlayer[] getPlayers() {
        return this.iPlayers;
    }


    @Override
    public void addPlayer(IPlayer iPlayer) {

    }

    //NAO SEI COMO FAZER
    @Override
    public int getPositionCount(IPlayerPosition iPlayerPosition) {
        return 0;
    }

    //NAO SEI COMO FAZER
    @Override
    public boolean isValidPositionForFormation(IPlayerPosition iPlayerPosition) {
        return false;
    }

    //NAO SEI COMO FAZER
    @Override
    public int getTeamStrength() {
        return 0;
    }

    @Override
    public void setFormation(IFormation iFormation) {
        this.formation = iFormation;
    }

    @Override
    public void exportToJson() throws IOException {

    }
}
