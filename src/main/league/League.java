/*
 * Nome: Ana Cunha
 * Número: 8210440
 * Turma: T1
 *
 * Nome: Carlos Barbosa
 * Número: 8210417
 * Turma: T3
 */

package main.league;

import com.ppstudios.footballmanager.api.contracts.league.ILeague;
import com.ppstudios.footballmanager.api.contracts.league.ISeason;

/**
 * Classe que representa uma liga, gerindo as temporadas associadas.
 */
public class League implements ILeague {

    private final String name;
    private final ISeason[] seasons;
    private int seasonCount;

    /**
     * Construtor da classe League.
     *
     * @param name Nome da liga.
     * @param maxSeasons Número máximo de temporadas permitidas.
     * @throws IllegalArgumentException se o nome for nulo ou o número máximo de temporadas for inválido.
     */
    public League(String name, int maxSeasons) {
        if (name == null || maxSeasons <= 0) {
            throw new IllegalArgumentException("Nome ou número máximo de épocas inválido.");
        }
        this.name = name;
        this.seasons = new ISeason[maxSeasons];
        this.seasonCount = 0;
    }

    /**
     * Obtém o nome da liga.
     *
     * @return nome da liga.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Obtém todas as temporadas adicionadas à liga.
     *
     * @return array com temporadas ativas.
     */
    @Override
    public ISeason[] getSeasons() {
        ISeason[] result = new ISeason[seasonCount];
        System.arraycopy(seasons, 0, result, 0, seasonCount);
        return result;
    }

    /**
     * Adiciona uma nova temporada à liga.
     *
     * @param season temporada a adicionar.
     * @return true se adicionada com sucesso, false caso contrário.
     */
    @Override
    public boolean createSeason(ISeason season) {
        if (season == null) return false;
        if (seasonCount >= seasons.length) return false;

        seasons[seasonCount++] = season;
        return true;
    }

    /**
     * Remove uma temporada específica da liga pelo índice.
     *
     * @param index índice da temporada a remover.
     * @return a temporada removida, ou null se índice inválido.
     */
    @Override
    public ISeason removeSeason(int index) {
        if (index < 0 || index >= seasonCount) return null;

        ISeason removed = seasons[index];
        for (int i = index; i < seasonCount - 1; i++) {
            seasons[i] = seasons[i + 1];
        }
        seasons[--seasonCount] = null;
        return removed;
    }

    /**
     * Obtém uma temporada específica pelo índice.
     *
     * @param index índice da temporada desejada.
     * @return a temporada correspondente, ou null se índice inválido.
     */
    @Override
    public ISeason getSeason(int index) {
        if (index < 0 || index >= seasonCount) return null;
        return seasons[index];
    }

    /**
     * Exporta a liga para um formato JSON.
     * (Aqui, apenas um exemplo simples é implementado com impressão no terminal.)
     */
    @Override
    public void exportToJson() {
        System.out.println("{ \"league\": \"" + name + "\", \"seasons\": " + seasonCount + " }");
    }
}