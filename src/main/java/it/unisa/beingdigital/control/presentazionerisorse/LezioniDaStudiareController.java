package it.unisa.beingdigital.control.presentazionerisorse;

import it.unisa.beingdigital.service.autenticazione.util.PersonaAutenticata;
import it.unisa.beingdigital.service.presentazionerisorse.PrelievoArgomentoService;
import it.unisa.beingdigital.storage.entity.Utente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Questa classe rappresenta il controller per visualizzare le lezioni ancora da studiare.
 */

@Controller
@RequestMapping("/utente/lezioniDaStudiare")
public class LezioniDaStudiareController {

  @Autowired
  private PrelievoArgomentoService prelievoArgomentoService;

  @Autowired
  private PersonaAutenticata personaAutenticata;

  /**
   * Implementa il get per la visualizzazione delle lezioni da studiare da un utente.
   *
   * @param model Model da passare alla view.
   * @return Stringa rappresentante il path della view da rappresentare.
   */
  @GetMapping
  public String get(Model model) {
    Utente utente = (Utente) personaAutenticata.getPersona().get();

    model.addAttribute("lezioniPerMetaInfo",
        prelievoArgomentoService.getLezioniDaStudiarePerMetaInfoSortedByLivelloKeywordTitolo(
            utente));
    model.addAttribute("tipo", "lezioniDaStudiare");
    return "presentazionerisorse/listaLezioni";
  }
}
