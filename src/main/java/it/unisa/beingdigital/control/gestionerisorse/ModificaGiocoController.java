package it.unisa.beingdigital.control.gestionerisorse;

import it.unisa.beingdigital.control.gestionerisorse.form.GiocoForm;
import it.unisa.beingdigital.service.gestionerisorse.ModificaRisorsaService;
import it.unisa.beingdigital.service.presentazionerisorse.PrelievoGiocoService;
import it.unisa.beingdigital.service.presentazionerisorse.PrelievoMetaInfoService;
import it.unisa.beingdigital.storage.entity.Gioco;
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
 * Questa classe rappresenta il controller per la modifica di un Gioco.
 */

@Controller
@RequestMapping("/admin/modificaGioco")
public class ModificaGiocoController {

  @Autowired
  private ModificaRisorsaService modificaRisorsaService;

  @Autowired
  private PrelievoMetaInfoService prelievoMetaInfoService;

  @Autowired
  private PrelievoGiocoService prelievoGiocoService;

  /**
   * Implementa il get per la modifica di un gioco.
   *
   * @param id        id del gioco da modificare.
   * @param giocoForm form da inserire nel model.
   * @param model     model da passare alla view.
   * @return Stringa rappresentante il path della view da rappresentare.
   * @throws ResponseStatusException se l'id è nullo o non valido.
   */
  @GetMapping
  public String get(@RequestParam Long id, @ModelAttribute GiocoForm giocoForm,
                    Model model) {
    Optional<Gioco> optional = prelievoGiocoService.getGioco(id);
    if (optional.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    Gioco gioco = optional.get();
    giocoForm.setNome(gioco.getNome());
    giocoForm.setPath(gioco.getPath());
    giocoForm.setMetaInfoId(gioco.getMetaInfo().getId());

    model.addAttribute("metaInfo",
        prelievoMetaInfoService.getMetaInfoSenzaGiocoSortedByLivelloKeyword(
            giocoForm.getMetaInfoId()));

    return "gestionerisorse/modificaGioco";
  }


  /**
   * Implementa il post per la modifica di un gioco.
   *
   * @param id            id del gioco da modificare.
   * @param giocoForm     form con i nuovi dati da inserire.
   * @param bindingResult risultato della validazione del form.
   * @param model         model da passare alla view.
   * @return Stringa rappresentante il path della view da rappresentare.
   * @throws ResponseStatusException se l'id è nullo o invalido, se il form è malformato.
   */
  @PostMapping
  public String post(@RequestParam Long id,
                     @ModelAttribute @Valid GiocoForm giocoForm,
                     BindingResult bindingResult, Model model) {
    if (bindingResult.hasErrors()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    if (!modificaRisorsaService.modificaGioco(id, giocoForm.getNome(), giocoForm.getPath(),
        giocoForm.getMetaInfoId())) {
      model.addAttribute("nomeEsistente", true);
      model.addAttribute("metaInfo",
          prelievoMetaInfoService.getMetaInfoSenzaGiocoSortedByLivelloKeyword(
              giocoForm.getMetaInfoId()));
      return "gestionerisorse/modificaGioco";
    }

    return "redirect:/admin/risorse";
  }
}
