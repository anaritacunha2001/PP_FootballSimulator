package io;

import main.model.Player;
import main.model.PlayerPosition;
import com.ppstudios.footballmanager.api.contracts.player.PreferredFoot;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;

public class Importer {

    public Player[] importarJogadores(String nomeFicheiro) {
        Player[] jogadores = new Player[30];
        int contador = 0;

        try {
            InputStream input = getClass().getClassLoader().getResourceAsStream(nomeFicheiro);
            if (input == null) {
                System.out.println("❌ Erro: ficheiro '" + nomeFicheiro + "' não encontrado.");
                return new Player[0];
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            StringBuilder sb = new StringBuilder();
            String linha;

            while ((linha = reader.readLine()) != null) {
                sb.append(linha.trim());
            }
            reader.close();

            String conteudo = sb.toString();
            String[] entradas = conteudo.split("\\{");

            for (int i = 0; i < entradas.length; i++) {
                if (entradas[i].contains("name")) {
                    String json = "{" + entradas[i];

                    String nome = extrairCampo(json, "name");
                    String nascimento = extrairCampo(json, "birthDate");
                    String nacionalidade = extrairCampo(json, "nationality");
                    String posicao = extrairCampo(json, "position");
                    String foto = extrairCampo(json, "photo");
                    int numero = Integer.parseInt(extrairCampo(json, "number"));

                    LocalDate data = LocalDate.parse(nascimento);
                    PlayerPosition pPos = new PlayerPosition(posicao);
                    PreferredFoot pe = PreferredFoot.Right;

                    jogadores[contador++] = new Player(
                            nome,
                            data,
                            nacionalidade,
                            numero,
                            50, // passing
                            foto,
                            pPos,
                            pe,
                            50, // shooting
                            50, // speed
                            50, // stamina
                            1.80f, // height
                            75f,  // weight
                            50,   // reflexes
                            50,   // tackling
                            50    // dribbling
                    );
                }
            }

        } catch (Exception e) {
            System.out.println("❌ Erro ao importar jogadores.");
            e.printStackTrace();
        }

        Player[] resultado = new Player[contador];
        for (int i = 0; i < contador; i++) {
            resultado[i] = jogadores[i];
        }

        return resultado;
    }

    private String extrairCampo(String json, String campo) {
        String[] partes = json.split(",");
        for (String p : partes) {
            if (p.contains("\"" + campo + "\"")) {
                String[] kv = p.split(":", 2);
                if (kv.length > 1) {
                    String valor = kv[1].replaceAll("[\"}]", "").trim();
                    if (campo.equals("number")) {
                        valor = valor.replaceAll("[^\\d]", "");
                    }
                    return valor;
                }
            }
        }
        return "";
    }

    public String[] importarNomesClubes(String nomeFicheiro) {
        String[] clubes = new String[50];
        int contador = 0;

        try {
            InputStream input = getClass().getClassLoader().getResourceAsStream(nomeFicheiro);
            if (input == null) {
                System.out.println("❌ Erro: ficheiro '" + nomeFicheiro + "' não encontrado.");
                return new String[0];
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            StringBuilder sb = new StringBuilder();
            String linha;

            while ((linha = reader.readLine()) != null) {
                sb.append(linha.trim());
            }
            reader.close();

            String conteudo = sb.toString();
            String[] entradas = conteudo.split("\\{");

            for (int i = 0; i < entradas.length; i++) {
                if (entradas[i].contains("name")) {
                    String bloco = "{" + entradas[i];
                    String nome = extrairCampo(bloco, "name");
                    clubes[contador++] = nome;
                }
            }

        } catch (Exception e) {
            System.out.println("❌ Erro ao importar clubes.");
            e.printStackTrace();
        }

        String[] resultado = new String[contador];
        for (int i = 0; i < contador; i++) {
            resultado[i] = clubes[i];
        }

        return resultado;
    }
}
