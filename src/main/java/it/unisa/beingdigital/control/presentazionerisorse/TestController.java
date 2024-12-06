package it.unisa.beingdigital.control.presentazionerisorse;

import it.unisa.beingdigital.control.presentazionerisorse.form.RispostaFormsWrapper;
import it.unisa.beingdigital.service.autenticazione.util.PersonaAutenticata;
import it.unisa.beingdigital.service.presentazionerisorse.PrelievoDomandaService;
import it.unisa.beingdigital.service.presentazionerisorse.TestService;
import it.unisa.beingdigital.storage.entity.Argomento;
import it.unisa.beingdigital.storage.entity.ProgressoUtente;
import it.unisa.beingdigital.storage.entity.Utente;
import it.unisa.beingdigital.storage.entity.util.Livello;
import it.unisa.beingdigital.storage.repository.ArgomentoRepository;
import it.unisa.beingdigital.storage.repository.ProgressoUtenteRepository;
import jakarta.validation.Valid;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.AbstractMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

  @Autowired
  private ArgomentoRepository argomentoRepository;

  @Autowired
  private ProgressoUtenteRepository progressoUtenteRepository;

  /**
   * Implementa il get per la visualizzazione del test.
   *
   * @param model Model da passare alla view.
   * @return Stringa rappresentante il path della view da rappresentare.
   * @throws ResponseStatusException se l'utente autenticato è di livello MASTER
   */
  @GetMapping
  public String get(@RequestParam String sottoArgomento, Model model) {
    Utente utente = (Utente) personaAutenticata.getPersona().orElseThrow(() ->
            new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Utente non autenticato"));

    model.addAttribute("Utente", utente);

    ProgressoUtente progresso = utente.getProgressi().stream()
            .filter(p -> p.getId().getSottoArgomento().equalsIgnoreCase(sottoArgomento))
            .findFirst()
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Progresso non trovato"));

    if (progresso.getLivello().equals(Livello.MASTER)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "L'utente è già a livello MASTER");
    }

    model.addAttribute("domande", prelievoDomandaService.getDomandeRandom(sottoArgomento, progresso.getLivello()));
    model.addAttribute("livello", progresso.getLivello());
    model.addAttribute("sottoArgomento", sottoArgomento);

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
  public String post(@RequestParam String sottoArgomento, @Valid RispostaFormsWrapper rispostaFormsWrapper) {
    Utente utente = (Utente) personaAutenticata.getPersona().orElseThrow(() ->
            new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Utente non autenticato"));

    ProgressoUtente progresso = utente.getProgressi().stream()
            .filter(p -> p.getId().getSottoArgomento().equalsIgnoreCase(sottoArgomento))
            .findFirst()
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Progresso non trovato"));

    if (progresso.getLivello().equals(Livello.MASTER)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "L'utente è già a livello MASTER");
    }

    List<AbstractMap.SimpleEntry<Long, String>> risposte = rispostaFormsWrapper.getRispostaFormList().stream()
            .map(rispostaForm -> new AbstractMap.SimpleEntry<>(rispostaForm.getIdDomanda(), rispostaForm.getRisposta()))
            .toList();

    try {
      List<Argomento> argomenti = argomentoRepository.findBySottoArgomento(sottoArgomento);
      if (argomenti.isEmpty()) {
        throw new IllegalArgumentException("Argomento non trovato per il sotto-argomento: " + sottoArgomento);
      }

      Argomento argomento = argomenti.get(0);

      if (testService.test(risposte, utente, argomento)) {
        aggiornaLivello(progresso);
        progressoUtenteRepository.save(progresso);
        return "redirect:/auth/areaPersonale?testCompletato=true&sottoArgomento=" + sottoArgomento;
      } else {
        return "redirect:/utente/risposte?sottoArgomento=" + sottoArgomento + "&errore=Test non superato al 100%";
      }
    } catch (Exception e) {
      e.printStackTrace();
      String erroreMessage = URLEncoder.encode("Errore durante l'elaborazione del test: " + e.getMessage(), StandardCharsets.UTF_8);
      return "redirect:/utente/risposte?sottoArgomento=" + sottoArgomento + "&errore=" + erroreMessage;
    }
  }

  private void aggiornaLivello(ProgressoUtente progresso) {
    Livello[] livelli = Livello.values();
    int indiceAttuale = -1;
    for (int i = 0; i < livelli.length; i++) {
      if (livelli[i] == progresso.getLivello()) {
        indiceAttuale = i;
        break;
      }
    }

    if (indiceAttuale != -1 && indiceAttuale < livelli.length - 1) {
      progresso.setLivello(livelli[indiceAttuale + 1]);
    }
  }
}