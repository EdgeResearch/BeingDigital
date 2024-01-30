package it.unisa.beingdigital.control.profilo;

import it.unisa.beingdigital.service.profilo.PromozioneUtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

/**
 * Questa classe rappresenta il controller per la promozione di un utente.
 */

@Controller
@RequestMapping("/admin/promozioneUtente")
public class PromozioneUtenteController {

  @Autowired
  private PromozioneUtenteService promozioneUtenteService;

  /**
   * Implementa il post per la promozione di un utente ad admin.
   *
   * @param id Id dell'utente.
   * @return Stringa rappresentante il path della view da rappresentare.
   * @throws ResponseStatusException se la promozione non viene completata.
   */
  @PostMapping
  public String post(@RequestParam Long id) {
    if (!promozioneUtenteService.promozioneUtente(id)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
    return "redirect:/auth/areaPersonale";
  }
}
