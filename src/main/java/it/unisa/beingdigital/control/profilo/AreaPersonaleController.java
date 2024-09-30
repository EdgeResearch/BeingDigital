package it.unisa.beingdigital.control.profilo;

import it.unisa.beingdigital.service.autenticazione.util.PersonaAutenticata;
import it.unisa.beingdigital.service.presentazionerisorse.PrelievoTeamService;
import it.unisa.beingdigital.service.profilo.DatiUtentiService;
import it.unisa.beingdigital.storage.entity.*;
import it.unisa.beingdigital.storage.entity.util.Livello;
import it.unisa.beingdigital.storage.repository.AmministratoreCittadiniRepository;
import it.unisa.beingdigital.storage.repository.TeamRepository;
import it.unisa.beingdigital.storage.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

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

    @Autowired
    private PrelievoTeamService prelievoTeamService;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private AmministratoreCittadiniRepository amministratoreCittadiniRepository;

    @GetMapping
    public String get(Model model) {
        Persona persona = personaAutenticata.getPersona().get();
        return caricaDatiPersonali(model, persona);
    }
    @PostMapping("/daDefinire")
    public String entraTeam(@RequestParam("codice") String codiceTeam, Model model) {
        Persona persona = personaAutenticata.getPersona().get();

        Optional<Team> teamOpt = teamRepository.findByCodice(codiceTeam);
        if (teamOpt.isPresent()) {
            Team team = teamOpt.get();
            if (persona instanceof Utente) {
                Utente utente = (Utente) persona;
                if (!team.getUtenti().stream().anyMatch(u -> u.getId().equals(utente.getId()))) {
                    team.getUtenti().add(utente);
                    teamRepository.save(team);
                    utenteRepository.save(utente);
                }else{
                    model.addAttribute("messageTeam", "Sei già membro di questo team.");
                }
            }else if(persona instanceof AmministratoreCittadini){
                AmministratoreCittadini amministratoreCittadini = (AmministratoreCittadini) persona;
                if (!team.getAmministratoriCittadini().stream().anyMatch(a -> a.getId().equals(amministratoreCittadini.getId()))) {
                    team.getAmministratoriCittadini().add(amministratoreCittadini);
                    teamRepository.save(team);
                    amministratoreCittadiniRepository.save(amministratoreCittadini);
                }else{
                    model.addAttribute("messageTeam", "Sei già membro di questo team.");
                }
            }
        }
        return caricaDatiPersonali(model, persona);
    }
    private String caricaDatiPersonali(Model model, Persona persona) {
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
        } else if (persona instanceof AmministratoreCittadini) {
            List<Team> teams = prelievoTeamService.getTeamsForAmministratore(persona.getId());
            model.addAttribute("amministratoreCittadini", persona);
            model.addAttribute("teams", teams.isEmpty() ? null : teams);
            model.addAttribute("message", teams.isEmpty() ? "non sei in nessun team" : null);
            return "profilo/amministratoreCittadini";
        } else if (persona instanceof Utente) {
            List<Team> teams = prelievoTeamService.getTeamsForUtente(persona.getId());
            model.addAttribute("utente", persona);
            model.addAttribute("teams", teams.isEmpty() ? null : teams);
            model.addAttribute("message", teams.isEmpty() ? "non sei in nessun team" : null);
            return "profilo/utente";
        }
        return "redirect:";
    }
}
