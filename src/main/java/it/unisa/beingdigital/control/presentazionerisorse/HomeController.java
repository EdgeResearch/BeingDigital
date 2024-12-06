package it.unisa.beingdigital.control.presentazionerisorse;

import it.unisa.beingdigital.service.autenticazione.util.PersonaAutenticata;
import it.unisa.beingdigital.storage.entity.Admin;
import it.unisa.beingdigital.storage.entity.Persona;
import it.unisa.beingdigital.storage.entity.Team;
import it.unisa.beingdigital.storage.entity.Utente;
import it.unisa.beingdigital.storage.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import java.util.List;

/**
 * Questa classe rappresenta il controller per la visualizzazione della homepage.
 */

@Controller
@RequestMapping("/")
public class HomeController {

  @Autowired
  private PersonaAutenticata personaAutenticata;

  @Autowired
  private TeamRepository teamRepository;

  @GetMapping
  public String get(Model model) {

    if (personaAutenticata.getPersona().isPresent()) {
      Persona persona = personaAutenticata.getPersona().get();

      if (persona instanceof Admin){
        Admin admin = (Admin) persona;
        model.addAttribute("admin", admin);

      }else if (persona instanceof Utente) {
        Utente utente = (Utente) persona;

        List<Team> teamList = teamRepository.findByUtentiContains(utente);

        model.addAttribute("utente", utente);
        model.addAttribute("teams", teamList.isEmpty() ? null : teamList);
      }
    }
    return "presentazionerisorse/homepage";
  }
}
