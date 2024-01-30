package it.unisa.beingdigital.control.gestionerisorse;

import it.unisa.beingdigital.control.gestionerisorse.form.DomandaForm;
import it.unisa.beingdigital.service.gestionerisorse.ModificaRisorsaService;
import it.unisa.beingdigital.service.presentazionerisorse.PrelievoDomandaService;
import it.unisa.beingdigital.service.presentazionerisorse.PrelievoMetaInfoService;
import it.unisa.beingdigital.storage.entity.Domanda;
import jakarta.validation.Valid;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

/**
 * Questa classe rappresenta il controller per la modifica di una Domanda.
 */

@Controller
@RequestMapping("/admin/modificaDomanda")
public class ModificaDomandaController {

  @Autowired
  private ModificaRisorsaService modificaRisorsaService;

  @Autowired
  private PrelievoDomandaService prelievoDomandaService;

  @Autowired
  private PrelievoMetaInfoService prelievoMetaInfoService;

  /**
   * Implementa il get per la modifica di una domanda.
   *
   * @param id          id della domanda da modificare.
   * @param domandaForm form da inserire nel model.
   * @param model       model da passare alla view.
   * @return Stringa rappresentante il path della view da rappresentare.
   * @throws ResponseStatusException se l'id è nullo o non valido.
   */
  @GetMapping
  public String get(@RequestParam Long id, @ModelAttribute DomandaForm domandaForm,
                    Model model) {
    Optional<Domanda> optional = prelievoDomandaService.getDomanda(id);
    if (optional.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    Domanda domanda = optional.get();
    domandaForm.setTesto(domanda.getTesto());
    domandaForm.setCorretta(domanda.getCorretta());
    domandaForm.setSbagliata1(domanda.getSbagliata1());
    domandaForm.setSbagliata2(domanda.getSbagliata2());
    domandaForm.setSbagliata3(domanda.getSbagliata3());
    domandaForm.setMetaInfoId(domanda.getMetaInfo().getId());

    model.addAttribute("metaInfo",
        prelievoMetaInfoService.getMetaInfoSortedByLivelloKeywordWithoutCittadinanza());

    return "gestionerisorse/modificaDomanda";
  }

  /**
   * Implementa il post per la modifica di una domanda.
   *
   * @param id            id della domanda da modificare.
   * @param domandaForm   form contenente i nuovi dati.
   * @param bindingResult risultato della validazione del form.
   * @return Stringa rappresentante il path della view da rappresentare.
   * @throws ResponseStatusException se l'id è nullo o non valido, se il form è malformato
   */
  @PostMapping
  public String post(@RequestParam Long id,
                     @ModelAttribute @Valid DomandaForm domandaForm,
                     BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    if (!modificaRisorsaService.modificaDomanda(id, domandaForm.getTesto(),
        domandaForm.getCorretta(), domandaForm.getSbagliata1(), domandaForm.getSbagliata2(),
        domandaForm.getSbagliata3(),
        domandaForm.getMetaInfoId())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    return "redirect:/admin/risorse";
  }
}
