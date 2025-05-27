package main.strategy;

import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.player.IPlayerPosition;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import com.ppstudios.footballmanager.api.contracts.team.IPlayerSelector;

public class PlayerSelector implements IPlayerSelector {
    @Override
    public IPlayer selectPlayer(IClub club, IPlayerPosition position) {
        if (club == null || position == null) return null;

        IPlayer[] players = club.getPlayers();
        // Tenta primeiro encontrar exatamente pela descrição
        for (IPlayer p : players) {
            if (p != null && p.getPosition() != null) {
                String desc = p.getPosition().getDescription();
                String wanted = position.getDescription();
                // DEBUG
                System.out.println("DEBUG: " + p.getName() + " posição=" + desc + " procurada=" + wanted);
                if (desc.equalsIgnoreCase(wanted)) {
                    return p;
                }
            }
        }
        // Se não encontrar, devolve o primeiro jogador não-null
        for (IPlayer p : players) {
            if (p != null) return p;
        }
        return null;
    }
}
