package it.unisa.beingdigital.control.presentazionerisorse;

import it.unisa.beingdigital.service.presentazionerisorse.PrelievoArgomentoService;
import it.unisa.beingdigital.service.presentazionerisorse.PrelievoDomandaService;
import it.unisa.beingdigital.service.presentazionerisorse.PrelievoGiocoService;
import it.unisa.beingdigital.service.presentazionerisorse.PrelievoMetaInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Questa classe rappresenta il controller per visualizzare delle risorse.
 */


@Controller
@RequestMapping("/admin/risorse")
public class RisorseController {

  @Autowired
  private PrelievoArgomentoService prelievoArgomentoService;

  @Autowired
  private PrelievoMetaInfoService prelievoMetaInfoService;

  @Autowired
  private PrelievoGiocoService prelievoGiocoService;

  @Autowired
  private PrelievoDomandaService prelievoDomandaService;

  /**
   * Implementa il get per la visualizzazione della risorsa.
   *
   * @param model Model da passare alla view.
   * @return Stringa rappresentante il path della view da rappresentare.
   */
  @GetMapping
  public String get(Model model) {
    model.addAttribute("lezioni",
        prelievoArgomentoService.getAllLezioniSortedByLivelloKeywordTitolo());
    model.addAttribute("racconti",
        prelievoArgomentoService.getAllRaccontiSortedByLivelloKeywordTitolo());
    model.addAttribute("metainfo",
        prelievoMetaInfoService.getAllMetaInfoSortedByLivelloKeyword());
    model.addAttribute("giochi",
        prelievoGiocoService.getAllGiochiSortedByLivelloKeyword());
    model.addAttribute("domande",
        prelievoDomandaService.getAllDomandeSortedByLivelloKeywordTesto());
    return "presentazionerisorse/risorse";
  }
}

