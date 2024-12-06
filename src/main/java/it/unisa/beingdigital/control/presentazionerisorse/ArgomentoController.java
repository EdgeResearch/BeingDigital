package it.unisa.beingdigital.control.presentazionerisorse;

import it.unisa.beingdigital.service.presentazionerisorse.PrelievoArgomentoService;
import it.unisa.beingdigital.storage.entity.Argomento;
import java.util.List;
import java.util.Optional;
import it.unisa.beingdigital.storage.entity.Flashcard;
import it.unisa.beingdigital.storage.repository.FlashcardRepository;
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

  @Autowired
  private FlashcardRepository flashcardRepository;

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

    Optional<Argomento> nextArgomento = prelievoArgomentoService.getArgomento(id + 1);
    Optional<Argomento> lastArgomento = prelievoArgomentoService.getArgomento(id - 1);

    if (optional.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    Argomento argomento = optional.get();
    model.addAttribute("argomento", argomento);

    model.addAttribute("sottoargomento", argomento.getSottoArgomento());

    model.addAttribute("nextId", nextArgomento.isPresent() ? id + 1 : null);
    model.addAttribute("lastId", lastArgomento.isPresent() ? id - 1 : null);

    List<Flashcard> flashcards = flashcardRepository.findByArgomentoId(argomento.getId());

    model.addAttribute("flashCards", flashcards);

    return "presentazionerisorse/argomento";
  }
}
