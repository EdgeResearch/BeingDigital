package it.unisa.beingdigital.control.presentazionerisorse;

import it.unisa.beingdigital.service.presentazionerisorse.PrelievoArgomentoService;
import it.unisa.beingdigital.storage.entity.util.Livello;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Questa classe rappresenta il controller per visualizzare le lezioni inerenti alla cittadinanza
 * digitale.
 */

@Controller
@RequestMapping("/lezioniCittadinanza")
public class LezioniCittadinanzaController {

  @Autowired
  private PrelievoArgomentoService prelievoArgomentoService;

  /**
   * Implementa il get per la visualizzazione delle lezioni di cittadinanza digitale.
   *
   * @param model Model da passare alla view.
   * @return Stringa rappresentante il path della view da rappresentare.
   */
  @GetMapping
  public String get(Model model) {
    model.addAttribute("lezioniPerMetaInfo",
        prelievoArgomentoService.getLezioniPerMetaInfoSortedByLivelloKeywordTitolo(
            Livello.CITTADINANZA_DIGITALE));
    model.addAttribute("tipo", "lezioniCittadinanza");
    return "presentazionerisorse/listaLezioni";
  }
}
