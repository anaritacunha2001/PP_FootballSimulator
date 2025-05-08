package ui;

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
            System.out.println("0. Sair");
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
            System.out.println("❌ Erro: ficheiro '" + caminho + "' não encontrado.");
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
}
