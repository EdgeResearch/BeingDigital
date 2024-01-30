package it.unisa.beingdigital.control.gestionerisorse;

import it.unisa.beingdigital.control.gestionerisorse.form.MetaInfoForm;
import it.unisa.beingdigital.service.gestionerisorse.InserimentoRisorsaService;
import jakarta.validation.Valid;
import java.io.IOException;
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
 * Questa classe rappresenta un controller per l'inserimento di una MetaInfo.
 */

@Controller
@RequestMapping("/admin/inserimentoMetaInfo")
public class InserimentoMetaInfoController {

  @Autowired
  private InserimentoRisorsaService inserimentoRisorsaService;

  @GetMapping
  public String get(@ModelAttribute MetaInfoForm metaInfoForm) {
    return "gestionerisorse/modificaMetaInfo";
  }

  /**
   * Implementa il post per l'inserimento di una metainfo.
   *
   * @param metaInfoForm  form rappresentante la metainfo da inserire.
   * @param bindingResult risultato della validazione di metaInfoForm.
   * @param model         model da passare alla view
   * @return Stringa rappresentante il path della view da rappresentare.
   * @throws IOException             se c'è un errore nel prelievo dell'icona dal form.
   * @throws ResponseStatusException se metaInfoForm è mal formato, l'icona è nulla o
   *                                 l'inserimento non va a buon fine.
   */
  @PostMapping
  public String post(@ModelAttribute @Valid MetaInfoForm metaInfoForm,
                     BindingResult bindingResult, Model model) throws IOException {
    if (bindingResult.hasErrors()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    if (metaInfoForm.getIcona() == null || metaInfoForm.getIcona().isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    if (!inserimentoRisorsaService.inserimentoMetaInfo(metaInfoForm.getKeyword(),
        metaInfoForm.getLivello(), metaInfoForm.getIcona().getBytes())) {
      model.addAttribute("keywordEsistente", true);
      return "gestionerisorse/modificaMetaInfo";
    }

    return "redirect:/admin/risorse";
  }
}
