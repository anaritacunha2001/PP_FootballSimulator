package ui;

import io.Importer;
import model.Player;

import java.util.Scanner;

public class Menu {

    private final Scanner scanner = new Scanner(System.in);
    private final Importer importer = new Importer();

    public void mostrarMenuPrincipal() {
        int opcao;
        do {
            System.out.println("\n==== SIMULADOR DE FUTEBOL - PPStudios ====");
            System.out.println("1. Ver plantel da equipa");
            System.out.println("2. Ver nomes dos clubes");
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
        Player[] jogadores = importer.importarJogadores(caminho);

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
}
