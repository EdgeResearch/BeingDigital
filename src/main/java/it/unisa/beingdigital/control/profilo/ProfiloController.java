package it.unisa.beingdigital.control.profilo;

import it.unisa.beingdigital.service.autenticazione.util.PersonaAutenticata;
import it.unisa.beingdigital.storage.entity.*;
import it.unisa.beingdigital.storage.repository.PersonaRepository;
import it.unisa.beingdigital.storage.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;
import java.util.Optional;

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

    @Autowired
    private PersonaRepository personaRepository;

    @GetMapping
    public String get(@RequestParam(required = false) Long personaId, Model model) {
        Persona persona;
        Persona personaAutenticataCorrente = personaAutenticata.getPersona()
                .orElseThrow(() -> new RuntimeException("Utente non autenticato"));

        if (personaId != null) {
            Optional<Persona> personaOptional = personaRepository.findById(personaId);
            if (personaOptional.isPresent()) {
                persona = personaOptional.get();
            } else {
                model.addAttribute("error", "Utente non trovato");
                return "error";
            }
        } else {
            persona = personaAutenticataCorrente;
        }

        boolean isOwner = persona.getId().equals(personaAutenticataCorrente.getId());
        model.addAttribute("isOwner", isOwner);

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
            model.addAttribute("AmministratoreCittadini", amministratoreCittadini);
        } else if (persona instanceof Utente) {
            Utente utente = (Utente) persona;
            teamList = teamRepository.findByUtentiContains(utente);
            model.addAttribute("teams", teamList.isEmpty() ? null : teamList);
            model.addAttribute("Utente", utente);
        }
        return "profilo/profilo";
    }

    @PostMapping("/salvaBio")
    public String salvaBio(@RequestParam("bio") String bio, RedirectAttributes redirectAttributes) {
        Persona persona = personaAutenticata.getPersona().orElseThrow(() -> new RuntimeException("Utente non autenticato"));

        if (persona instanceof AmministratoreCittadini) {
            AmministratoreCittadini amministratore = (AmministratoreCittadini) persona;
            amministratore.setBiografia(bio);
            personaRepository.save(amministratore);
        } else if (persona instanceof Utente) {
            Utente utente = (Utente) persona;
            utente.setBiografia(bio);
            personaRepository.save(utente);
        }

        redirectAttributes.addFlashAttribute("successMessage", "Biografia aggiornata con successo!");
        return "redirect:/auth/Profilo";
    }
}