package it.unisa.beingdigital.storage.entity;

import it.unisa.beingdigital.storage.entity.util.Livello;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Questa classe rappresenta un utente.
 * Un utente può usufruire di tutte le risorse del sito.
 */

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class Utente extends Persona {

  @Enumerated(EnumType.STRING)
  private Livello livello;

  public Utente(String nome, String cognome, String email, String password, Livello livello, byte[] fotoprofilo) {
    super(nome, cognome, email, password, fotoprofilo);
    this.livello = livello;
  }

  @ManyToMany
  @JoinTable(
          name = "team_utenti",
          joinColumns = @JoinColumn(name = "utente_id"),
          inverseJoinColumns = @JoinColumn(name = "codice")
  )
  private List<Team> teams;

  public List<Team> getTeams() {
    return teams;
  }
}
