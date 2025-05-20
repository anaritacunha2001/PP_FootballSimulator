/*package main.ui;

import main.ui.Menu;

public class Main {
    public static void main(String[] args) {
        //Menu menu = new Menu();
        //menu.mostrarMenuPrincipal();
    }
}

import main.io.Importer;
import main.model.Club;
import main.model.Player;

public class Main {
    public static void main(String[] args) {
        Importer importer = new Importer();

        // Importar jogadores de Benfica e Porto
        Player[] benfica = importer.importarJogadores("players/Benfica.json");
        Player[] porto = importer.importarJogadores("players/Porto.json");

        System.out.println("✅ Jogadores do Benfica: " + benfica.length);
        for (Player p : benfica) {
            System.out.println("- " + p.getName() + " (" + p.getPosition().getDescription() + ")");
        }

        System.out.println("\n✅ Jogadores do Porto: " + porto.length);
        for (Player p : porto) {
            System.out.println("- " + p.getName() + " (" + p.getPosition().getDescription() + ")");
        }

        // Assumir clubes importados
        Club[] clubes = importer.importarClubes("clubs.json", new Player[][]{benfica, porto});
        System.out.println("\n✅ Clubes importados: " + clubes.length);
        for (Club c : clubes) {
            System.out.println("🏟️ " + c.getName() + " → " + c.getPlayers().length + " jogadores");
        }

        // Testes terminados — comentar a seguir para voltar ao menu normal
        // Menu menu = new Menu();
        // menu.mostrarMenuPrincipal();
    }
}*/



// TESTE PLAYER

/*
package main.ui;

import main.model.Player;
import main.model.PlayerPosition;
import com.ppstudios.footballmanager.api.contracts.player.PreferredFoot;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        Player gk = new Player(
                "José",
                LocalDate.of(1990, 1, 1),
                "Portugal",
                1,
                50,
                "gk.png",
                PlayerPosition.GOALKEEPER,
                PreferredFoot.Right,
                30,
                40,
                70,
                1.90f,
                82.0f,
                95,
                40,
                20
        );


        Player cm = new Player(
                "Carlos",
                LocalDate.of(1995, 6, 10),
                "Brasil",
                8,
                78,
                "cm.png",
                PlayerPosition.CENTRAL_MIDFIELDER,
                PreferredFoot.Left,
                65,
                68,
                80,
                1.76f,
                70.0f,
                40,
                60,
                75
        );


        Player st = new Player(
                "Miguel",
                LocalDate.of(1998, 12, 25),
                "Espanha",
                9,
                60,
                "st.png",
                PlayerPosition.STRIKER,
                PreferredFoot.Right,
                85,
                85,
                77,
                1.80f,
                75.0f,
                30,
                45,
                90
        );

        // Imprimir jogadores
        System.out.println("Jogadores criados:\n");

        for (Player p : new Player[]{gk, cm, st}) {
            System.out.println(p);
            System.out.println("Posição: " + p.getPosition().getDescription());
            System.out.println("Idade: " + p.getAge());
            System.out.println("--");



        }
    }
}
*/


//TESTE FORMATION
/*
package main.ui;

import main.manager.Formation;

public class Main {
    public static void main(String[] args) {
        // Criar formações válidas
        Formation f1 = new Formation("4-3-3");
        Formation f2 = new Formation("4-4-2");
        Formation f3 = new Formation("3-5-2");

        // Mostrar nomes
        System.out.println("✅ Formações criadas:");
        System.out.println("- " + f1.getDisplayName());
        System.out.println("- " + f2.getDisplayName());
        System.out.println("- " + f3.getDisplayName());

        // Testar vantagem tática
        System.out.println("\n🎯 Vantagens táticas:");
        System.out.println("4-3-3 vs 4-4-2: " + f1.getTacticalAdvantage(f2));
        System.out.println("4-4-2 vs 3-5-2: " + f2.getTacticalAdvantage(f3));
        System.out.println("3-5-2 vs 4-3-3: " + f3.getTacticalAdvantage(f1));

        // Testar criação inválida (descomentar para testar exceção)
        // Formation invalida = new Formation("5-3-2");
    }
}
*/

