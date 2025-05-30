/*
 * Nome: Ana Rita Dias Cunha
 * Número: 8210440
 * Turma: T1
 *
 * Nome: Carlos Barbosa
 * Número: 8210417
 * Turma: T3
 */

package main.model;

import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.player.IPlayerPosition;
import com.ppstudios.footballmanager.api.contracts.player.PreferredFoot;
import main.model.PlayerPosition;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Representa um jogador de futebol com atributos físicos e técnicos.
 */
public class Player implements IExtendedPlayer, Serializable {
    private static final long serialVersionUID = 1L;

    // Atributos principais do jogador
    private String name;
    private LocalDate birthDate;
    private String nationality;
    private int number;
    private String photo;
    private IPlayerPosition position;
    private PreferredFoot preferredFoot;

    // Atributos técnicos e físicos
    private int passing;
    private int shooting;
    private int speed;
    private int stamina;
    private float height;
    private float weight;
    private int reflexes;
    private int tackling;
    private int dribbling;

    /**
     * Construtor do jogador com todos os atributos.
     */
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

    @Override public String getName()            { return name; }
    @Override public LocalDate getBirthDate()    { return birthDate; }
    @Override public int getAge()                { return LocalDate.now().getYear() - birthDate.getYear(); }
    @Override public String getNationality()     { return nationality; }
    @Override public int getNumber()             { return number; }
    @Override public int getPassing()            { return passing; }
    @Override public String getPhoto()           { return photo; }
    @Override public IPlayerPosition getPosition() { return position; }
    @Override public PreferredFoot getPreferredFoot() { return preferredFoot; }
    @Override public int getShooting()           { return shooting; }
    @Override public int getSpeed()              { return speed; }
    @Override public int getStamina()            { return stamina; }
    @Override public float getHeight()           { return height; }
    @Override public float getWeight()           { return weight; }
    @Override public int getReflexes()           { return reflexes; }
    @Override public int getTackling()           { return tackling; }
    @Override public int getDribbling()          { return dribbling; }

    @Override
    public void setPosition(IPlayerPosition position) {
        this.position = position;
    }

    /**
     * Exporta os dados do jogador para formato JSON.
     */
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
                "\"weight\": " + weight + ", " +
                "\"reflexes\": " + reflexes + ", " +
                "\"tackling\": " + tackling + ", " +
                "\"dribbling\": " + dribbling + " }";

        System.out.println(json);
    }

    /**
     * Representa o jogador como uma string resumida com os seus atributos.
     */
    @Override
    public String toString() {
        String pos = "";
        if (position != null && position instanceof PlayerPosition) {
            pos = ((PlayerPosition) position).getName();
        }

        return name + " (" + nationality + ") #" + number + " - " + pos +
                " | Remate: " + shooting +
                " | Passe: " + passing +
                " | Vel: " + speed +
                " | Ref: " + reflexes +
                " | Tack: " + tackling +
                " | Drib: " + dribbling +
                " | Pé: " + preferredFoot;
    }
}
