package it.unisa.beingdigital.control.autenticazione;

import it.unisa.beingdigital.service.autenticazione.CheckPasswordService;
import it.unisa.beingdigital.service.autenticazione.util.PersonaAutenticata;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Questa classe rappresenta il controller per il controllo della password della
 * persona autenticata.
 */

@Controller
@RequestMapping("/auth/checkPassword")
public class CheckPasswordController {

  @Autowired
  private PersonaAutenticata personaAutenticata;

  @Autowired
  private CheckPasswordService checkPasswordService;

  /**
   * Implementa il post per il controllo della password.
   *
   * @param password Password della persona autenticata.
   * @return ResponseEntity rappresentate la risposta da inviare al client
   * @throws org.springframework.web.server.ResponseStatusException se la password è nulla
   */
  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> post(@RequestParam String password) {
    boolean result =
        checkPasswordService.checkPassword(personaAutenticata.getPersona().get(), password);

    return ResponseEntity.ok(Collections.singletonMap("result", result));
  }
}