//TESTE TEAM com CLUB
/*package main.ui;

import main.manager.Team;
import main.manager.Formation;
import main.model.Player;
import main.model.PlayerPosition;
import main.model.Club;
import com.ppstudios.footballmanager.api.contracts.player.PreferredFoot;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        Player gk = new Player("José", LocalDate.of(1990, 1, 1), "Portugal", 1, 50, "gk.png",
                PlayerPosition.GOALKEEPER, PreferredFoot.Right, 30, 40, 70, 1.90f, 82.0f, 95, 40, 20);

        Player cm = new Player("Carlos", LocalDate.of(1995, 6, 10), "Brasil", 8, 78, "cm.png",
                PlayerPosition.CENTRAL_MIDFIELDER, PreferredFoot.Left, 65, 68, 80, 1.76f, 70.0f, 40, 60, 75);

        Player st = new Player("Miguel", LocalDate.of(1998, 12, 25), "Espanha", 9, 60, "st.png",
                PlayerPosition.STRIKER, PreferredFoot.Right, 85, 85, 77, 1.80f, 75.0f, 30, 45, 90);

        IPlayer[] plantel = new IPlayer[]{gk, cm, st};


        Club club = new Club("FC Teste", "FCT", "Portugal", 1910, "Estádio Teste", "logo.png", plantel);

        Formation formation = new Formation("4-3-3");

        Team equipa = new Team(club, formation);
        equipa.addPlayer(gk);
        equipa.addPlayer(cm);
        equipa.addPlayer(st);

        System.out.println("Clube: " + equipa.getClub().getName());
        System.out.println("Formação: " + equipa.getFormation().getDisplayName());
        System.out.println("Força da equipa: " + equipa.getTeamStrength());

        System.out.println("Total STRIKERS: " + equipa.getPositionCount(PlayerPosition.STRIKER));
        System.out.println("Total MIDFIELDERS: " + equipa.getPositionCount(PlayerPosition.CENTRAL_MIDFIELDER));
        System.out.println("Total GOALKEEPERS: " + equipa.getPositionCount(PlayerPosition.GOALKEEPER));
    }
}*/


/*
package main.ui;

import main.manager.Formation;
import main.manager.Team;
import main.model.Club;
import main.model.Player;
import main.model.PlayerPosition;
import main.model.IExtendedPlayer;
import com.ppstudios.footballmanager.api.contracts.player.PreferredFoot;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.team.IPlayerSelector;
import com.ppstudios.footballmanager.api.contracts.player.IPlayerPosition;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        Player gk = new Player("José Guarda", LocalDate.of(1990, 1, 1), "Portugal", 1, 50, "gk.png",
                PlayerPosition.GOALKEEPER, PreferredFoot.Right, 30, 40, 70, 1.90f, 82.0f, 95, 40, 20);

        Player cm = new Player("Carlos Médio", LocalDate.of(1995, 6, 10), "Brasil", 8, 78, "cm.png",
                PlayerPosition.CENTRAL_MIDFIELDER, PreferredFoot.Left, 65, 68, 80, 1.76f, 70.0f, 40, 60, 75);

        Player st = new Player("Miguel Avançado", LocalDate.of(1998, 12, 25), "Espanha", 9, 60, "st.png",
                PlayerPosition.STRIKER, PreferredFoot.Right, 85, 85, 77, 1.80f, 75.0f, 30, 45, 90);

        IPlayer[] plantel = new IPlayer[11];
        plantel[0] = gk;
        plantel[1] = cm;
        plantel[2] = st;

        Club clube = new Club("FC Teste", "FCT", "Portugal", 1904, "Estádio Demo", "logo.png", plantel);

        System.out.println("Clube: " + clube.getName());
        System.out.println("Código: " + clube.getCode());
        System.out.println("País: " + clube.getCountry());
        System.out.println("Fundado: " + clube.getFoundedYear());
        System.out.println("Estádio: " + clube.getStadiumName());
        System.out.println("Logo: " + clube.getLogo());
        System.out.println("É válido: " + clube.isValid());

        System.out.println("Contagem de jogadores: " + clube.getPlayerCount());
        System.out.println("É jogador (cm)? " + clube.isPlayer(cm));
        clube.removePlayer(cm);
        System.out.println("Após remoção do cm: " + clube.getPlayerCount() + " jogadores");
        clube.addPlayer(cm);
        System.out.println("Após adicionar de volta: " + clube.getPlayerCount());

        IPlayerSelector selector = (club, pos) -> {
            for (IPlayer p : club.getPlayers()) {
                if (p != null && p.getPosition().getDescription().equals(pos.getDescription())) {
                    return p;
                }
            }
            return null;
        };
        IPlayer selecionado = clube.selectPlayer(selector, PlayerPosition.STRIKER);
        System.out.println("Selecionado (STRIKER): " + (selecionado != null ? selecionado.getName() : "nenhum"));

        Formation formacao = new Formation("4-4-2");
        Team equipa = new Team(clube, formacao);

        equipa.addPlayer(gk);
        equipa.addPlayer(cm);
        equipa.addPlayer(st);

        System.out.println("Formação usada: " + equipa.getFormation().getDisplayName());
        System.out.println("Força da equipa: " + equipa.getTeamStrength());
        System.out.println("Jogadores titulares: " + equipa.getPlayers().length);
        System.out.println("STRIKERS na equipa: " + equipa.getPositionCount(PlayerPosition.STRIKER));
    }
}*/

