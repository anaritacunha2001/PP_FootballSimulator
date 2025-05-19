package io;

import main.model.Player;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.player.IPlayerPosition;

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

                // Corrigir o acesso à posição e garantir que o método 'getPosition()' seja tratado corretamente
                IPlayerPosition position = p.getPosition();
                String positionDescription = (position != null) ? position.getDescription() : "Not Defined";
                writer.write("    \"position\": \"" + positionDescription + "\",\n");

                // Corrigir o acesso ao 'PreferredFoot'
                writer.write("    \"preferredFoot\": \"" + p.getPreferredFoot() + "\"\n");

                writer.write("  }" + (i < jogadores.length - 1 ? "," : "") + "\n");
            }
            writer.write("]");

            writer.close();
            System.out.println("✔ Plantel exportado para: " + caminho);

        } catch (IOException e) {
            System.out.println(" Erro ao exportar o plantel.");
            e.printStackTrace();
        }
    }
}
