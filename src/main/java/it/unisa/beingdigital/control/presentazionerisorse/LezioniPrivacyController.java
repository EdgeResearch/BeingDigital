package it.unisa.beingdigital.control.presentazionerisorse;

import it.unisa.beingdigital.service.autenticazione.util.PersonaAutenticata;
import it.unisa.beingdigital.service.presentazionerisorse.PrelievoArgomentoService;
import it.unisa.beingdigital.storage.entity.util.Livello;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Questa classe rappresenta il controller per visualizzare i giochi inerenti alla privacy.
 */

@Controller
@RequestMapping("/utente/lezioniPrivacy")
public class LezioniPrivacyController {

  @Autowired
  private PrelievoArgomentoService prelievoArgomentoService;

  @Autowired
  private PersonaAutenticata personaAutenticata;

  /**
   * Implementa il get per la visualizzazione delle lezioni di privacy.
   *
   * @param sottoArgomento tipo di sottoArgomento.
   * @param model Model da passare alla view.
   * @return Stringa rappresentante il path della view da rappresentare.
   */
  @GetMapping
  public String get(@RequestParam("sottoArgomento") String sottoArgomento, Model model) {

    if ("Privacy".equalsIgnoreCase(sottoArgomento)) {
      model.addAttribute("lezioniPerMetaInfo", prelievoArgomentoService.getLezioniPerMetaInfoSortedByLivelloKeywordIdAndSottoArgomento(Livello.MASTER, "Privacy"));
      model.addAttribute("tipo", "lezioniPrivacy");
    } else if ("IntelligenzaArtificiale".equalsIgnoreCase(sottoArgomento)) {
      model.addAttribute("lezioniPerMetaInfo", prelievoArgomentoService.getLezioniPerMetaInfoSortedByLivelloKeywordIdAndSottoArgomento(Livello.MASTER, "IntelligenzaArtificiale"));
      model.addAttribute("tipo", "lezioniIntelligenza");
    }

    return "presentazionerisorse/listaLezioni";
  }
}
