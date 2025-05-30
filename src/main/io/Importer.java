/*
 * Nome: Ana Cunha
 * Número: 8210440
 * Turma: T1
 *
 * Nome: Carlos Barbosa
 * Número: 8210417
 * Turma: T3
 */

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

/**
 * Classe responsável por importar dados de clubes e jogadores a partir de ficheiros JSON.
 */
public class Importer {

    /**
     * Importa clubes de um ficheiro JSON especificado.
     *
     * @param ficheiroClubs nome do ficheiro JSON com dados dos clubes.
     * @return array de objetos Club importados.
     */
    public Club[] importarClubes(String ficheiroClubs) {
        String json = lerFicheiro(ficheiroClubs);
        if (json.isBlank()) return new Club[0];

        String conteudo = json.replaceAll("^\\s*\\[|\\]\\s*$", "");
        String[] blocos = conteudo.split("\\},\\s*\\{");

        List<Club> lista = new ArrayList<>();
        for (String raw : blocos) {
            String bloco = raw.startsWith("{") ? raw : "{" + raw;
            bloco = bloco.endsWith("}") ? bloco : bloco + "}";
            String name = extrairValorString(bloco, "name");
            String code = extrairValorString(bloco, "code");
            String country = extrairValorString(bloco, "country");
            int founded = extrairValorInt(bloco, "founded");
            String stadium = extrairValorString(bloco, "stadium");
            String logo = extrairValorString(bloco, "logo");
            lista.add(new Club(name, code, country, founded, stadium, logo, new IPlayer[0]));
        }
        return lista.toArray(new Club[0]);
    }

    /**
     * Importa jogadores de um ficheiro JSON especificado.
     *
     * @param ficheiroPlayers nome do ficheiro JSON com dados dos jogadores.
     * @return array de objetos Player importados.
     */
    public Player[] importarJogadores(String ficheiroPlayers) {
        String json = lerFicheiro(ficheiroPlayers);
        if (json.isBlank()) return new Player[0];

        String conteudo = json.replaceAll("^\\s*\\[|\\]\\s*$", "");
        String[] entradas = conteudo.split("\\},\\s*\\{");

        List<Player> lista = new ArrayList<>();
        for (String raw : entradas) {
            String bloco = raw.startsWith("{") ? raw : "{" + raw;
            bloco = bloco.endsWith("}") ? bloco : bloco + "}";
            String nome = extrairValorString(bloco, "name");
            String posicaoStr = extrairValorString(bloco, "position").toUpperCase();
            int number = extrairValorInt(bloco, "number");
            String nacionalidade = extrairValorString(bloco, "nationality");
            String photo = extrairValorString(bloco, "photo");
            int passing = extrairValorInt(bloco, "passing");
            int shooting = extrairValorInt(bloco, "shooting");
            int speed = extrairValorInt(bloco, "speed");
            int stamina = extrairValorInt(bloco, "stamina");
            int height = extrairValorInt(bloco, "height");
            int weight = extrairValorInt(bloco, "weight");
            int reflexes = extrairValorInt(bloco, "reflexes");
            int tackling = extrairValorInt(bloco, "tackling");
            LocalDate birthDate = LocalDate.now();

            PlayerPosition pos = new PlayerPosition(posicaoStr);
            PreferredFoot foot = PreferredFoot.Right;

            lista.add(new Player(nome, birthDate, nacionalidade, number, 50, photo, pos, foot,
                    passing, shooting, speed, (float) height / 100f, (float) weight,
                    stamina, reflexes, tackling));
        }
        return lista.toArray(new Player[0]);
    }

    /**
     * Método auxiliar que lê um ficheiro a partir dos recursos da aplicação.
     *
     * @param nomeFicheiro nome do ficheiro a ler.
     * @return conteúdo do ficheiro como String.
     */
    private String lerFicheiro(String nomeFicheiro) {
        try (InputStream in = getClass().getClassLoader().getResourceAsStream(nomeFicheiro)) {
            if (in == null) return "";
            BufferedReader r = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String linha;
            while ((linha = r.readLine()) != null) {
                sb.append(linha.trim());
            }
            return sb.toString();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Método auxiliar que extrai um valor String de um campo específico em JSON.
     *
     * @param json JSON onde procurar.
     * @param campo nome do campo a extrair.
     * @return valor do campo como String.
     */
    private String extrairValorString(String json, String campo) {
        Pattern p = Pattern.compile("\"" + campo + "\"\\s*:\\s*\"([^\"]*)\"");
        Matcher m = p.matcher(json);
        return m.find() ? m.group(1) : "";
    }

    /**
     * Método auxiliar que extrai um valor inteiro de um campo específico em JSON.
     *
     * @param json JSON onde procurar.
     * @param campo nome do campo a extrair.
     * @return valor do campo como int.
     */
    private int extrairValorInt(String json, String campo) {
        Pattern p = Pattern.compile("\"" + campo + "\"\\s*:\\s*(\\d+)");
        Matcher m = p.matcher(json);
        return m.find() ? Integer.parseInt(m.group(1)) : 0;
    }
}
