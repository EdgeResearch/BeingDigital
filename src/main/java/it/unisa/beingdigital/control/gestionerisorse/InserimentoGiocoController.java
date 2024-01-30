package it.unisa.beingdigital.control.gestionerisorse;

import it.unisa.beingdigital.control.gestionerisorse.form.GiocoForm;
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
 * Questa classe rappresenta un controller per l'inserimento di un Gioco.
 */

@Controller
@RequestMapping("/admin/inserimentoGioco")
public class InserimentoGiocoController {

  @Autowired
  private InserimentoRisorsaService inserimentoRisorsaService;

  @Autowired
  private PrelievoMetaInfoService prelievoMetaInfoService;

  /**
   * Implementa il get per l'inserimento di un gioco.
   *
   * @param giocoForm form da inserire nel model.
   * @param model     model da passare alla view.
   * @return Stringa rappresentante il path della view da rappresentare.
   */
  @GetMapping
  public String get(@ModelAttribute GiocoForm giocoForm, Model model) {
    model.addAttribute("metaInfo",
        prelievoMetaInfoService.getMetaInfoSenzaGiocoSortedByLivelloKeyword(null));

    return "gestionerisorse/modificaGioco";
  }

  /**
   * Implementa il post per l'inserimento di un gioco.
   *
   * @param giocoForm     form rappresentante il gioco da inserire.
   * @param bindingResult risultato della validazione di giocoForm.
   * @return Stringa rappresentante il path della view da rappresentare.
   * @throws ResponseStatusException se giocoForm è mal formato o
   *                                 se l'inserimento non va a buon fine.
   */
  @PostMapping
  public String post(@ModelAttribute @Valid GiocoForm giocoForm,
                     BindingResult bindingResult, Model model) {
    if (bindingResult.hasErrors()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    if (!inserimentoRisorsaService.inserimentoGioco(giocoForm.getNome(), giocoForm.getPath(),
        giocoForm.getMetaInfoId())) {
      model.addAttribute("nomeEsistente", true);
      model.addAttribute("metaInfo",
          prelievoMetaInfoService.getMetaInfoSenzaGiocoSortedByLivelloKeyword(null));
      return "gestionerisorse/modificaGioco";
    }

    return "redirect:/admin/risorse";
  }
}
