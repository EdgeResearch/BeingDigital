package it.unisa.beingdigital.control.profilo;

import it.unisa.beingdigital.service.profilo.CancellazioneAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

/**
 * Questa classe rappresenta il controller per la cancellazione di un account utente
 * da parte dell'amministratore.
 */

@Controller
@RequestMapping("/admin/cancellazioneUtente")
public class CancellazioneUtenteController {

  @Autowired
  private CancellazioneAccountService cancellazioneAccountService;

  /**
   * Implementa il post per l'eliminazione dell'account con quell'id.
   *
   * @param id Id dell'account da eliminare
   * @return Stringa rappresentante il path della view da rappresentare.
   * @throws ResponseStatusException se l'eliminazione dell'account non viene completata.
   */
  @PostMapping
  public String post(@RequestParam Long id) {
    if (!cancellazioneAccountService.cancellazioneUtente(id)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
    return "redirect:/auth/areaPersonale";
  }
}
