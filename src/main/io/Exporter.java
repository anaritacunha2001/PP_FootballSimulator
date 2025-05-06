package io;

import model.Player;

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
                writer.write("    \"position\": \"" + (p.getPosition() != null ? p.getPosition().getDescription() : "") + "\",\n");
                writer.write("    \"preferredFoot\": \"" + p.getPreferredFoot() + "\"\n");
                writer.write("  }" + (i < jogadores.length - 1 ? "," : "") + "\n");
            }
            writer.write("]");

            writer.close();
            System.out.println("✔ Plantel exportado para: " + caminho);

        } catch (IOException e) {
            System.out.println("❌ Erro ao exportar o plantel.");
            e.printStackTrace();
        }
    }
}
