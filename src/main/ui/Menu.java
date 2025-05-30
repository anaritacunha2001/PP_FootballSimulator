// src/main/ui/Menu.java
package main.ui;

import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import io.Exporter;
import io.Importer;
import main.league.Season;
import main.match.GameEvent;
import main.simulation.MatchSimulator;
import main.manager.Formation;
import main.manager.Team;
import main.model.Club;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.io.*;
import java.util.*;
import java.util.stream.IntStream;

public class Menu {
    private static final BufferedReader reader =
            new BufferedReader(new InputStreamReader(System.in));
    private static final Importer importer = new Importer();
    private static final Exporter exporter = new Exporter();

    private static Club[] clubes = new Club[0];
    private static Season season;
    private static final int MAX_TEAMS = 20;
    private static final String[] FORMACOES = { "4-4-2", "4-3-3", "3-5-2" };
    private static final Random RNG = new Random();

    public void mostrarMenu() throws IOException {
        // Prepara o ficheiro de export
        exporter.startExport();

        boolean running = true;
        while (running) {
            System.out.println("\n==== SIMULADOR DE FUTEBOL – PPStudios ====");
            System.out.println("1. Criar época / Liga");
            System.out.println("2. Adicionar clubes à liga");
            System.out.println("3. Ver clubes da liga");
            System.out.println("4. Ver plantel de uma equipa");
            System.out.println("5. Ver calendário");
            System.out.println("6. Simular próxima ronda");
            System.out.println("7. Simular época completa");
            System.out.println("8. Ver resultados de uma ronda");
            System.out.println("9. Ver classificação");
            System.out.println("10. Ver todos os eventos de um jogo");
            System.out.println("11. Ver histórico de resultados");
            System.out.println("12. Mostrar output/epocas.json");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            String opcao = reader.readLine();
            switch (opcao) {
                case "1": criarEpoca(); break;
                case "2": adicionarClubes(); break;
                case "3": verClubes(); break;
                case "4": verPlantel(); break;
                case "5": gerarCalendario(); break;
                case "6": simularProximaRonda(); break;
                case "7": simularEpocaCompleta(); break;
                case "8": verResultadosRonda(); break;
                case "9": verClassificacao(); break;
                case "10": verEventosDeJogo(); break;
                case "11": historicoEpoca(); break;
                case "12": mostrarEpocasJson(); break;
                case "0":
                    System.out.println("A sair...");
                    running = false;
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private void criarEpoca() throws IOException {
        System.out.print("\nNome da liga: ");
        String nome = reader.readLine();
        System.out.print("Temporada (ex: 2024/25): ");
        String temp = reader.readLine();
        season = new Season(nome + " " + temp, 0, MAX_TEAMS, 0);
        season.setMatchSimulator(new MatchSimulator());
        // reinicia ficheiro de export
        exporter.startExport();
        System.out.println("Época criada: " + nome + " " + temp);
    }

    private void adicionarClubes() {
        if (season == null) {
            System.out.println("Crie primeiro a época (opção 1).");
            return;
        }
        clubes = importer.importarClubes("clubs.json");
        if (clubes.length < 2) {
            System.out.println("Erro: são necessários pelo menos 2 clubes.");
            clubes = new Club[0];
            return;
        }
        for (Club c : clubes) {
            c.setPlayers(importer.importarJogadores(
                    "players/" + keyDoFicheiroPara(c.getName()) + ".json"
            ));
            season.addClub(c, "4-4-2");
        }
        System.out.println(clubes.length + " clubes adicionados");
    }

    private void verClubes() {
        if (season == null || clubes.length == 0) {
            System.out.println("Use as opções 1 e 2 primeiro.");
            return;
        }
        System.out.println("\n--- Clubes da Liga ---");
        for (int i = 0; i < clubes.length; i++) {
            System.out.printf("%2d) %s%n", i + 1, clubes[i].getName());
        }
    }

    private void verPlantel() throws IOException {
        System.out.print("Nome da equipa: ");
        String nome = reader.readLine();
        var plantel = importer.importarJogadores(
                "players/" + keyDoFicheiroPara(nome) + ".json"
        );
        if (plantel.length == 0) {
            System.out.println("Nenhum jogador encontrado para: " + nome);
            return;
        }
        System.out.println("\n--- Plantel de " + nome + " ---");
        for (int i = 0; i < plantel.length; i++) {
            System.out.printf("%2d) %s – %s%n",
                    i + 1,
                    plantel[i].getName(),
                    plantel[i].getPosition().getDescription()
            );
        }
    }

    private void gerarCalendario() {
        if (season == null) {
            System.out.println("Crie primeiro a época.");
            return;
        }
        try {
            season.generateSchedule();
            System.out.println("\n--- Calendário completo ---");
            season.printFullSchedule();
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void simularProximaRonda() throws IOException {
        if (season == null || season.isSeasonComplete()) {
            System.out.println("Época não iniciada ou já completa.");
            return;
        }
        int roundIndex = season.getCurrentRound();
        IMatch[] jogos = season.getMatches(roundIndex);

        // escalações e formações aleatórias
        for (IMatch jogo : jogos) {
            String fH = FORMACOES[RNG.nextInt(FORMACOES.length)];
            String fA = FORMACOES[RNG.nextInt(FORMACOES.length)];
            IPlayer[] homeLineup = selectRandomLineup((Club) jogo.getHomeClub());
            IPlayer[] awayLineup = selectRandomLineup((Club) jogo.getAwayClub());

            Team th = new Team((Club) jogo.getHomeClub(), new Formation(fH));
            for (IPlayer p : homeLineup) th.addPlayer(p);
            ((main.match.Match) jogo).setHomeTeam(th);

            Team ta = new Team((Club) jogo.getAwayClub(), new Formation(fA));
            for (IPlayer p : awayLineup) ta.addPlayer(p);
            ((main.match.Match) jogo).setAwayTeam(ta);
        }

        // simula e grava
        season.simulateRound();
        exporter.appendRound(season, roundIndex + 1);

        System.out.println("\n--- Resultados Ronda " + (roundIndex + 1) + " ---");
        for (IMatch m : jogos) {
            System.out.println(" - " + season.displayMatchResult(m));
        }
    }

    private void simularEpocaCompleta() throws IOException {
        if (season == null) {
            System.out.println("Crie primeiro a época.");
            return;
        }
        while (!season.isSeasonComplete()) {
            simularProximaRonda();
        }
        System.out.println("Época completa terminada!");
    }

    private void verResultadosRonda() throws IOException {
        if (season == null) return;
        int totalJogos = season.getMatches().length;
        int jogosPorRonda = clubes.length / 2;
        int totalRondas = totalJogos / jogosPorRonda;
        System.out.print("Ronda (1 a " + totalRondas + "): ");

        int r = Integer.parseInt(reader.readLine()) - 1;
        IMatch[] jogos = season.getMatches(r);

        System.out.println("\n--- Resultados ronda " + (r + 1) + " ---");
        for (IMatch m : jogos) {
            int gH = m.getTotalByEvent(GameEvent.class, m.getHomeClub());
            int gA = m.getTotalByEvent(GameEvent.class, m.getAwayClub());
            System.out.printf("%s %d - %d %s%n",
                    m.getHomeClub().getName(), gH, gA, m.getAwayClub().getName());
        }
    }

    private void verClassificacao() throws IOException {
        Path f = Paths.get("output", "epocas.json");
        if (Files.notExists(f) || Files.size(f) == 0) {
            System.out.println("Nenhuma época guardada ainda.");
            return;
        }

        System.out.print("Nome da liga: ");
        String liga = reader.readLine().trim();
        System.out.print("Temporada (ex: 23/24): ");
        String temp = reader.readLine().trim();
        String fullName = liga + " " + temp;

        // lê tudo para uma string
        String content = Files.readString(f, StandardCharsets.UTF_8);

        // regex para encontrar cada objecto {...} da época certa
        Pattern pSeason = Pattern.compile(
                "\\{(?:[^{}]|\\{[^}]*\\})*\"name\"\\s*:\\s*\"" +
                        Pattern.quote(fullName) +
                        "\"(?:[^{}]|\\{[^}]*\\})*\\}"
                , Pattern.DOTALL);
        Matcher mSeason = pSeason.matcher(content);

        // regex para extrair resultados dentro de cada objecto
        Pattern pResult = Pattern.compile(
                "\\{\\s*\"round\"\\s*:\\s*\\d+\\s*,\\s*"
                        + "\"home\"\\s*:\\s*\"([^\"]*)\"\\s*,\\s*"
                        + "\"away\"\\s*:\\s*\"([^\"]*)\"\\s*,\\s*"
                        + "\"score\"\\s*:\\s*\"(\\d+)-(\\d+)\"\\s*\\}"
        );

        // mapa para acumular pontos
        Map<String, Integer> pontos = new HashMap<>();

        while (mSeason.find()) {
            String bloco = mSeason.group();
            Matcher mRes = pResult.matcher(bloco);
            while (mRes.find()) {
                String casa = mRes.group(1);
                String fora = mRes.group(2);
                int gC = Integer.parseInt(mRes.group(3));
                int gF = Integer.parseInt(mRes.group(4));

                pontos.putIfAbsent(casa, 0);
                pontos.putIfAbsent(fora, 0);

                if (gC > gF) {
                    pontos.put(casa, pontos.get(casa) + 3);
                } else if (gC < gF) {
                    pontos.put(fora, pontos.get(fora) + 3);
                } else {
                    pontos.put(casa, pontos.get(casa) + 1);
                    pontos.put(fora, pontos.get(fora) + 1);
                }
            }
        }

        if (pontos.isEmpty()) {
            System.out.println("Época não encontrada em output/epocas.json.");
            return;
        }

        // ordena por pontos desc
        System.out.println("\n--- Classificação de " + fullName + " ---");
        pontos.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder()))
                .forEach(e -> System.out.printf("%s: %d pts%n", e.getKey(), e.getValue()));
    }

    private int getTotalRondas() {
        int totalJogos = season.getMatches().length;
        int jogosPorRonda = clubes.length / 2;
        return totalJogos / jogosPorRonda;
    }


    private void verEventosDeJogo() throws IOException {
        if (season == null) return;

        int totalRondas = getTotalRondas();
        System.out.print("Ronda (1 a " + totalRondas + "): ");
        int r = Integer.parseInt(reader.readLine()) - 1;

        IMatch[] jogos = season.getMatches(r);
        System.out.print("Jogo (1 a " + jogos.length + "): ");
        int j = Integer.parseInt(reader.readLine()) - 1;

        IMatch m = jogos[j];
        System.out.println("\n--- Eventos de " +
                m.getHomeClub().getName() + " vs " +
                m.getAwayClub().getName() + " ---");
        for (var e : m.getEvents()) {
            System.out.println(" * " + e);
        }
    }


    private void historicoEpoca() {
        if (season == null) return;
        System.out.println("\n--- Histórico de resultados ---");
        for (IMatch m : season.getMatches()) {
            System.out.println(" - " + season.displayMatchResult(m));
        }
    }

    private void mostrarEpocasJson() throws IOException {
        Path f = Paths.get("output", "epocas.json");
        if (Files.notExists(f) || Files.size(f) == 0) {
            System.out.println("Nenhuma época guardada ainda.");
            return;
        }

        System.out.print("Nome da liga: ");
        String liga = reader.readLine().trim();
        System.out.print("Temporada (ex: 23/24): ");
        String temp = reader.readLine().trim();
        String fullName = liga + " " + temp;

        List<String> lines = Files.readAllLines(f);
        Pattern pName = Pattern.compile("\"name\"\\s*:\\s*\"([^\"]*)\"");
        Pattern pRoundsPlayed = Pattern.compile("\"roundsPlayed\"\\s*:\\s*(\\d+)");
        Pattern pResult = Pattern.compile(
                "\\{\\s*\"round\"\\s*:\\s*(\\d+)\\s*,\\s*"
                        + "\"home\"\\s*:\\s*\"([^\"]*)\"\\s*,\\s*"
                        + "\"away\"\\s*:\\s*\"([^\"]*)\"\\s*,\\s*"
                        + "\"score\"\\s*:\\s*\"([^\"]*)\"\\s*\\}"
        );

        boolean foundAny = false;
        for (int i = 0; i < lines.size(); i++) {
            String stripped = lines.get(i).strip();
            Matcher mName = pName.matcher(stripped);
            if (mName.find() && fullName.equals(mName.group(1))) {
                // achou a entrada desta época: agora recolhe TODO o bloco { ... }
                // primeiro localiza a linha do '{' que abre este objeto
                int start = i;
                while (start >= 0 && !lines.get(start).contains("{")) {
                    start--;
                }
                // agora varre até fechar todas as chavetas
                List<String> block = new ArrayList<>();
                int brace = 0;
                for (int j = start; j < lines.size(); j++) {
                    String l = lines.get(j);
                    if (l.contains("{")) brace++;
                    if (l.contains("}")) brace--;
                    block.add(l.strip());
                    if (brace == 0) {
                        i = j;  // avança o i para não reprocessar dentro deste bloco
                        break;
                    }
                }

                // extrai o número da ronda
                int roundNum = 0;
                for (String l : block) {
                    Matcher mR = pRoundsPlayed.matcher(l);
                    if (mR.find()) {
                        roundNum = Integer.parseInt(mR.group(1));
                        break;
                    }
                }

                // imprime cabeçalho só uma vez
                System.out.println("\n--- Época: " + fullName + " ---");
                System.out.println("Ronda " + roundNum + ":");
                foundAny = true;

                // imprime cada resultado
                for (String l : block) {
                    Matcher mRes = pResult.matcher(l);
                    if (mRes.find()) {
                        String home  = mRes.group(2);
                        String away  = mRes.group(3);
                        String score = mRes.group(4);
                        System.out.printf("  %s %s %s%n", home, score, away);
                    }
                }
            }
        }

        if (!foundAny) {
            System.out.println("Época não encontrada em output/epocas.json.");
        }
    }


    private IPlayer[] selectRandomLineup(Club c) {
        IPlayer[] p = c.getPlayers();
        int n = p.length;
        int[] idx = IntStream.range(0, n).toArray();
        IPlayer[] sel = new IPlayer[11];
        for (int i = 0; i < 11; i++) {
            int swap = i + RNG.nextInt(n - i);
            int tmp = idx[i]; idx[i] = idx[swap]; idx[swap] = tmp;
            sel[i] = p[idx[i]];
        }
        return sel;
    }

    private String keyDoFicheiroPara(String nome) {
        return switch (nome) {
            case "Sporting CP"           -> "sporting";
            case "SL Benfica"            -> "benfica";
            case "FC Porto"              -> "fcporto";
            case "SC Braga"              -> "braga";
            case "Vitória SC"            -> "vitoriasc";
            case "CD Santa Clara"        -> "santaclara";
            case "FC Famalicão"          -> "famalicao";
            case "GD Estoril Praia"      -> "estoril";
            case "Casa Pia AC"           -> "casapia";
            case "Moreirense FC"         -> "moreirense";
            case "Rio Ave FC"            -> "rioave";
            case "Gil Vicente FC"        -> "gilvicente";
            case "SC Farense"            -> "farense";
            case "Boavista FC"           -> "boavista";
            case "CD Nacional"           -> "nacional";
            case "FC Arouca"             -> "arouca";
            case "CF Estrela da Amadora" -> "estrelaamadora";
            case "AVS Futebol SAD"       -> "avs";
            default                      -> nome.toLowerCase().replace(" ", "");
        };
    }
}
