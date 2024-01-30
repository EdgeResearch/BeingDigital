package it.unisa.beingdigital.control.presentazionerisorse.util;

import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 * Questa classe rappresenta una classe di utility per operazioni sugli array.
 */

@Component
public class Arrays {

  /**
   * Ordina casuale di un array.
   *
   * @param args Array da ordinare.
   * @return Lista ordinata casualmente.
   */
  public <T> List<T> shuffle(T... args) {
    List<T> result = java.util.Arrays.asList(args);
    Collections.shuffle(result);
    return result;
  }
}
