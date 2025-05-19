package main.manager;

import com.ppstudios.footballmanager.api.contracts.team.IFormation;

public class Formation implements IFormation {

    private String displayName;

    public Formation(String displayName) {
        if (!displayName.equals("4-3-3") &&
                !displayName.equals("4-4-2") &&
                !displayName.equals("3-5-2")) {
            throw new IllegalArgumentException("Formação inválida: " + displayName);
        }
        this.displayName = displayName;
    }

    @Override
    public int getTacticalAdvantage(IFormation opponent) {
        if (opponent == null) {
            throw new IllegalArgumentException("Formação adversária não pode ser null.");
        }

        String other = opponent.getDisplayName();

        if (this.displayName.equals("4-3-3") && other.equals("4-4-2")) {
            return 1;
        } else if (this.displayName.equals("4-4-2") && other.equals("4-3-3")) {
            return -1;
        } else if (this.displayName.equals("3-5-2") && other.equals("4-4-2")) {
            return 1;
        } else if (this.displayName.equals("4-4-2") && other.equals("3-5-2")) {
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }
}

