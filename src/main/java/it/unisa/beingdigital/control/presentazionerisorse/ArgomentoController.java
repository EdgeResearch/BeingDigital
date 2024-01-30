package it.unisa.beingdigital.control.presentazionerisorse;

import it.unisa.beingdigital.service.presentazionerisorse.PrelievoArgomentoService;
import it.unisa.beingdigital.storage.entity.Argomento;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

/**
 * Questa classe rappresenta il controller per il prelievo argomenti.
 */

@Controller
@RequestMapping("/argomento")
public class ArgomentoController {

  @Autowired
  private PrelievoArgomentoService prelievoArgomentoService;

  /**
   * Implementa il get per la visualizzazione di un argomento.
   *
   * @param id    Id dell'argomento.
   * @param model Model da passare alla view.
   * @return Stringa rappresentante il path della view da rappresentare.
   * @throws ResponseStatusException se l'id è nullo o invalido.
   */
  @GetMapping
  public String get(@RequestParam Long id, Model model) {
    Optional<Argomento> optional = prelievoArgomentoService.getArgomento(id);
    if (optional.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    model.addAttribute("argomento", optional.get());
    return "presentazionerisorse/argomento";
  }
}
