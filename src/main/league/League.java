package main.league;

import com.ppstudios.footballmanager.api.contracts.league.ILeague;
import com.ppstudios.footballmanager.api.contracts.league.ISeason;
import com.ppstudios.footballmanager.api.contracts.data.IExporter;

public class League implements ILeague {

    private final String name;
    private final ISeason[] seasons;
    private int seasonCount;

    public League(String name, int maxSeasons) {
        if (name == null || maxSeasons <= 0) {
            throw new IllegalArgumentException("Nome ou número máximo de épocas inválido.");
        }
        this.name = name;
        this.seasons = new ISeason[maxSeasons];
        this.seasonCount = 0;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ISeason[] getSeasons() {
        ISeason[] result = new ISeason[seasonCount];
        System.arraycopy(seasons, 0, result, 0, seasonCount);
        return result;
    }

    @Override
    public boolean createSeason(ISeason season) {
        if (season == null) return false;
        if (seasonCount >= seasons.length) return false;

        seasons[seasonCount++] = season;
        return true;
    }

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

    @Override
    public ISeason getSeason(int index) {
        if (index < 0 || index >= seasonCount) return null;
        return seasons[index];
    }

    @Override
    public void exportToJson() {
        System.out.println("{ \"league\": \"" + name + "\", \"seasons\": " + seasonCount + " }");
    }
}
