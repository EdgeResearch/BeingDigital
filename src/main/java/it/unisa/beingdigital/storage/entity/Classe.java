package it.unisa.beingdigital.storage.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Questa classe rappresenta un Classe.
 * Per Classe si intende una specializzazione di team dove gli utenti sono degli studenti.
 */
@Entity
@Table(name = "classe")
@PrimaryKeyJoinColumn(name = "codice")
@Getter
@Setter
@ToString
public class Classe extends Team{
    @Column(name = "classe")
    private String classe;
    @Column(name = "scuola")
    private String scuola;

    public Classe(String codice, String nome, List<Utente> utenti, List<AmministratoreCittadini> amministratoreCittadini, String email, String classe, String scuola) {
        super(codice, nome, utenti, amministratoreCittadini, email);
        this.classe = classe;
        this.scuola = scuola;
    }

    public Classe() {
    }
}
