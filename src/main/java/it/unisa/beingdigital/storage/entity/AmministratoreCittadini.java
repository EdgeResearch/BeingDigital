package it.unisa.beingdigital.storage.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Lob;
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

    @Lob
    private byte[] fotoprofilo;

    public AmministratoreCittadini(String nome, String cognome, String email, String password, byte[] fotoprofilo, String biografia) {
        super(nome, cognome, email, password, fotoprofilo, biografia);
        this.fotoprofilo = fotoprofilo;
    }

    @ManyToMany(mappedBy = "amministratoriCittadini", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Team> teams;

    public List<Team> getTeams() {
        return teams;
    }

    public byte[] getProfileImage() {
        return fotoprofilo;
    }

    public void setProfileImage(byte[] fotoprofilo) {
        this.fotoprofilo = fotoprofilo;
    }
}
