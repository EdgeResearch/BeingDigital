package it.unisa.beingdigital.storage.entity;

import jakarta.persistence.*;

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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Utente> utenti;

    @ManyToMany
    @JoinTable(
            name = "team_amministratore_cittadini",
            joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "amministratore_id")
    )
    private List<AmministratoreCittadini> amministratoriCittadini;

    @Column(nullable = false, length = 319)
    private String email;

    public Team(String codice, String nome, List<Utente> utenti, List<AmministratoreCittadini> amministratoreCittadini, String email) {
        this.codice = codice;
        this.nome = nome;
        this.utenti = utenti;
        this.amministratoriCittadini = amministratoreCittadini;
        this.email = email;
    }
}