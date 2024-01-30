package it.unisa.beingdigital.control.gestionerisorse;

import it.unisa.beingdigital.control.gestionerisorse.form.MetaInfoForm;
import it.unisa.beingdigital.service.gestionerisorse.ModificaRisorsaService;
import it.unisa.beingdigital.service.presentazionerisorse.PrelievoMetaInfoService;
import it.unisa.beingdigital.storage.entity.MetaInfo;
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
 * Questa classe rappresenta il controller per la modifica di una MetaInfo.
 */

@Controller
@RequestMapping("/admin/modificaMetaInfo")
public class ModificaMetaInfoController {

  @Autowired
  private ModificaRisorsaService modificaRisorsaService;

  @Autowired
  private PrelievoMetaInfoService prelievoMetaInfoService;

  /**
   * Implementa il get per la modifica di una metainfo.
   *
   * @param id           id della metainfo da modificare.
   * @param metaInfoForm form da inserire nel model.
   * @return Stringa rappresentante il path della view da rappresentare.
   * @throws ResponseStatusException se l'id è nullo o non valido.
   */
  @GetMapping
  public String get(@RequestParam Long id,
                    @ModelAttribute MetaInfoForm metaInfoForm) {
    Optional<MetaInfo> optional = prelievoMetaInfoService.getMetaInfo(id);
    if (optional.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    MetaInfo metaInfo = optional.get();
    metaInfoForm.setKeyword(metaInfo.getKeyword());
    metaInfoForm.setLivello(metaInfo.getLivello());
    return "gestionerisorse/modificaMetaInfo";
  }

  /**
   * Implementa il post per la modifica di una metainfo.
   *
   * @param id            id della metainfo da modificare.
   * @param metaInfoForm  form contenente i nuovi dati.
   * @param bindingResult risultato della validazione del form.
   * @param model         model da passare alla view.
   * @return Stringa rappresentante il path della view da rappresentare.
   * @throws IOException             se c'è un errore nel prelievo dell'icona dal form.
   * @throws ResponseStatusException se l'id risulta nullo, se il form non è ben formato.
   */
  @PostMapping
  public String post(@RequestParam Long id,
                     @ModelAttribute @Valid MetaInfoForm metaInfoForm,
                     BindingResult bindingResult, Model model) throws IOException {
    if (bindingResult.hasErrors()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    byte[] icona = null;
    if (metaInfoForm.getIcona() != null && !metaInfoForm.getIcona().isEmpty()) {
      icona = metaInfoForm.getIcona().getBytes();
    }

    if (!modificaRisorsaService.modificaMetaInfo(id, metaInfoForm.getKeyword(),
        metaInfoForm.getLivello(), icona)) {
      model.addAttribute("keywordEsistente", true);
      return "gestionerisorse/modificaMetaInfo";
    }

    return "redirect:/admin/risorse";
  }
}
