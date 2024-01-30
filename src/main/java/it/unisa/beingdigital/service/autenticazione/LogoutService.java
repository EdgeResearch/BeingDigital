package it.unisa.beingdigital.service.autenticazione;

import it.unisa.beingdigital.service.autenticazione.util.PersonaAutenticata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Questa classe rappresenta il service per il processo di loguot di una persona.
 */

@Service
public class LogoutService {

  @Autowired
  private PersonaAutenticata personaAutenticata;

  public void logout() {
    personaAutenticata.setPersona(null);
  }
}
