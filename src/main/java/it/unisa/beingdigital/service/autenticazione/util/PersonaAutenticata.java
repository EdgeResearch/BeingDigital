package it.unisa.beingdigital.service.autenticazione.util;

import it.unisa.beingdigital.storage.entity.Persona;
import java.util.Optional;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

/**
 * Questa classe rappresenta il bean di sessione che conserva l'istanza dell'utente autenticato.
 */

@Component
@SessionScope
@Setter
public class PersonaAutenticata {

  private Persona persona;

  public Optional<Persona> getPersona() {
    return Optional.ofNullable(persona);
  }
}
