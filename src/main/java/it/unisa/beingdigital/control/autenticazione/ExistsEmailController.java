package it.unisa.beingdigital.control.autenticazione;

import it.unisa.beingdigital.service.autenticazione.ExistsEmailService;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Questa classe rappresenta il controller per il controllo dell'esistenza di un dato indirizzo
 * email nel database.
 */

@Controller
@RequestMapping("/existsEmail")
public class ExistsEmailController {

  @Autowired
  private ExistsEmailService existsEmailService;

  /**
   * Implementa il post per il controllo dell'esistenza dell'email.
   *
   * @param email Email da controllare.
   * @return ResponseEntity rappresentante la risposta da inviare al client
   * @throws org.springframework.web.server.ResponseStatusException se l'email è nulla
   */
  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> post(@RequestParam String email) {
    boolean result = existsEmailService.existsPersonaByEmail(email);

    return ResponseEntity.ok(Collections.singletonMap("result", result));
  }
}
