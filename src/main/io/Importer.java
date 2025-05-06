package io;

import model.Player;
import main.model.PlayerPosition;
import com.ppstudios.footballmanager.api.contracts.player.PreferredFoot;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;

public class Importer {

    // Método para importar jogadores de um ficheiro
    public Player[] importarJogadores(String nomeFicheiro) {
        Player[] jogadores = new Player[30];
        int contador = 0;

        try {
            // Acessar o ficheiro diretamente sem "resources/"
            InputStream input = getClass().getClassLoader().getResourceAsStream(nomeFicheiro);

            // Verificar se o ficheiro foi encontrado
            if (input == null) {
                System.out.println("❌ Erro: ficheiro '" + nomeFicheiro + "' não encontrado.");
                return new Player[0];  // Retorna um array vazio se não encontrado
            }

            // Ler o conteúdo do ficheiro
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            StringBuilder sb = new StringBuilder();
            String linha;

            while ((linha = reader.readLine()) != null) {
                sb.append(linha.trim());
            }

            reader.close();

            // Processar o conteúdo JSON
            String conteudo = sb.toString();
            String[] entradas = conteudo.split("\\{");

            // Processar as entradas e extrair informações dos jogadores
            for (int i = 0; i < entradas.length; i++) {
                if (entradas[i].contains("name")) {
                    String json = "{" + entradas[i];

                    // Extrair campos do JSON
                    String nome = extrairCampo(json, "name");
                    String nascimento = extrairCampo(json, "birthDate");
                    String nacionalidade = extrairCampo(json, "nationality");
                    String posicao = extrairCampo(json, "basePosition");
                    String foto = extrairCampo(json, "photo");
                    int numero = Integer.parseInt(extrairCampo(json, "number"));

                    // Converter a data de nascimento para LocalDate
                    LocalDate data = LocalDate.parse(nascimento);
                    // Criar a posição do jogador
                    PlayerPosition pPos = new PlayerPosition(posicao);
                    // Definir o pé preferido (default é direito)
                    PreferredFoot pe = PreferredFoot.Right;

                    // Adicionar o jogador ao array
                    jogadores[contador++] = new Player(nome, data, nacionalidade, numero, 50, foto,
                            pPos, pe, 50, 50, 50, 1.80f, 75f);
                }
            }

        } catch (Exception e) {
            System.out.println("❌ Erro ao importar jogadores.");
            e.printStackTrace();
        }

        // Copiar para um array de tamanho exato
        Player[] resultado = new Player[contador];
        for (int i = 0; i < contador; i++) {
            resultado[i] = jogadores[i];
        }

        return resultado;
    }

    // Método auxiliar para extrair um campo específico do JSON
    private String extrairCampo(String json, String campo) {
        String[] partes = json.split(",");
        for (String p : partes) {
            if (p.contains("\"" + campo + "\"")) {
                String[] kv = p.split(":");
                String valor = kv[1].replaceAll("[\"}]", "").trim();  // Limpar as aspas e chaves

                // Caso o campo seja "number", limpar qualquer caractere extra como "]"
                if (campo.equals("number")) {
                    valor = valor.replaceAll("[^\\d]", "");  // Remove qualquer caractere não numérico
                }
                return valor;
            }
        }
        return "";
    }


    // Método para importar os nomes dos clubes
    public String[] importarNomesClubes(String nomeFicheiro) {
        String[] clubes = new String[50];
        int contador = 0;

        try {
            // Acessar o ficheiro diretamente sem "resources/"
            InputStream input = getClass().getClassLoader().getResourceAsStream(nomeFicheiro);

            // Verificar se o ficheiro foi encontrado
            if (input == null) {
                System.out.println("❌ Erro: ficheiro '" + nomeFicheiro + "' não encontrado.");
                return new String[0];  // Retorna um array vazio se não encontrado
            }

            // Ler o conteúdo do ficheiro
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            StringBuilder sb = new StringBuilder();
            String linha;

            while ((linha = reader.readLine()) != null) {
                sb.append(linha.trim());
            }

            reader.close();

            // Processar o conteúdo JSON
            String conteudo = sb.toString();
            String[] entradas = conteudo.split("\\{");

            // Extrair os nomes dos clubes
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

        // Copiar para um array de tamanho exato
        String[] resultado = new String[contador];
        for (int i = 0; i < contador; i++) {
            resultado[i] = clubes[i];
        }

        return resultado;
    }
}
