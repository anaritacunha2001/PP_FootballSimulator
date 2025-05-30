/*
 * Nome: Ana Rita Dias Cunha
 * Número: 8210440
 * Turma: T1
 *
 * Nome: Carlos Barbosa
 * Número: 8210417
 * Turma: <Turma do colega de grupo>
 */

package main.match;

import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;
import com.ppstudios.footballmanager.api.contracts.event.IEvent;
import com.ppstudios.footballmanager.api.contracts.data.IExporter;

import java.io.Serializable;

/**
 * Representa um jogo de futebol entre dois clubes, contendo informações sobre as equipas,
 * eventos ocorridos e resultados.
 */
public class Match implements IMatch, IExporter, Serializable {
    private static final long serialVersionUID = 1L;

    private ITeam homeTeam, awayTeam;
    private final IClub homeClub, awayClub;
    private final IEvent[] events = new IEvent[200];
    private int eventCount = 0;
    private boolean played = false;
    private final int round;

    /**
     * Construtor de um jogo com clubes e ronda definidos.
     */
    public Match(IClub homeClub, IClub awayClub, int round) {
        if (homeClub == null || awayClub == null)
            throw new IllegalArgumentException("Clubes não podem ser null.");
        this.homeClub = homeClub;
        this.awayClub = awayClub;
        this.round = round;
    }

    // Getters básicos
    @Override public IClub getHomeClub()                  { return homeClub; }
    @Override public IClub getAwayClub()                  { return awayClub; }
    @Override public ITeam getHomeTeam()                  { return homeTeam; }
    @Override public ITeam getAwayTeam()                  { return awayTeam; }
    @Override public int getRound()                       { return round; }
    @Override public boolean isPlayed()                   { return played; }
    @Override public void setPlayed()                     { this.played = true; }

    /**
     * Associa uma equipa ao jogo, verificando se pertence ao clube certo.
     */
    @Override
    public void setTeam(ITeam team) {
        if (team == null) throw new IllegalArgumentException("Equipa não pode ser null.");
        if (homeTeam == null && team.getClub().equals(homeClub)) {
            homeTeam = team;
        } else if (awayTeam == null && team.getClub().equals(awayClub)) {
            awayTeam = team;
        } else {
            throw new IllegalStateException("Equipa não pertence a nenhum dos clubes do jogo ou já atribuída.");
        }
    }

    // Setters explícitos para evitar ambiguidades
    public void setHomeTeam(ITeam team) {
        if (team == null || !team.getClub().equals(homeClub))
            throw new IllegalStateException("Equipa não corresponde ao clube da casa.");
        this.homeTeam = team;
    }
    public void setAwayTeam(ITeam team) {
        if (team == null || !team.getClub().equals(awayClub))
            throw new IllegalStateException("Equipa não corresponde ao clube visitante.");
        this.awayTeam = team;
    }

    /**
     * Adiciona um evento ao jogo.
     */
    @Override
    public void addEvent(IEvent event) {
        if (event != null && eventCount < events.length)
            events[eventCount++] = event;
    }

    /**
     * Devolve os eventos ocorridos no jogo.
     */
    @Override
    public IEvent[] getEvents() {
        IEvent[] copy = new IEvent[eventCount];
        System.arraycopy(events, 0, copy, 0, eventCount);
        return copy;
    }

    @Override public int getEventCount()                 { return eventCount; }

    /**
     * Calcula o total de eventos do tipo GOAL para o clube indicado.
     */
    @Override
    public int getTotalByEvent(Class eventClass, IClub club) {
        int total = 0;
        for (int i = 0; i < eventCount; i++) {
            IEvent e = events[i];
            if (e instanceof GameEvent ge &&
                    ge.getTeam().getClub().equals(club) &&
                    "GOAL".equalsIgnoreCase(ge.getType())) {
                total++;
            }
        }
        return total;
    }

    /**
     * Determina a equipa vencedora com base nos golos marcados.
     */
    @Override
    public ITeam getWinner() {
        int hg = getTotalByEvent(null, homeClub);
        int ag = getTotalByEvent(null, awayClub);
        if (hg > ag) return homeTeam;
        if (ag > hg) return awayTeam;
        return null; // empate
    }

    /**
     * Verifica se ambas as equipas estão corretamente atribuídas.
     */
    @Override
    public boolean isValid() {
        return homeTeam != null && awayTeam != null
                && homeTeam.getClub().equals(homeClub)
                && awayTeam.getClub().equals(awayClub);
    }

    /**
     * Exporta os dados do jogo para formato JSON (simulado via consola).
     */
    @Override
    public void exportToJson() {
        System.out.println("{");
        System.out.println("  \"homeClub\": \"" + homeClub.getName() + "\",");
        System.out.println("  \"awayClub\": \"" + awayClub.getName() + "\",");
        System.out.println("  \"score\": \"" + getTotalByEvent(null, homeClub)
                + " - " + getTotalByEvent(null, awayClub) + "\",");
        System.out.println("  \"round\": " + round + ",");
        System.out.println("  \"events\": [");
        for (int i = 0; i < eventCount; i++) {
            IEvent e = events[i];
            System.out.print("    { \"minute\": " + e.getMinute()
                    + ", \"description\": \"" + e.getDescription() + "\" }");
            System.out.println(i < eventCount -1 ? "," : "");
        }
        System.out.println("  ]");
        System.out.println("}");
    }
}
