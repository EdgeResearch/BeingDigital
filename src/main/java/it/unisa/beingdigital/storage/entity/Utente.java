package it.unisa.beingdigital.storage.entity;

import it.unisa.beingdigital.storage.entity.util.Livello;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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

  public Utente(String nome, String cognome, String email, String password,
                Livello livello) {
    super(nome, cognome, email, password);
    this.livello = livello;
  }
}
