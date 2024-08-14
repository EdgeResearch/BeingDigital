package it.unisa.beingdigital.storage.entity;

import java.util.List;

/**
 * Questa classe rappresenta un Classe.
 * Per Classe si intende una specializzazione di team dove gli utenti sono degli studenti.
 */

public class Classe extends Team{
    private String classe;
    private String scuola;
    public Classe(String nome, List<Utente> utenti, String email, String classe, String scuola) {
        super(nome, utenti, email);
        this.classe = classe;
        this.scuola = scuola;
    }
}
