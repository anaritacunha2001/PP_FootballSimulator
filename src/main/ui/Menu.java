package main.ui;

import com.ppstudios.footballmanager.api.contracts.league.IStanding;
import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import io.Importer;
import main.league.Season;
import main.model.Club;
import main.model.Player;
import main.simulation.MatchSimulator;

import java.util.Scanner;

public class Menu {

    private final Scanner scanner = new Scanner(System.in);
    private final Importer importer = new Importer();
    private Season season;
    private final int maxClubes = 10;
    private final int maxRondas = 5;
    private Player[] jogadores;

    public void mostrarMenuPrincipal() {
        int opcao;
        do {
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
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            opcao = lerOpcao();

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
                case 0 -> System.out.println("A sair...");
                default -> System.out.println("Opção inválida.");
            }

        } while (opcao != 0);
    }

    private int lerOpcao() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
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


        int adicionados = 0;
        for (IClub club : clubes) {
            if (season.addClub(club)) {
                adicionados++;
            }
        }
        System.out.println("" + adicionados + " clubes adicionados.");
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
        if (season != null) {
            season.generateSchedule();
            System.out.println("Calendário gerado com sucesso.");
        } else {
            System.out.println("Primeiro cria a época e adiciona clubes.");
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

        System.out.println("\n--- Simulação da Ronda " + (rondaAtual + 1) + " ---");

        // Simular a ronda atual
        season.simulateRound();

        IMatch[] jogos = season.getMatches(rondaAtual + 1); // porque o método pode esperar índice 1-based

        for (IMatch match : jogos) {
            if (match != null) {
                System.out.println(season.displayMatchResult(match));
            }
        }
    }


    private void simularEpocaCompleta() {
        if (season != null) {
            season.simulateSeason();
            System.out.println("Época simulada.");
        }
    }

    private void verResultadosRonda() {
        if (season == null) return;

        System.out.print("Número da ronda (0 a " + (season.getMaxRounds() - 1) + "): ");
        int ronda = Integer.parseInt(scanner.nextLine());
        IMatch[] jogos = season.getMatches(ronda);
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
}