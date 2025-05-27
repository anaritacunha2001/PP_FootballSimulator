package main.ui;

import com.ppstudios.footballmanager.api.contracts.league.IStanding;
import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import io.Exporter;
import io.Importer;
import main.league.Season;
import main.model.Club;
import main.model.Player;
import main.simulation.MatchSimulator;

import java.util.Scanner;

public class Menu {

    private final Scanner scanner = new Scanner(System.in);
    private final Importer importer = new Importer();
    private final Exporter exporter = new Exporter();
    private Season season;
    private final int maxClubes = 10;
    private final int maxRondas = 5;
    private Player[] jogadores;

    public void mostrarMenuPrincipal() {
        int opcao;
        do {
            mostrarCabecalho();
            opcao = lerOpcao();
            tratarOpcao(opcao);
        } while (opcao != 0);
    }

    private void mostrarCabecalho() {
        System.out.println("\n==== SIMULADOR DE FUTEBOL - PPStudios ====");
        System.out.println("1. Criar nova época");
        System.out.println("2. Adicionar clubes");
        System.out.println("3. Ver plantel da equipa");
        System.out.println("4. Ver nomes dos clubes");
        System.out.println("5. Gerar calendário");
        System.out.println("6. Simular 1 ronda");
        System.out.println("7. Simular época completa");
        System.out.println("8. Ver resultados de uma ronda");
        System.out.println("9. Ver classificação");
        System.out.println("10. Ver todos os eventos de um jogo");
        System.out.println("11. Ver histórico de resultados da época");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private int lerOpcao() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void tratarOpcao(int opcao) {
        switch (opcao) {
            case 1 -> criarEpoca();
            case 2 -> adicionarClubes();
            case 3 -> verPlantel();
            case 4 -> verClubes();
            case 5 -> gerarCalendario();
            case 6 -> simularRonda();
            case 7 -> simularEpocaCompleta();
            case 8 -> verResultadosRonda();
            case 9 -> verClassificacao();
            case 10 -> verEventosDeJogo();
            case 11 -> verHistoricoResultados();
            case 0 -> System.out.println("A sair...");
            default -> System.out.println("Opção inválida.");
        }
    }

    private void criarEpoca() {
        System.out.println("\nNome da época:");
        String nome = scanner.nextLine();
        System.out.println("Ano da época:");
        int ano = Integer.parseInt(scanner.nextLine());

        season = new Season(nome, ano, maxClubes, maxRondas);
        season.setMatchSimulator(new MatchSimulator());
        System.out.println("Época criada com sucesso.");
    }

    private void adicionarClubes() {
        Club[] clubes = importer.importarClubes("clubs.json");

        System.out.println("\n--- Formações disponíveis ---");
        System.out.println("1. 4-4-2");
        System.out.println("2. 4-3-3");
        System.out.println("3. 3-5-2");
        System.out.print("Escolha a formação para todos os clubes: ");
        int formacaoEscolhida = lerOpcao();
        String formacao;
        switch (formacaoEscolhida) {
            case 1 -> formacao = "4-4-2";
            case 2 -> formacao = "4-3-3";
            case 3 -> formacao = "3-5-2";
            default -> {
                System.out.println("Formação inválida.");
                return;
            }
        }

        int adicionados = 0;
        for (IClub club : clubes) {
            if (season.addClub(club, formacao)) {
                adicionados++;
            }
        }
        System.out.println(adicionados + " clubes adicionados com formação " + formacao + ".");
    }

    private void verPlantel() {
        System.out.print("Nome da equipa (ex: benfica): ");
        String nome = scanner.nextLine().toLowerCase();
        String caminho = "players/" + nome + ".json";
        jogadores = importer.importarJogadores(caminho);

        System.out.println("\n--- Plantel da Equipa: " + nome + " ---");
        if (jogadores.length > 0) {
            for (int i = 0; i < jogadores.length; i++) {
                Player p = jogadores[i];
                if (p != null) {
                    System.out.printf("%d. %s (%s) #%d - %s | Remate: %d | Passe: %d | Vel: %d | Ref: %d | Tack: %d | Drib: %d | Pé: %s\n",
                            i + 1,
                            p.getName(),
                            p.getNationality(),
                            p.getNumber(),
                            p.getPosition().getDescription(),
                            p.getShooting(),
                            p.getPassing(),
                            p.getSpeed(),
                            p.getReflexes(),
                            p.getTackling(),
                            p.getDribbling(),
                            p.getPreferredFoot()
                    );
                }
            }
        } else {
            System.out.println("Erro: ficheiro '" + caminho + "' não encontrado ou vazio.");
        }
    }

    private void verClubes() {
        String[] clubes = importer.importarNomesClubes("clubs.json");
        System.out.println("\n--- Clubes Disponíveis ---");
        for (int i = 0; i < clubes.length; i++) {
            System.out.println((i + 1) + ". " + clubes[i]);
        }
    }

    private void gerarCalendario() {
        if (season == null) {
            System.out.println("Primeiro cria a época e adiciona clubes.");
            return;
        }

        try {
            season.generateSchedule();
            System.out.println("Calendário gerado com sucesso!\n");
            season.printFullSchedule();
        } catch (IllegalStateException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void simularRonda() {
        if (season == null) {
            System.out.println("Primeiro cria a época e adiciona clubes.");
            return;
        }

        int rondaAtual = season.getCurrentRound();
        if (rondaAtual >= season.getMaxRounds()) {
            System.out.println("Todas as rondas já foram simuladas.");
            return;
        }

        System.out.println("\n--- Simulação da Ronda " + (rondaAtual + 1) + " ---\n");
        season.simulateRound();
        exporter.exportarEpoca(season, "epoca.json");
        System.out.println("Ronda " + (season.getCurrentRound()) + " simulada com sucesso. Resultados exportados.\n");
    }

    private void simularEpocaCompleta() {
        if (season == null) {
            System.out.println("Primeiro cria a época e adiciona clubes.");
            return;
        }

        if (season.isSeasonComplete()) {
            System.out.println("A época já está concluída.");
            return;
        }

        if (season.getSchedule() == null) {
            System.out.println("Gera primeiro o calendário antes de simular a época.");
            return;
        }

        season.simulateAndPrintSeason();
        exporter.exportarEpoca(season, "epoca.json");
        System.out.println("✔ Resultados da época exportados para epoca.json");
    }

    private void verResultadosRonda() {
        if (season == null) return;

        int rondasJogadas = season.getCurrentRound();
        if (rondasJogadas == 0) {
            System.out.println("⚠ Ainda não foi simulada nenhuma ronda.");
            return;
        }

        System.out.print("Número da ronda (0 a " + (rondasJogadas - 1) + "): ");
        int ronda = lerOpcao();
        if (ronda < 0 || ronda >= rondasJogadas) {
            System.out.println("⚠ Essa ronda ainda não foi simulada.");
            return;
        }

        IMatch[] jogos = season.getMatches(ronda);
        if (jogos.length == 0) {
            System.out.println("⚠ Nenhum jogo encontrado para essa ronda.");
            return;
        }

        System.out.println("\n== Resultados da Ronda " + ronda + " ==");
        for (IMatch match : jogos) {
            if (match != null) {
                System.out.println(season.displayMatchResult(match));
            }
        }
    }

    private void verClassificacao() {
        if (season == null) return;

        IStanding[] tabela = season.getLeagueStandings();
        System.out.println("\nClassificação:");
        for (IStanding s : tabela) {
            if (s != null && s.getTeam() != null) {
                System.out.println(s.getTeam().getClub().getName() + " - " + s.getPoints() + " pts");
            }
        }
    }

    private void verEventosDeJogo() {
        if (season == null) return;

        int rondasJogadas = season.getCurrentRound();
        if (rondasJogadas == 0) {
            System.out.println("⚠ Ainda não foi simulada nenhuma ronda.");
            return;
        }

        System.out.print("Número da ronda (0 a " + (rondasJogadas - 1) + "): ");
        int ronda = lerOpcao();
        if (ronda < 0 || ronda >= rondasJogadas) {
            System.out.println("⚠ Essa ronda ainda não foi simulada.");
            return;
        }

        IMatch[] jogos = season.getMatches(ronda);
        if (jogos.length == 0) {
            System.out.println("⚠ Nenhum jogo encontrado para essa ronda.");
            return;
        }

        System.out.println("\n== Eventos Detalhados da Ronda " + ronda + " ==");
        for (IMatch match : jogos) {
            System.out.println("\n--- " + match.getHomeClub().getName() + " vs " + match.getAwayClub().getName() + " ---");
            for (var evento : match.getEvents()) {
                System.out.println(" - " + evento);
            }
        }
    }

    private void verHistoricoResultados() {
        if (season == null) return;

        IMatch[] todosOsJogos = season.getMatches();
        System.out.println("\n--- Histórico de todos os jogos ---");
        for (IMatch match : todosOsJogos) {
            if (match != null && match.isPlayed()) {
                System.out.println(season.displayMatchResult(match));
            }
        }
    }
}
