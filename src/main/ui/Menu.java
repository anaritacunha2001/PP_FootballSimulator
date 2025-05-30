package main.ui;

import com.ppstudios.footballmanager.api.contracts.league.IStanding;
import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import com.ppstudios.footballmanager.api.contracts.simulation.MatchSimulatorStrategy;

import io.Importer;
import io.Exporter;

import main.league.Season;
import main.simulation.MatchSimulator;
import main.manager.Formation;
import main.manager.Team;
import main.model.Club;
import main.model.Player;
import main.match.Match;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Menu {
    private static final BufferedReader reader =
            new BufferedReader(new InputStreamReader(System.in));
    private static final Importer importer = new Importer();
    private static final Exporter exporter = new Exporter();

    private static Club[] clubes = new Club[0];
    private static Season season;
    private static final int MAX_TEAMS  = 20;
    private static final int MAX_ROUNDS = 20;

    public void mostrarMenu() throws IOException {
        boolean running = true;
        while (running) {
            mostrarMenuPrincipal();
            String opcao = reader.readLine();
            running = tratarOpcao(opcao);
        }
    }

    private void mostrarMenuPrincipal() {
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
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private boolean tratarOpcao(String opcao) throws IOException {
        switch (opcao) {
            case "1": criarEpoca();            break;
            case "2": adicionarClubes();       break;
            case "3": verClubes();             break;
            case "4": verPlantel();            break;
            case "5": gerarCalendario();       break;
            case "6": simularProximaRonda();   break;
            case "7": simularEpocaCompleta();  break;
            case "8": verResultadosRonda();    break;
            case "9": verClassificacao();      break;
            case "10": verEventosDeJogo();     break;
            case "11": historicoEpoca();       break;
            case "0":
                System.out.println("A sair...");
                return false;
            default:
                System.out.println("Opção inválida.");
        }
        return true;
    }

    private void criarEpoca() throws IOException {
        System.out.print("\nNome da liga: ");
        String nome = reader.readLine();
        System.out.print("Temporada (ex: 2024/25): ");
        String temp = reader.readLine();
        season = new Season(nome + " " + temp, 0, MAX_TEAMS, MAX_ROUNDS);
        season.setMatchSimulator(new MatchSimulator());
        System.out.println("Época criada: " + nome + " " + temp);
    }

    private void adicionarClubes() {
        if (season == null) {
            System.out.println("Crie primeiro a época (opção 1).");
            return;
        }
        clubes = importer.importarClubes("clubs.json");
        if (clubes.length < 2) {
            System.out.println("Erro: é necessário pelo menos 2 clubes em clubs.json.");
            clubes = new Club[0];
            return;
        }
        for (Club c : clubes) {
            Player[] plantel = importer.importarJogadores(
                    "players/" + keyDoFicheiroPara(c.getName()) + ".json"
            );
            c.setPlayers(plantel);
            season.addClub(c, "4-4-2");
        }
        System.out.println(clubes.length + " clubes adicionados com formação 4-4-2.");
    }

    private void verClubes() {
        if (season == null) {
            System.out.println("Crie primeiro a época (opção 1).");
            return;
        }
        if (clubes.length == 0) {
            System.out.println("Nenhum clube carregado. Use a opção 2 para adicionar.");
            return;
        }
        System.out.println("\n--- Clubes da Liga ---");
        for (int i = 0; i < clubes.length; i++) {
            System.out.printf("%2d) %s%n", i + 1, clubes[i].getName());
        }
    }

    private void verPlantel() throws IOException {
        System.out.print("Nome da equipa (ex: benfica): ");
        String nome = reader.readLine();
        Player[] plantel = importer.importarJogadores(
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
            System.out.println("Crie primeiro a época (opção 1).");
            return;
        }
        try {
            season.generateSchedule();
            System.out.println("\n--- Calendário completo ---");
            season.printFullSchedule();
        } catch (Exception e) {
            System.out.println("Erro ao gerar calendário: " + e.getMessage());
        }
    }

    private void simularProximaRonda() throws IOException {
        if (season == null) {
            System.out.println("Crie primeiro a época (opção 1).");
            return;
        }
        if (season.isSeasonComplete()) {
            System.out.println("A época já terminou.");
            return;
        }
        int round = season.getCurrentRound();
        IMatch[] jogos = season.getMatches(round);

        System.out.println("\n--- Simular ronda " + (round + 1) + " ---");
        // ** HERE IS THE FIXED LOOP WITH BRACES & SEMICOLONS **
        for (IMatch jogo : jogos) {
            prepararEquipaParaJogo(jogo, true);
            prepararEquipaParaJogo(jogo, false);
        }

        season.simulateRound();
        exporter.exportarEpoca(season, "epoca.json");

        System.out.println("\n--- Resultados ronda " + (round + 1) + " ---");
        for (IMatch m : jogos) {
            System.out.println(" - " + season.displayMatchResult(m));
        }
    }

    private void simularEpocaCompleta() throws IOException {
        if (season == null) {
            System.out.println("Crie primeiro a época (opção 1).");
            return;
        }
        System.out.println("\n--- Simular época completa ---");
        while (!season.isSeasonComplete()) {
            simularProximaRonda();
        }
        System.out.println("Época completa terminada!");
    }

    private void verResultadosRonda() throws IOException {
        if (season == null) return;
        System.out.print("Ronda (0 a " + (season.getCurrentRound() - 1) + "): ");
        int r = Integer.parseInt(reader.readLine());
        System.out.println("\n--- Resultados ronda " + r + " ---");
        for (IMatch m : season.getMatches(r)) {
            System.out.println(" - " + season.displayMatchResult(m));
        }
    }

    private void verClassificacao() {
        if (season == null) return;
        System.out.println("\n--- Classificação ---");
        for (IStanding s : season.getLeagueStandings()) {
            System.out.printf("%s: %d pts%n",
                    s.getTeam().getClub().getName(), s.getPoints());
        }
    }

    private void verEventosDeJogo() throws IOException {
        if (season == null) return;
        System.out.print("Ronda (0 a " + (season.getCurrentRound() - 1) + "): ");
        int r = Integer.parseInt(reader.readLine());
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

    private void prepararEquipaParaJogo(IMatch jogo, boolean home) throws IOException {
        IClub clube = home ? jogo.getHomeClub() : jogo.getAwayClub();
        System.out.println("\nEscolher formação e titulares para " + clube.getName());
        System.out.println(" 1) 4-4-2   2) 4-3-3   3) 3-5-2");
        System.out.print("Formação: ");
        String op = reader.readLine();
        String form = switch (op) {
            case "2" -> "4-3-3";
            case "3" -> "3-5-2";
            default  -> "4-4-2";
        };

        IPlayer[] plantel = ((Club) clube).getPlayers();
        IPlayer[] titulares = new IPlayer[11];
        boolean[] usados = new boolean[plantel.length];

        System.out.println("Escolha 11 titulares:");
        for (int i = 0; i < plantel.length; i++) {
            System.out.printf("%2d) %s%n", i + 1, plantel[i].getName());
        }
        for (int i = 0; i < 11; i++) {
            int idx;
            do {
                System.out.print("Jogador #" + (i + 1) + ": ");
                idx = Integer.parseInt(reader.readLine()) - 1;
                if (idx < 0 || idx >= plantel.length || usados[idx]) {
                    System.out.println("Inválido ou já usado!");
                    idx = -1;
                }
            } while (idx < 0);
            titulares[i] = plantel[idx];
            usados[idx] = true;
        }

        Team equipa = new Team((Club) clube, new Formation(form));
        for (IPlayer p : titulares) {
            equipa.addPlayer(p);
        }

        Match real = (Match) jogo;
        if (home)   real.setHomeTeam(equipa);
        else        real.setAwayTeam(equipa);
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
