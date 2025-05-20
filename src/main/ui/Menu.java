/*package ui;

import io.Importer;
import main.model.Player;

import java.util.Scanner;

public class Menu {

    private final Scanner scanner = new Scanner(System.in);
    private final Importer importer = new Importer();
    private Player[] jogadores;  // Variável jogadores declarada aqui

    public void mostrarMenuPrincipal() {
        int opcao;
        do {
            System.out.println("\n==== SIMULADOR DE FUTEBOL - PPStudios ====");
            System.out.println("1. Ver plantel da equipa");
            System.out.println("2. Ver nomes dos clubes");
            System.out.println("3. Escolher formação e jogadores titulares");
            System.out.println("4. Ver calendário de jogos");
            System.out.println("5. Ver estatísticas");
            System.out.println("6. Avançar para o próximo jogo");
            System.out.println("0. Fechar");
            System.out.print("Escolha uma opção: ");

            opcao = lerOpcao();

            switch (opcao) {
                case 1:
                    verPlantel();
                    break;
                case 2:
                    verClubes();
                    break;
                case 3:
                    escolherFormacaoEJogadores();
                    break;
                case 4:
                    // verCalendario();
                    break;
                case 5:
                    // verEstatisticas();
                    break;
                case 6:
                    // avancarJogo();
                    break;
                case 0:
                    System.out.println("A sair...");
                    break;
                default:
                    System.out.println("Opção inválida.");
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

    private void verPlantel() {
        System.out.print("Nome da equipa (ex: Benfica): ");
        String nome = scanner.nextLine().toLowerCase();  // Para garantir que o nome seja tratado corretamente

        // Caminho correto para os arquivos dentro de src/main/resources
        String caminho = "players/" + nome + ".json";
        jogadores = importer.importarJogadores(caminho);  // Agora 'jogadores' é um campo da classe

        System.out.println("\n--- Plantel da Equipa: " + nome + " ---");
        if (jogadores.length > 0) {
            for (int i = 0; i < jogadores.length; i++) {
                System.out.println((i + 1) + ". " + jogadores[i]);
            }
        } else {
            System.out.println(" Erro: ficheiro '" + caminho + "' não encontrado.");
        }
    }

    private void verClubes() {
        String[] clubes = importer.importarNomesClubes("clubs.json");

        System.out.println("\n--- Clubes Disponíveis ---");
        for (int i = 0; i < clubes.length; i++) {
            System.out.println((i + 1) + ". " + clubes[i]);
        }
    }

    private void escolherFormacaoEJogadores() {
        System.out.println("\n--- Escolher Formação e Jogadores Titulares ---");

        // Escolher a formação
        System.out.println("Escolha a formação (ex: 4-4-2): ");
        String formacao = scanner.nextLine();

        // Exibir os jogadores disponíveis
        System.out.println("\nEscolha os jogadores titulares:");
        for (int i = 0; i < jogadores.length; i++) {
            if (jogadores[i] != null) {
                System.out.println((i + 1) + ". " + jogadores[i].getName() + " - " + jogadores[i].getPosition());
            }
        }

        // Solicitar que o usuário selecione os jogadores titulares
        System.out.println("Escolha os jogadores titulares (separados por vírgula, ex: 1,2,3): ");
        String input = scanner.nextLine();
        String[] indices = input.split(",");

        // Criar a formação e associar os jogadores escolhidos
        System.out.println("Formação escolhida: " + formacao);
        System.out.println("Jogadores escolhidos:");
        for (String indice : indices) {
            int index = Integer.parseInt(indice.trim()) - 1;  // Ajustar para índice do array
            if (index >= 0 && index < jogadores.length && jogadores[index] != null) {
                System.out.println(jogadores[index].getName() + " - " + jogadores[index].getPosition());
                // Aqui, você pode adicionar lógica para salvar os jogadores selecionados para o jogo
            } else {
                System.out.println("Jogador inválido: " + indice);
            }
        }
    }
}*/

package main.ui;

import com.ppstudios.footballmanager.api.contracts.league.IStanding;
import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import io.Importer;
import main.league.Season;
import main.model.Club;
import main.model.Player;
import main.simulation.MatchSimulatorStrategy;

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
        season.setMatchSimulator(new MatchSimulatorStrategy());
        System.out.println("✅ Época criada com sucesso.");
    }

    private void adicionarClubes() {
        Club[] clubes = importer.importarClubes("clubs.json");


        int adicionados = 0;
        for (IClub club : clubes) {
            if (season.addClub(club)) {
                adicionados++;
            }
        }
        System.out.println("✅ " + adicionados + " clubes adicionados.");
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
            System.out.println("❌ Erro: ficheiro '" + caminho + "' não encontrado ou vazio.");
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
            System.out.println("✅ Calendário gerado com sucesso.");
        } else {
            System.out.println("⚠️ Primeiro cria a época e adiciona clubes.");
        }
    }

    private void simularRonda() {
        if (season != null) {
            season.simulateRound();
            System.out.println("✅ Ronda simulada.");
        }
    }

    private void simularEpocaCompleta() {
        if (season != null) {
            season.simulateSeason();
            System.out.println("✅ Época simulada.");
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
        System.out.println("\n🏆 Classificação:");
        for (IStanding s : tabela) {
            if (s != null && s.getTeam() != null) {
                System.out.println(s.getTeam().getClub().getName() + " - " + s.getPoints() + " pts");
            }
        }
    }
}
