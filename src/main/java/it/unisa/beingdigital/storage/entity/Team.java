package it.unisa.beingdigital.storage.entity;

import jakarta.persistence.*;
import java.util.Iterator;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


/**
 * Questa classe rappresenta un Team.
 * Per Team si intende un insieme di persone che compongono una squadra.
 */


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@ToString
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "team")
public class Team{

    @Id
    @Column(name = "codice")
    private String codice;

    @Column(nullable = false)
    private String nome;

    @ManyToMany
    @JoinTable(
            name = "team_utenti",
            joinColumns = @JoinColumn(name = "codice"),
            inverseJoinColumns = @JoinColumn(name = "utente_id")
    )
    private List<Utente> utenti;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "team_amministratore_cittadini",
            joinColumns = @JoinColumn(name = "codice"),
            inverseJoinColumns = @JoinColumn(name = "amministratore_id")
    )
    private List<AmministratoreCittadini> amministratoriCittadini;

    @Column(nullable = false, length = 319)
    private String email;

    public boolean espelliUtente(Long idUtente) {
        Iterator<Utente> iterator = utenti.iterator();
        while (iterator.hasNext()) {
            Utente utente = iterator.next();
            if (utente.getId().equals(idUtente)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    public boolean espelliAmministratore(Long idUtente) {
        Iterator<AmministratoreCittadini> iterator = amministratoriCittadini.iterator();
        while (iterator.hasNext()) {
            AmministratoreCittadini amministratoreCittadini = iterator.next();
            if (amministratoreCittadini.getId().equals(idUtente)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    public Team(String codice, String nome, List<Utente> utenti, List<AmministratoreCittadini> amministratoreCittadini, String email) {
        this.codice = codice;
        this.nome = nome;
        this.utenti = utenti;
        this.amministratoriCittadini = amministratoreCittadini;
        this.email = email;
    }
}
