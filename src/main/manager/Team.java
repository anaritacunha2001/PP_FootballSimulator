package main.manager;

import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.player.IPlayerPosition;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import com.ppstudios.footballmanager.api.contracts.team.IFormation;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;
import main.model.IExtendedPlayer;

import java.io.IOException;

public class Team implements ITeam {

    private IClub club; // Referência ao clube base
    private IFormation formation; // Formação selecionada
    private IPlayer[] starters; // Jogadores titulares
    private int starterCount;

    public Team(IClub club, IFormation formation) {
        this.club = club;
        this.formation = formation;
        this.starters = new IPlayer[11];
        this.starterCount = 0;
    }

    @Override
    public IClub getClub() {
        return club;
    }

    @Override
    public IFormation getFormation() {
        return formation;
    }

    @Override
    public void setFormation(IFormation formation) {
        this.formation = formation;
    }

    @Override
    public IPlayer[] getPlayers() {
        return starters;
    }

    @Override
    public void addPlayer(IPlayer player) {
        if (starterCount < starters.length) {
            starters[starterCount++] = player;
        }
    }

    @Override
    public int getPositionCount(IPlayerPosition position) {
        int count = 0;
        for (int i = 0; i < starterCount; i++) {
            if (starters[i] != null && starters[i].getPosition().getDescription().equals(position.getDescription())) {
                count++;
            }
        }
        return count;
    }

    @Override
    public boolean isValidPositionForFormation(IPlayerPosition position) {
        // Simplificação: considera que qualquer posição é válida
        return true;
    }

    @Override
    public int getTeamStrength() {
        int total = 0;
        for (int i = 0; i < starterCount; i++) {
            if (starters[i] != null) {
                total += starters[i].getPassing();
                total += starters[i].getShooting();
                total += starters[i].getSpeed();
                total += starters[i].getStamina();

                IExtendedPlayer p = (IExtendedPlayer) starters[i];
                total += p.getDribbling();
                total += p.getTackling();
                total += p.getReflexes();
            }
        }
        return total / starterCount;
    }

    @Override
    public void exportToJson() throws IOException {
        System.out.println("{\"club\": \"" + club.getName() + "\", \"formation\": \"" + formation.getDisplayName() + "\"}");
    }
}

