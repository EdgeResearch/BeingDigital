package it.unisa.beingdigital.control.profilo;

import it.unisa.beingdigital.service.autenticazione.util.PersonaAutenticata;
import it.unisa.beingdigital.service.profilo.CancellazioneAccountService;
import it.unisa.beingdigital.storage.entity.Admin;
import it.unisa.beingdigital.storage.entity.Persona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Questa classe rappresenta il controller per la cancellazione di un account.
 */

@Controller
@RequestMapping("/auth/cancellazioneAccount")
public class CancellazioneAccountController {

  @Autowired
  private CancellazioneAccountService cancellazioneAccountService;

  @Autowired
  private PersonaAutenticata personaAutenticata;

  /**
   * Implementa il post per l'eliminazione dell'account della persona autenticata.
   *
   * @return Stringa rappresentante il path della view da rappresentare.
   */
  @PostMapping
  public String post() {
    Persona persona = personaAutenticata.getPersona().get();

    if (persona instanceof Admin) {
      cancellazioneAccountService.cancellazioneAdmin(persona.getId());
    } else {
      cancellazioneAccountService.cancellazioneUtente(persona.getId());
    }

    return "redirect:/logout";
  }
}
