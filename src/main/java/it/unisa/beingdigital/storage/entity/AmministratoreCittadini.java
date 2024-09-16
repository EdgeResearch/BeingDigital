package it.unisa.beingdigital.storage.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * Questa classe rappresenta un amministratore di cittadini.
 * Un amministratore può modificare le risorse del sito e creare Teams
 * I teams sono gruppi o classi di cittadini.
 */
@Entity
@NoArgsConstructor
@ToString(callSuper = true)
public class AmministratoreCittadini extends Persona {

    public AmministratoreCittadini(String nome, String cognome, String email, String password) {
        super(nome, cognome, email, password);
    }

    @ManyToMany(mappedBy = "amministratoriCittadini")
    private List<Team> teams;

    public List<Team> getTeams() {
        return teams;
    }
}