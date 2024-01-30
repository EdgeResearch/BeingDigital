package it.unisa.beingdigital.control.gestionerisorse;

import it.unisa.beingdigital.control.gestionerisorse.form.ArgomentoForm;
import it.unisa.beingdigital.service.gestionerisorse.ModificaRisorsaService;
import it.unisa.beingdigital.service.presentazionerisorse.PrelievoArgomentoService;
import it.unisa.beingdigital.service.presentazionerisorse.PrelievoMetaInfoService;
import it.unisa.beingdigital.storage.entity.Argomento;
import jakarta.validation.Valid;
import java.io.IOException;
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
 * Questa classe rappresenta il controller per la modifica di un argomento.
 */

@Controller
@RequestMapping("/admin/modificaArgomento")
public class ModificaArgomentoController {

  @Autowired
  private ModificaRisorsaService modificaRisorsaService;

  @Autowired
  private PrelievoMetaInfoService prelievoMetaInfoService;

  @Autowired
  private PrelievoArgomentoService prelievoArgomentoService;

  /**
   * Implementa il get per la modifica di un argomento.
   *
   * @param id            id dell'argomento da modificare.
   * @param argomentoForm form da inserire nel model.
   * @param model         model da passare alla view.
   * @return Stringa rappresentante il path della view da rappresentare.
   * @throws ResponseStatusException se l'id è nullo o non è valido.
   */
  @GetMapping
  public String get(@RequestParam Long id,
                    @ModelAttribute ArgomentoForm argomentoForm, Model model) {
    Optional<Argomento> optional = prelievoArgomentoService.getArgomento(id);
    if (optional.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    Argomento argomento = optional.get();
    argomentoForm.setTitolo(argomento.getTitolo());
    argomentoForm.setCorpo(argomento.getCorpo());
    argomentoForm.setMetaInfoId(argomento.getMetaInfo().getId());

    model.addAttribute("metaInfo",
        prelievoMetaInfoService.getAllMetaInfoSortedByLivelloKeyword());

    return "gestionerisorse/modificaArgomento";
  }

  /**
   * Implementa il post per la modifica di un argomento.
   *
   * @param id            id dell'argomento da modificare.
   * @param argomentoForm form con i nuovi dati da modificare.
   * @param bindingResult risultato della validazione del form.
   * @return Stringa rappresentante il path della view da rappresentare.
   * @throws IOException             se c'è un errore mel prelievo della copertina dal form.
   * @throws ResponseStatusException se l'id è nullo o non è valido, se la modifica non è andata
   *                                 a buon fine.
   */
  @PostMapping
  public String post(@RequestParam Long id,
                     @ModelAttribute @Valid ArgomentoForm argomentoForm,
                     BindingResult bindingResult) throws IOException {
    if (bindingResult.hasErrors()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    byte[] copertina = null;
    if (argomentoForm.getCopertina() != null && !argomentoForm.getCopertina().isEmpty()) {
      copertina = argomentoForm.getCopertina().getBytes();
    }

    if (!modificaRisorsaService.modificaArgomento(id, argomentoForm.getTitolo(),
        argomentoForm.getCorpo(), copertina,
        argomentoForm.getMetaInfoId())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    return "redirect:/admin/risorse";
  }
}
