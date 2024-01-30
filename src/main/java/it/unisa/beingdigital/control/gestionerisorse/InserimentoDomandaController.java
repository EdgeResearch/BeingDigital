package it.unisa.beingdigital.control.gestionerisorse;

import it.unisa.beingdigital.control.gestionerisorse.form.DomandaForm;
import it.unisa.beingdigital.service.gestionerisorse.InserimentoRisorsaService;
import it.unisa.beingdigital.service.presentazionerisorse.PrelievoMetaInfoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

/**
 * Questa classe rappresenta un controller per l'inserimento di una Domanda.
 */

@Controller
@RequestMapping("/admin/inserimentoDomanda")
public class InserimentoDomandaController {

  @Autowired
  private InserimentoRisorsaService inserimentoRisorsaService;

  @Autowired
  private PrelievoMetaInfoService prelievoMetaInfoService;

  /**
   * Implementa il get per l'inserimento di una domanda.
   *
   * @param domandaForm form da inserire nel model.
   * @param model       model da passare alla view.
   * @return Stringa rappresentante il path della view da rappresentare.
   */
  @GetMapping
  public String get(@ModelAttribute DomandaForm domandaForm, Model model) {
    model.addAttribute("metaInfo",
        prelievoMetaInfoService.getMetaInfoSortedByLivelloKeywordWithoutCittadinanza());

    return "gestionerisorse/modificaDomanda";
  }

  /**
   * Implementa il post per l'inserimento di una domanda.
   *
   * @param domandaForm   form rappresentante l'argomento da inserire.
   * @param bindingResult risultato della validazione di domandaForm.
   * @return Stringa rappresentante il path della view da rappresentare.
   * @throws ResponseStatusException se domandaForm è mal formato o
   *                                 se l'inserimento non va a buon fine.
   */
  @PostMapping
  public String post(@ModelAttribute @Valid DomandaForm domandaForm,
                     BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    if (!inserimentoRisorsaService.inserimentoDomanda(domandaForm.getTesto(),
        domandaForm.getCorretta(), domandaForm.getSbagliata1(), domandaForm.getSbagliata2(),
        domandaForm.getSbagliata3(),
        domandaForm.getMetaInfoId())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    return "redirect:/admin/risorse";
  }
}
