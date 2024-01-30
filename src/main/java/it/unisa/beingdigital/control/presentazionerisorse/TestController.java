package it.unisa.beingdigital.control.presentazionerisorse;

import it.unisa.beingdigital.control.presentazionerisorse.form.RispostaFormsWrapper;
import it.unisa.beingdigital.service.autenticazione.util.PersonaAutenticata;
import it.unisa.beingdigital.service.presentazionerisorse.PrelievoDomandaService;
import it.unisa.beingdigital.service.presentazionerisorse.TestService;
import it.unisa.beingdigital.storage.entity.Utente;
import it.unisa.beingdigital.storage.entity.util.Livello;
import jakarta.validation.Valid;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

/**
 * Questa classe rappresenta il controller per visualizzare del test.
 */

@Controller
@RequestMapping("/utente/test")
public class TestController {

  @Autowired
  private PrelievoDomandaService prelievoDomandaService;

  @Autowired
  private PersonaAutenticata personaAutenticata;

  @Autowired
  private TestService testService;

  /**
   * Implementa il get per la visualizzazione del test.
   *
   * @param model Model da passare alla view.
   * @return Stringa rappresentante il path della view da rappresentare.
   * @throws ResponseStatusException se l'utente autenticato è di livello MASTER
   */
  @GetMapping
  public String get(Model model) {
    Utente utente = (Utente) personaAutenticata.getPersona().get();

    if (utente.getLivello().equals(Livello.MASTER)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    model.addAttribute("domande",
        prelievoDomandaService.getDomandeRandom(utente.getLivello()));
    model.addAttribute("livello", utente.getLivello());
    return "presentazionerisorse/test";
  }

  /**
   * Implementa il post per la valutazione del test.
   *
   * @param rispostaFormsWrapper Id dell'account da eliminare
   * @return Stringa rappresentante il path della view da rappresentare.
   * @throws ResponseStatusException se il form non è valido o se un utente livello master prova
   *                                 a effettuare il test o il test non è andato a buon fine.
   */
  @PostMapping
  public String post(@Valid RispostaFormsWrapper rispostaFormsWrapper) {
    Utente utente = (Utente) personaAutenticata.getPersona().get();

    if (utente.getLivello().equals(Livello.MASTER)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    List<Map.Entry<Long, String>> risposte =
        rispostaFormsWrapper.getRispostaFormList().stream().map(
                rispostaForm -> (Map.Entry<Long, String>) new AbstractMap.SimpleEntry<>(
                    rispostaForm.getIdDomanda(),
                    rispostaForm.getRisposta()))
            .toList();

    try {
      if (testService.test(risposte, utente)) {
        return "redirect:/auth/areaPersonale?testCompletato=true";
      }
      return "redirect:/utente/risposte";
    } catch (IllegalArgumentException exception) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
  }
}
