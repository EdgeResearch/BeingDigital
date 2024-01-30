package it.unisa.beingdigital.control.autenticazione.filter;

import it.unisa.beingdigital.storage.entity.Persona;
import it.unisa.beingdigital.storage.entity.Utente;
import org.springframework.stereotype.Component;

/**
 * Filtro per le pagine a cui può accedere solo un utente.
 */

@Component
public class UtenteFilter extends AuthFilter {

  @Override
  protected boolean isClassValid(Persona persona) {
    return persona instanceof Utente;
  }
}
