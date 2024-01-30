package it.unisa.beingdigital.storage.entity;

import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Questa classe rappresenta una lezione.
 * Una lezione è un tipo di argomento che può essere studiato da un utente.
 */
@Entity
@NoArgsConstructor
@ToString(callSuper = true)
public class Lezione extends Argomento {

  public Lezione(String titolo, String corpo, byte[] copertina, MetaInfo metaInfo) {
    super(titolo, corpo, copertina, metaInfo);
  }
}
