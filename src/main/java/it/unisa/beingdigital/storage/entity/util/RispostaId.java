package it.unisa.beingdigital.storage.entity.util;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Questa classe rappresenta l'id di una risposta.
 * Crea un id composto da quello dell'utente, che l'ha selezionata,
 * e della domanda di appartenenza della risposta.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class RispostaId implements Serializable {

  private Long utente;

  private Long domanda;
}
