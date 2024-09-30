package it.unisa.beingdigital.control.profilo;

import it.unisa.beingdigital.service.autenticazione.util.PersonaAutenticata;
import it.unisa.beingdigital.storage.entity.*;
import it.unisa.beingdigital.storage.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Questa classe rappresenta il controller per il profilo di un utente.
 */

@Controller
@RequestMapping("/auth/Profilo")
public class ProfiloController {

    @Autowired
    private PersonaAutenticata personaAutenticata;

    @Autowired
    private TeamRepository teamRepository;



    @GetMapping
    public String get(Model model) {
        Persona persona = personaAutenticata.getPersona().get();
        return caricaDatiPersonali(model, persona);
    }

    private String caricaDatiPersonali(Model model, Persona persona) {
        List<Team> teamList;
        if (persona instanceof Admin) {
            model.addAttribute("admin", persona);
        } else if (persona instanceof AmministratoreCittadini) {
            AmministratoreCittadini amministratoreCittadini = (AmministratoreCittadini) persona;
            teamList = teamRepository.findByAmministratoriCittadiniContains(amministratoreCittadini);
            model.addAttribute("teams", teamList.isEmpty() ? null : teamList);
            model.addAttribute("amministratoreCittadini", amministratoreCittadini);
        } else if (persona instanceof Utente) {
            Utente utente = (Utente) persona;
            teamList = teamRepository.findByUtentiContains(utente);
            model.addAttribute("teams", teamList.isEmpty() ? null : teamList);
            model.addAttribute("utente", utente);
        }
        return "profilo/profilo";
    }
}
