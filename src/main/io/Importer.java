package io;

import main.model.Club;
import main.model.Player;
import main.model.PlayerPosition;
import com.ppstudios.footballmanager.api.contracts.player.PreferredFoot;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Importer {

    /**
     * Importa todos os clubes de resources/clubs.json
     */
    public Club[] importarClubes(String ficheiroClubs) {
        String json = lerFicheiro(ficheiroClubs);
        if (json.isBlank()) return new Club[0];

        // Remove os colchetes externos e divide em blocos de objetos
        String conteudo = json.replaceAll("^\\s*\\[|\\]\\s*$", "");
        String[] blocos = conteudo.split("\\},\\s*\\{");

        List<Club> lista = new ArrayList<>();
        for (String raw : blocos) {
            String bloco = raw;
            if (!bloco.startsWith("{")) bloco = "{" + bloco;
            if (!bloco.endsWith("}")) bloco = bloco + "}";

            String name    = extrairValorString(bloco, "name");
            String code    = extrairValorString(bloco, "code");
            String country = extrairValorString(bloco, "country");
            int founded    = extrairValorInt(bloco, "founded");
            String stadium = extrairValorString(bloco, "stadium");
            String logo    = extrairValorString(bloco, "logo");

            lista.add(new Club(name, code, country, founded, stadium, logo, new IPlayer[0]));
        }

        return lista.toArray(new Club[0]);
    }

    /**
     * Importa todos os jogadores de resources/players/<ficheiro>
     */
    public Player[] importarJogadores(String ficheiroPlayers) {
        String json = lerFicheiro(ficheiroPlayers);
        if (json.isBlank()) return new Player[0];

        // Remove colchetes externos e divide em objetos individuais
        String conteudo = json.replaceAll("^\\s*\\[|\\]\\s*$", "");
        String[] entradas = conteudo.split("\\},\\s*\\{");

        List<Player> lista = new ArrayList<>();
        for (String raw : entradas) {
            String bloco = raw;
            if (!bloco.startsWith("{")) bloco = "{" + bloco;
            if (!bloco.endsWith("}")) bloco = bloco + "}";

            String nome        = extrairValorString(bloco, "name");
            String posicaoStr  = extrairValorString(bloco, "position").toUpperCase();
            int number         = extrairValorInt(bloco, "number");
            String nacionalidade = extrairValorString(bloco, "nationality");
            String photo       = extrairValorString(bloco, "photo");

            // Campos de atributos técnicos (passing, shooting, etc)
            int passing  = extrairValorInt(bloco, "passing");
            int shooting = extrairValorInt(bloco, "shooting");
            int speed    = extrairValorInt(bloco, "speed");
            int stamina  = extrairValorInt(bloco, "stamina");
            int height   = extrairValorInt(bloco, "height");
            int weight   = extrairValorInt(bloco, "weight");
            int reflexes = extrairValorInt(bloco, "reflexes");
            int tackling = extrairValorInt(bloco, "tackling");
            int dribbling= extrairValorInt(bloco, "dribbling");

            // Não há birthDate no JSON; usa data actual
            LocalDate birthDate = LocalDate.now();

            PlayerPosition pos = new PlayerPosition(posicaoStr);
            PreferredFoot foot = PreferredFoot.Right;

            lista.add(new Player(
                    nome,
                    birthDate,
                    nacionalidade,
                    number,
                    50,           // overall placeholder
                    photo,
                    pos,
                    foot,
                    passing,
                    shooting,
                    speed,
                    (float) height / 100f,
                    (float) weight,
                    stamina,
                    reflexes,
                    tackling
            ));
        }

        return lista.toArray(new Player[0]);
    }

    // ---------------- métodos auxiliares ----------------

    private String lerFicheiro(String nomeFicheiro) {
        try (InputStream in = getClass()
                .getClassLoader()
                .getResourceAsStream(nomeFicheiro))
        {
            if (in == null) {
                System.err.println("Erro: ficheiro '" + nomeFicheiro + "' não encontrado.");
                return "";
            }
            BufferedReader r = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String linha;
            while ((linha = r.readLine()) != null) {
                sb.append(linha.trim());
            }
            return sb.toString();
        } catch (Exception e) {
            System.err.println("Erro ao ler ficheiro '" + nomeFicheiro + "': " + e.getMessage());
            return "";
        }
    }

    private String extrairValorString(String json, String campo) {
        Pattern p = Pattern.compile("\"" + campo + "\"\\s*:\\s*\"([^\"]*)\"");
        Matcher m = p.matcher(json);
        if (m.find()) {
            return m.group(1);
        }
        return "";
    }

    private int extrairValorInt(String json, String campo) {
        Pattern p = Pattern.compile("\"" + campo + "\"\\s*:\\s*(\\d+)");
        Matcher m = p.matcher(json);
        if (m.find()) {
            return Integer.parseInt(m.group(1));
        }
        return 0;
    }
}
