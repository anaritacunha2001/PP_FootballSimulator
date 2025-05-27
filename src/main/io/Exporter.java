package io;

import main.model.Player;
import com.ppstudios.footballmanager.api.contracts.player.IPlayerPosition;
import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.event.IEvent;
import main.match.GameEvent;
import main.league.Season;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Exporter {

    public void exportarPlantel(Player[] jogadores, String nomeEquipa) {
        try {
            String caminho = "export_" + nomeEquipa + ".json";
            BufferedWriter writer = new BufferedWriter(new FileWriter(caminho));

            writer.write("[\n");
            for (int i = 0; i < jogadores.length; i++) {
                Player p = jogadores[i];
                writer.write("  {\n");
                writer.write("    \"name\": \"" + p.getName() + "\",\n");
                writer.write("    \"birthDate\": \"" + p.getBirthDate() + "\",\n");
                writer.write("    \"nationality\": \"" + p.getNationality() + "\",\n");
                writer.write("    \"number\": " + p.getNumber() + ",\n");
                writer.write("    \"photo\": \"" + p.getPhoto() + "\",\n");

                IPlayerPosition position = p.getPosition();
                String positionDescription = (position != null) ? position.getDescription() : "Not Defined";
                writer.write("    \"position\": \"" + positionDescription + "\",\n");

                writer.write("    \"preferredFoot\": \"" + p.getPreferredFoot() + "\"\n");
                writer.write("  }" + (i < jogadores.length - 1 ? "," : "") + "\n");
            }
            writer.write("]");

            writer.close();
            System.out.println("✔ Plantel exportado para: " + caminho);

        } catch (IOException e) {
            System.out.println("Erro ao exportar o plantel.");
            e.printStackTrace();
        }
    }

    public void exportarEpoca(Season season, String caminho) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminho))) {
            writer.write("[\n");
            IMatch[] jogos = season.getMatches();

            for (int i = 0; i < jogos.length; i++) {
                IMatch jogo = jogos[i];
                if (jogo == null) continue;

                writer.write("  {\n");
                writer.write("    \"homeClub\": \"" + jogo.getHomeClub().getName() + "\",\n");
                writer.write("    \"awayClub\": \"" + jogo.getAwayClub().getName() + "\",\n");
                writer.write("    \"score\": \"" +
                        jogo.getTotalByEvent(GameEvent.class, jogo.getHomeClub()) + " - " +
                        jogo.getTotalByEvent(GameEvent.class, jogo.getAwayClub()) + "\",\n");

                writer.write("    \"events\": [\n");
                IEvent[] eventos = jogo.getEvents();
                for (int j = 0; j < eventos.length; j++) {
                    IEvent e = eventos[j];
                    writer.write("      {\n");
                    writer.write("        \"minute\": " + e.getMinute() + ",\n");
                    writer.write("        \"description\": \"" + e.getDescription() + "\"\n");
                    writer.write("      }" + (j < eventos.length - 1 ? "," : "") + "\n");
                }
                writer.write("    ]\n");
                writer.write("  }" + (i < jogos.length - 1 ? "," : "") + "\n");
            }

            writer.write("]");
            System.out.println("Época exportada para: " + caminho);

        } catch (IOException e) {
            System.out.println("Erro ao exportar época.");
            e.printStackTrace();
        }
    }
}
