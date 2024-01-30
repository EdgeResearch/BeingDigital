package it.unisa.beingdigital.storage.entity;

import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Questa classe rappresenta un admin.
 * Un admin può modificare le risorse del sito e promuovere un utente.
 */
@Entity
@NoArgsConstructor
@ToString(callSuper = true)
public class Admin extends Persona {

  public Admin(String nome, String cognome, String email, String password) {
    super(nome, cognome, email, password);
  }
}
