package main.manager;

import com.ppstudios.footballmanager.api.contracts.team.IFormation;

public class Formation implements IFormation {

    @Override
    public int getTacticalAdvantage(IFormation iFormation) {
        return 0;
    }

    @Override
    public String getDisplayName() {
        return "";
    }
}


