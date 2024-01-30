package it.unisa.beingdigital.control.gestionerisorse;

import it.unisa.beingdigital.service.gestionerisorse.CancellazioneRisorsaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

/**
 * Questa classe rappresenta un controller per la cancellazione di una risorsa.
 */

@Controller
@RequestMapping("/admin/cancellazioneRisorsa")
public class CancellazioneRisorsaController {

  @Autowired
  private CancellazioneRisorsaService cancellazioneRisorsaService;

  /**
   * Implementa il post per la cancellazione di una risorsa.
   *
   * @param risorsa tipo di risorsa da cancellare.
   * @param id      id della risorsa.
   * @return Stringa rappresentante il path della view da rappresentare.
   * @throws ResponseStatusException se risorsa o id sono nulli, oppure se la
   *                                 cancellazione non è andata a buon fine.
   */
  @PostMapping
  public String post(@RequestParam String risorsa, @RequestParam Long id) {
    boolean result = switch (risorsa) {
      case "metainfo" -> cancellazioneRisorsaService.cancellazioneMetaInfo(id);
      case "gioco" -> cancellazioneRisorsaService.cancellazioneGioco(id);
      case "argomento" -> cancellazioneRisorsaService.cancellazioneArgomento(id);
      case "domanda" -> cancellazioneRisorsaService.cancellazioneDomanda(id);
      default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    };

    if (!result) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
    return "redirect:/admin/risorse";
  }
}