//TESTE PLAYERSELECTOR

/*
package main.ui;

import main.model.Club;
import main.model.Player;
import main.model.PlayerPosition;
import main.manager.Formation;
import main.manager.Team;
import main.strategy.PlayerSelector;
import com.ppstudios.footballmanager.api.contracts.player.PreferredFoot;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.player.IPlayerPosition;
import com.ppstudios.footballmanager.api.contracts.team.IPlayerSelector;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        Player gk = new Player("José Guarda", LocalDate.of(1990, 1, 1), "Portugal", 1, 50, "gk.png",
                PlayerPosition.GOALKEEPER, PreferredFoot.Right, 30, 40, 70, 1.90f, 82.0f, 95, 40, 20);

        Player cm = new Player("Carlos Médio", LocalDate.of(1995, 6, 10), "Brasil", 8, 78, "cm.png",
                PlayerPosition.CENTRAL_MIDFIELDER, PreferredFoot.Left, 65, 68, 80, 1.76f, 70.0f, 40, 60, 75);

        Player st = new Player("Miguel Avançado", LocalDate.of(1998, 12, 25), "Espanha", 9, 60, "st.png",
                PlayerPosition.STRIKER, PreferredFoot.Right, 85, 85, 77, 1.80f, 75.0f, 30, 45, 90);

        IPlayer[] plantel = new IPlayer[16];
        plantel[0] = gk;
        plantel[1] = cm;
        plantel[2] = st;

        Club clube = new Club("FC Teste", "FCT", "Portugal", 1904, "Estádio Demo", "logo.png", plantel);

        IPlayerSelector selector = new PlayerSelector();

        IPlayer escolhido = clube.selectPlayer(selector, PlayerPosition.STRIKER);

        System.out.println(" Jogador escolhido para STRIKER: " +
                (escolhido != null ? escolhido.getName() : "Nenhum encontrado"));
    }
}
*/

//TESTE PARA O SIMULADOR

package main.ui;

/*public class Main {
    public static void main(String[] args) {
        // Criar jogadores (mínimo para jogar)
        Player gk1 = new Player("GR A", LocalDate.of(1990, 1, 1), "Portugal", 1, 50, "foto.png", PlayerPosition.GOALKEEPER, PreferredFoot.Right, 30, 40, 70, 1.90f, 80f, 95, 20, 10);
        Player st1 = new Player("Avançado A", LocalDate.of(1998, 3, 10), "Brasil", 9, 60, "foto.png", PlayerPosition.STRIKER, PreferredFoot.Right, 85, 70, 75, 1.80f, 75f, 40, 50, 90);

        Player gk2 = new Player("GR B", LocalDate.of(1992, 5, 15), "Espanha", 1, 50, "foto.png", PlayerPosition.GOALKEEPER, PreferredFoot.Left, 35, 45, 72, 1.88f, 82f, 90, 25, 15);
        Player st2 = new Player("Avançado B", LocalDate.of(1997, 8, 20), "França", 11, 65, "foto.png", PlayerPosition.STRIKER, PreferredFoot.Left, 82, 73, 77, 1.83f, 76f, 30, 35, 88);

        // Criar clubes
        IPlayer[] playersA = new IPlayer[]{gk1, st1};
        IPlayer[] playersB = new IPlayer[]{gk2, st2};

        IClub clubA = new Club("FC A", "FCA", "Portugal", 1900, "Estádio A", "logoA.png", playersA);
        IClub clubB = new Club("FC B", "FCB", "Espanha", 1905, "Estádio B", "logoB.png", playersB);

        // Criar equipas
        Formation formation = new Formation("4-4-2");
        Team teamA = new Team(clubA, formation);
        teamA.addPlayer(gk1);
        teamA.addPlayer(st1);

        Team teamB = new Team(clubB, formation);
        teamB.addPlayer(gk2);
        teamB.addPlayer(st2);

        // Criar jogo
        Match match = new Match(clubA, clubB, 1);
        match.setTeam(teamA);
        match.setTeam(teamB);

        // Simular jogo
        MatchSimulatorStrategy simulator = new MatchSimulatorStrategy();
        simulator.simulate(match);

        // Mostrar resultado
        System.out.println("Resultado final:");
        System.out.println(clubA.getName() + " " + match.getTotalByEvent(GameEvent.class, clubA) +
                " - " + match.getTotalByEvent(GameEvent.class, clubB) + " " + clubB.getName());

        // Mostrar eventos
        System.out.println("\n Eventos:");
        for (var e : match.getEvents()) {
            System.out.println(e.getDescription());
        }
    }
}*/


public class Main {
    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.mostrarMenuPrincipal();
    }
}




