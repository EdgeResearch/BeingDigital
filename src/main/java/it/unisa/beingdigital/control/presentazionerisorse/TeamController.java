package it.unisa.beingdigital.control.presentazionerisorse;

import it.unisa.beingdigital.service.autenticazione.util.PersonaAutenticata;
import it.unisa.beingdigital.service.presentazionerisorse.PrelievoTeamService;
import it.unisa.beingdigital.storage.entity.*;
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

@Controller
@RequestMapping("/team")
public class TeamController {

    @Autowired
    private PersonaAutenticata personaAutenticata;

    @Autowired
    private PrelievoTeamService prelievoTeamService;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private AmministratoreCittadiniRepository amministratoreCittadiniRepository;


    /**
     * Metodo che gestisce la visualizzazione di un team.
     * Può gestire sia GET che POST, in base al metodo di richiesta.
     * @param model Oggetto model per passare i dati alla vista.
     * @return Nome della vista da caricare.
     */
    @GetMapping
    public String selezionaTeam(Model model) {
        return caricaTeam(model);
    }

    @PostMapping
    public String selezionaTeamPost(Model model) {
        return caricaTeam(model);
    }

    @GetMapping("/dettagli")
    public String getTeamDettagli(@RequestParam("codice") String codice, Model model) {
        Persona persona = personaAutenticata.getPersona().get();

        if (persona instanceof AmministratoreCittadini) {
            model.addAttribute("AmministratoreCittadini", persona);
        } else if (persona instanceof Utente) {
            model.addAttribute("Utente", persona);
        }

        Optional<Team> teamOptional = teamRepository.findByCodice(codice);

        if (teamOptional.isPresent()) {
            Team team = teamOptional.get();
            model.addAttribute("team", team);
            return "presentazionerisorse/dettagli";
        } else {
            model.addAttribute("error", "Team non trovato");
            return "error";
        }
    }

    private String caricaTeam(Model model) {
        Persona persona = personaAutenticata.getPersona().get();
        List<Team> teams = List.of();

        if (persona instanceof AmministratoreCittadini) {
            model.addAttribute("AmministratoreCittadini", persona);
            teams = prelievoTeamService.getTeamsForAmministratore(persona.getId());
        } else if (persona instanceof Utente) {
            model.addAttribute("Utente", persona);
            teams = prelievoTeamService.getTeamsForUtente(persona.getId());
        }

        for (Team team : teams) {
            List<AmministratoreCittadini> amministratoreCittadini = prelievoTeamService.getAmministratoriForTeam(team.getCodice());
            List<Utente> utenti = prelievoTeamService.getUtentiForTeam(team.getCodice());
            team.setAmministratoriCittadini(amministratoreCittadini);
            team.setUtenti(utenti);
        }

        model.addAttribute("teams", teams.isEmpty() ? null : teams);

        return "presentazionerisorse/team";
    }

    @PostMapping("/entraTeam")
    public String entraTeam(@RequestParam("codice") String codiceTeam, Model model) {
        Persona persona = personaAutenticata.getPersona().get();

        Optional<Team> teamOpt = teamRepository.findByCodice(codiceTeam);

        if (teamOpt.isPresent()) {
            Team team = teamOpt.get();

            if (persona instanceof Utente) {
                Utente utente = (Utente) persona;
                if (team.getUtenti().stream().anyMatch(u -> u.getId().equals(utente.getId()))) {
                    model.addAttribute("messageTeam", "Sei già membro di questo team.");
                } else {
                    team.getUtenti().add(utente);
                    teamRepository.save(team);
                    utenteRepository.save(utente);
                }
            } else if (persona instanceof AmministratoreCittadini) {
                AmministratoreCittadini amministratoreCittadini = (AmministratoreCittadini) persona;
                if (team.getAmministratoriCittadini().stream().anyMatch(a -> a.getId().equals(amministratoreCittadini.getId()))) {
                    model.addAttribute("messageTeam", "Sei già membro di questo team.");
                } else {
                    team.getAmministratoriCittadini().add(amministratoreCittadini);
                    teamRepository.save(team);
                    amministratoreCittadiniRepository.save(amministratoreCittadini);
                }
            }
        } else {
            model.addAttribute("messageTeam", "Il codice inserito non è valido.");
        }

        return caricaTeam(model);
    }

    /*
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
        } else if (persona instanceof AmministratoreCittadini) {
            List<Team> teams = prelievoTeamService.getTeamsForAmministratore(persona.getId());
            model.addAttribute("amministratoreCittadini", persona);
            model.addAttribute("teams", teams.isEmpty() ? null : teams);
            model.addAttribute("message", teams.isEmpty() ? "non sei in nessun team" : null);
        } else if (persona instanceof Utente) {
            List<Team> teams = prelievoTeamService.getTeamsForUtente(persona.getId());
            model.addAttribute("utente", persona);
            model.addAttribute("teams", teams.isEmpty() ? null : teams);
            model.addAttribute("message", teams.isEmpty() ? "non sei in nessun team" : null);
        }
        return "/presentazionerisorse/team";
    }*/
}
