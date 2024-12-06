package it.unisa.beingdigital.storage.entity.util;

/**
 * Questa classe enum rappresenta un livello.
 * Il livello viene assegnato a un utente e alle meta-info.
 */

public enum Livello {

  BASE,
  INTERMEDIO,
  AVANZATO,
  MASTER,
  CITTADINANZA_DIGITALE;

  /**
   * Restituisce il livello successivo.
   *
   * @param livello il livello corrente.
   * @return il livello successivo o null se è già al massimo livello.
   */
  public static Livello getSuccessivo(Livello livello) {
    if (livello == null) {
      throw new IllegalArgumentException("Il livello non può essere nullo");
    }

    switch (livello) {
      case BASE:
        return INTERMEDIO;
      case INTERMEDIO:
        return AVANZATO;
      case AVANZATO:
        return MASTER;
      case MASTER:
        return CITTADINANZA_DIGITALE;
      case CITTADINANZA_DIGITALE:
        return null;
      default:
        throw new IllegalArgumentException("Livello non valido: " + livello);
    }
  }
}
