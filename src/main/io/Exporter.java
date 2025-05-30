/*
 * Nome: Ana Cunha
 * Número: 8210440
 * Turma: T1
 *
 * Nome: Carlos Barbosa
 * Número: 8210417
 * Turma: <Turma do colega de grupo>
 */

// src/main/io/Exporter.java
package io;

import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import main.league.Season;
import main.match.GameEvent;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe responsável pela exportação incremental dos resultados das rondas de um campeonato
 * para um ficheiro JSON (output/epocas.json).
 */
public class Exporter {

    // Caminho fixo para o ficheiro de saída JSON.
    private static final Path OUTPUT = Paths.get("output", "epocas.json");

    /**
     * Método que inicializa o ficheiro JSON antes de começar as simulações.
     * Se não existir, cria o ficheiro e coloca o '[' inicial.
     */
    public void startExport() throws IOException {
        if (Files.notExists(OUTPUT)) {
            Files.createDirectories(OUTPUT.getParent());
            Files.writeString(OUTPUT, "[\n", StandardCharsets.UTF_8, StandardOpenOption.CREATE);
        } else {
            // Caso exista mas esteja vazio, escreve o '[' inicial.
            if (Files.size(OUTPUT) == 0) {
                Files.writeString(OUTPUT, "[\n", StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING);
            }
        }
    }

    /**
     * Método que adiciona ao ficheiro JSON os resultados de uma ronda específica.
     *
     * @param season Objeto Season contendo informações da época.
     * @param roundNumber Número da ronda a exportar (baseado em índice 1).
     */
    public void appendRound(Season season, int roundNumber) throws IOException {
        // Lê todas as linhas existentes no ficheiro.
        List<String> linhas = Files.readAllLines(OUTPUT, StandardCharsets.UTF_8);

        // Remove o último ']', para permitir adicionar nova ronda.
        int last = linhas.size() - 1;
        while (last >= 0 && linhas.get(last).strip().equals("]")) {
            last--;
        }
        linhas = linhas.subList(0, last + 1);

        // Adiciona uma vírgula caso já existam rondas exportadas anteriormente.
        if (linhas.stream().anyMatch(l -> l.strip().equals("},"))) {
            linhas.add(",");
        }

        // Gera o conteúdo JSON da ronda especificada e adiciona ao ficheiro.
        String jsonRound = roundToJson(season, roundNumber);
        linhas.addAll(
                jsonRound.lines().collect(Collectors.toList())
        );

        // Fecha o array JSON com ']'.
        linhas.add("]");

        // Reescreve o ficheiro com as alterações.
        Files.write(OUTPUT, linhas, StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING);

        // Mensagem de confirmação no terminal.
        System.out.println("✔ Ronda " + roundNumber +
                " de \"" + season.getName() + "\" guardada em " + OUTPUT);
    }

    /**
     * Gera uma string JSON representando os resultados de uma ronda.
     *
     * @param season Época atual.
     * @param roundNumber Número da ronda.
     * @return String formatada em JSON.
     */
    private String roundToJson(Season season, int roundNumber) {
        StringBuilder sb = new StringBuilder();
        sb.append("  {\n");
        sb.append("    \"name\": \"").append(escape(season.getName())).append("\",\n");
        sb.append("    \"roundsPlayed\": ").append(roundNumber).append(",\n");
        sb.append("    \"results\": [\n");

        IMatch[] jogos = season.getMatches(roundNumber - 1);
        for (int i = 0; i < jogos.length; i++) {
            IMatch m = jogos[i];
            int gH = m.getTotalByEvent(GameEvent.class, m.getHomeClub());
            int gA = m.getTotalByEvent(GameEvent.class, m.getAwayClub());

            sb.append("      {")
                    .append("\"round\":").append(roundNumber).append(",")
                    .append("\"home\":\"").append(escape(m.getHomeClub().getName())).append("\",")
                    .append("\"away\":\"").append(escape(m.getAwayClub().getName())).append("\",")
                    .append("\"score\":\"").append(gH).append("-").append(gA).append("\"")
                    .append("}");

            // Adiciona vírgula exceto após o último jogo.
            if (i < jogos.length - 1) sb.append(",");
            sb.append("\n");
        }

        sb.append("    ]\n");
        sb.append("  }");
        return sb.toString();
    }

    /**
     * Método auxiliar para escapar caracteres especiais numa string JSON.
     *
     * @param s String original.
     * @return String escapada.
     */
    private String escape(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
