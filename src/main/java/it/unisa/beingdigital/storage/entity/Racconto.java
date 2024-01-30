package it.unisa.beingdigital.storage.entity;

import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Questa classe rappresenta un racconto.
 * Un racconto è un tipo di argomento che può essere letto da un utente.
 */

@Entity
@NoArgsConstructor
@ToString(callSuper = true)
public class Racconto extends Argomento {

  public Racconto(String titolo, String corpo, byte[] copertina, MetaInfo metaInfo) {
    super(titolo, corpo, copertina, metaInfo);
  }
}
