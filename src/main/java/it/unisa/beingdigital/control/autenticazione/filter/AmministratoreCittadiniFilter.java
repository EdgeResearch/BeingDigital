package it.unisa.beingdigital.control.autenticazione.filter;

import it.unisa.beingdigital.storage.entity.AmministratoreCittadini;
import it.unisa.beingdigital.storage.entity.Persona;
import org.springframework.stereotype.Component;

/**
 * Filtro per le pagine a cui può accedere solo un Amministratore di cittadini.
 */

@Component
public class AmministratoreCittadiniFilter extends AuthFilter {

    @Override
    protected boolean isClassValid(Persona persona) {
        return persona instanceof AmministratoreCittadini;
    }
}