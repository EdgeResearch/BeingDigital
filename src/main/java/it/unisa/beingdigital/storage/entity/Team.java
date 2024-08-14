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

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@ToString


public class Team{

    @Id
    @Column(nullable = false)
    private String nome;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Utente> utenti;

    @Column(nullable = false, length = 319, unique = true)
    private String email;

    protected Team(String nome, List<Utente> utenti, String email) {
        this.nome = nome;
        this.utenti = utenti;
        this.email = email;
    }
}
