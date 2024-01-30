package it.unisa.beingdigital.control.profilo;

import it.unisa.beingdigital.service.autenticazione.util.PersonaAutenticata;
import it.unisa.beingdigital.service.profilo.DatiUtentiService;
import it.unisa.beingdigital.storage.entity.Admin;
import it.unisa.beingdigital.storage.entity.Persona;
import it.unisa.beingdigital.storage.entity.Utente;
import it.unisa.beingdigital.storage.entity.util.Livello;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Questa classe rappresenta il controller per l'area personale.
 */

@Controller
@RequestMapping("/auth/areaPersonale")
public class AreaPersonaleController {

  @Autowired
  private PersonaAutenticata personaAutenticata;

  @Autowired
  private DatiUtentiService datiUtentiService;

  /**
   * Implementa il get per l'accesso all'area personale.
   *
   * @param model Model da passare alla view.
   * @return Stringa rappresentante il path della view da rappresentare.
   */
  @GetMapping
  public String get(Model model) {
    Persona persona = personaAutenticata.getPersona().get();

    if (persona instanceof Admin) {
      model.addAttribute("admin", persona);
      model.addAttribute("listaUtenti", datiUtentiService.getAllUtenti());
      model.addAttribute("percentualeBase",
          datiUtentiService.getPercentualeUtenti(Livello.BASE));
      model.addAttribute("percentualeIntermedio",
          datiUtentiService.getPercentualeUtenti(Livello.INTERMEDIO));
      model.addAttribute("percentualeAvanzato",
          datiUtentiService.getPercentualeUtenti(Livello.AVANZATO));
      model.addAttribute("percentualeMaster",
          datiUtentiService.getPercentualeUtenti(Livello.MASTER));
      return "profilo/admin";
    } else {
      model.addAttribute("utente", persona);
      model.addAttribute("percentuale", datiUtentiService.getPercentualeCompletamento(
          (Utente) persona));
      return "profilo/utente";
    }
  }
}
