package it.unisa.beingdigital.storage.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Questa classe rappresenta un Gruppo.
 * Per Gruppo si intende una specializzazione di team dove gli utenti sono dei cittadini.
 */

@Entity
@Table(name = "gruppo")
@PrimaryKeyJoinColumn(name = "codice")
@Getter
@Setter
@ToString
public class Gruppo extends Team{
    @Column(name = "città")
    private String città;
    public Gruppo(String codice, String nome, List<Utente> utenti, List<AmministratoreCittadini> amministratoreCittadini, String email, String città) {
        super(codice, nome, utenti, amministratoreCittadini, email);
        this.città = città;
    }

    public Gruppo() {
    }
}
