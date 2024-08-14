package it.unisa.beingdigital.storage.entity;

import java.util.List;

/**
 * Questa classe rappresenta un Gruppo.
 * Per Gruppo si intende una specializzazione di team dove gli utenti sono dei cittadini.
 */

public class Gruppo extends Team{
    private String città;
    public Gruppo(String nome, List<Utente> utenti, String email, String città) {
        super(nome, utenti, email);
        this.città = città;
    }
}
