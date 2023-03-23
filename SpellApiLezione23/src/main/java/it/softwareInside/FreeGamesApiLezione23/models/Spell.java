package it.softwareInside.FreeGamesApiLezione23.models;

import java.util.ArrayList;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data

@Entity
public class Spell {
    @Id
    private String id;
    
    @NotBlank(message = "Errore nome vuoto")
    private String name;
    private String incantation;
    private String effect;
    private boolean canBeVerbal;

    class Root{
        ArrayList<Spell> spells;
    }
}