package it.unisa.beingdigital.control.gestionerisorse;

import it.unisa.beingdigital.control.gestionerisorse.form.ArgomentoForm;
import it.unisa.beingdigital.service.gestionerisorse.InserimentoRisorsaService;
import it.unisa.beingdigital.service.presentazionerisorse.PrelievoMetaInfoService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

/**
 * Questa classe rappresenta un controller per l'inserimento di un argomento.
 */

@Controller
@RequestMapping("/admin/inserimentoArgomento")
public class InserimentoArgomentoController {

  @Autowired
  private InserimentoRisorsaService inserimentoRisorsaService;

  @Autowired
  private PrelievoMetaInfoService prelievoMetaInfoService;

  /**
   * Implementa il get per l'inserimento di un argomento.
   *
   * @param argomentoForm form da inserire nel model.
   * @param model         model da passare alla view.
   * @return Stringa rappresentante il path della view da rappresentare.
   */
  @GetMapping
  public String get(@ModelAttribute ArgomentoForm argomentoForm, Model model) {
    model.addAttribute("metaInfo",
        prelievoMetaInfoService.getAllMetaInfoSortedByLivelloKeyword());

    return "gestionerisorse/modificaArgomento";
  }

  /**
   * Implementa il post per l'inserimento di un argomento.
   *
   * @param tipo          tipo di argomento da inserire
   * @param argomentoForm form rappresentante l'argomento da inserire.
   * @param bindingResult risultato della validazione di argomentoForm.
   * @return Stringa rappresentante il path della view da rappresentare.
   * @throws IOException             se c'è un errore mel prelievo della copertina dal form.
   * @throws ResponseStatusException se tipo è nullo o se argomentoForm è mal formato,
   *                                 se la copertina è vuota, se l'inserimento non va a buon fine.
   */
  @PostMapping
  public String post(@RequestParam String tipo,
                     @ModelAttribute @Valid ArgomentoForm argomentoForm,
                     BindingResult bindingResult) throws IOException {
    if (bindingResult.hasErrors()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    if (argomentoForm.getCopertina() == null || argomentoForm.getCopertina().isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    boolean result = switch (tipo) {
      case "lezione" -> inserimentoRisorsaService.inserimentoLezione(argomentoForm.getTitolo(),
          argomentoForm.getCorpo(), argomentoForm.getCopertina().getBytes(),
          argomentoForm.getMetaInfoId());
      case "racconto" -> inserimentoRisorsaService.inserimentoRacconto(argomentoForm.getTitolo(),
          argomentoForm.getCorpo(), argomentoForm.getCopertina().getBytes(),
          argomentoForm.getMetaInfoId());
      default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    };

    if (!result) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
    return "redirect:/admin/risorse";
  }
}
