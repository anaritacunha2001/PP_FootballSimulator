package main.model;

import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.player.IPlayerPosition;
import com.ppstudios.footballmanager.api.contracts.player.PreferredFoot;

import java.io.IOException;
import java.time.LocalDate;

public class Player implements IPlayer {

    private String name;
    private LocalDate birthDate;
    private String nationality;
    private int number;
    private int passing;
    private String photo;
    private IPlayerPosition position;
    private PreferredFoot preferredFoot;
    private int shooting;
    private int speed;
    private int stamina;
    private float height;
    private float weight;
    private int reflexes;
    private int tackling;
    private int dribbling;

    public Player(String name, LocalDate birthDate, String nationality, int number, int passing, String photo,
                  IPlayerPosition position, PreferredFoot preferredFoot, int shooting, int speed, int stamina,
                  float height, float weight, int reflexes, int tackling, int dribbling) {
        this.name = name;
        this.birthDate = birthDate;
        this.nationality = nationality;
        this.number = number;
        this.passing = passing;
        this.photo = photo;
        this.position = position;
        this.preferredFoot = preferredFoot;
        this.shooting = shooting;
        this.speed = speed;
        this.stamina = stamina;
        this.height = height;
        this.weight = weight;
        this.reflexes = reflexes;
        this.tackling = tackling;
        this.dribbling = dribbling;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public LocalDate getBirthDate() {
        return birthDate;
    }

    @Override
    public int getAge() {
        return LocalDate.now().getYear() - birthDate.getYear();
    }

    @Override
    public String getNationality() {
        return nationality;
    }

    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public int getPassing() {
        return passing;
    }

    @Override
    public String getPhoto() {
        return photo;
    }

    @Override
    public IPlayerPosition getPosition() {
        return position;
    }

    @Override
    public PreferredFoot getPreferredFoot() {
        return preferredFoot;
    }

    @Override
    public int getShooting() {
        return shooting;
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public int getStamina() {
        return stamina;
    }

    @Override
    public float getHeight() {
        return height;
    }

    @Override
    public float getWeight() {
        return weight;
    }

    @Override
    public void setPosition(IPlayerPosition position) {
        this.position = position;
    }

    @Override
    public void exportToJson() throws IOException {
        String pos = "";
        if (position != null && position instanceof PlayerPosition) {
            pos = ((PlayerPosition) position).getName();
        }

        String json = "{ \"name\": \"" + name + "\", " +
                "\"birthDate\": \"" + birthDate + "\", " +
                "\"nationality\": \"" + nationality + "\", " +
                "\"number\": " + number + ", " +
                "\"passing\": " + passing + ", " +
                "\"photo\": \"" + photo + "\", " +
                "\"position\": \"" + pos + "\", " +
                "\"preferredFoot\": \"" + preferredFoot + "\", " +
                "\"shooting\": " + shooting + ", " +
                "\"speed\": " + speed + ", " +
                "\"stamina\": " + stamina + ", " +
                "\"height\": " + height + ", " +
                "\"weight\": " + weight + " }" +
                "\"reflexes\": " + reflexes + ", " +
                "\"tackling\": " + tackling + ", " +
                "\"dribbling\": " + dribbling + " }";

        System.out.println(json);
    }

    @Override
    public String toString() {
        String pos = "";
        if (position != null && position instanceof PlayerPosition) {
            pos = ((PlayerPosition) position).getName();
        }

        return name + " (" + nationality + ") #" + number + " - " + pos +
                " | Remate: " + shooting +
                " | Passe: " + passing +
                " | Pé: " + preferredFoot +
                " | Vel: " + speed +
                " | Ref: " + reflexes +
                " | Tack: " + tackling +
                " | Drib: " + dribbling;
    }
}
