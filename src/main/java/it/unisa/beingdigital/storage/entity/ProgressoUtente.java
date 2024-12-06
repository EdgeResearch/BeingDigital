package it.unisa.beingdigital.storage.entity;

import jakarta.persistence.*;
import it.unisa.beingdigital.storage.entity.util.Livello;

@Entity
public class ProgressoUtente {

    @EmbeddedId
    private ProgressoUtenteId id;

    @ManyToOne
    @MapsId("utenteId")
    @JoinColumn(name = "utente_id")
    private Utente utente;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Livello livello;

    public ProgressoUtente() {
    }

    public ProgressoUtente(ProgressoUtenteId id, Utente utente, Livello livello) {
        this.id = id;
        this.utente = utente;
        this.livello = livello;
    }

    public ProgressoUtenteId getId() {
        return id;
    }

    public void setId(ProgressoUtenteId id) {
        this.id = id;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public Livello getLivello() {
        return livello;
    }

    public void setLivello(Livello livello) {
        this.livello = livello;
    }
}