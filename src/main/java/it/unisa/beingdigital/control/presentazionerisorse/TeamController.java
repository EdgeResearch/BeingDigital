package it.unisa.beingdigital.control.presentazionerisorse;

import it.unisa.beingdigital.service.autenticazione.util.PersonaAutenticata;
import it.unisa.beingdigital.storage.entity.AmministratoreCittadini;
import it.unisa.beingdigital.storage.entity.Persona;
import it.unisa.beingdigital.storage.entity.Team;
import it.unisa.beingdigital.storage.entity.Utente;
import it.unisa.beingdigital.storage.repository.TeamRepository;
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
    private TeamRepository teamRepository;

    @Autowired
    private PersonaAutenticata personaAutenticata;


    /**
     * Metodo che gestisce la visualizzazione di un team.
     * Può gestire sia GET che POST, in base al metodo di richiesta.
     * @param codice codice del team selezionato.
     * @param model Oggetto model per passare i dati alla vista.
     * @return Nome della vista da caricare.
     */
    @GetMapping
    public String selezionaTeam(@RequestParam("codice") String codice, Model model) {
        return caricaTeam(codice, model);
    }

    @PostMapping
    public String selezionaTeamPost(@RequestParam("codice") String codice, Model model) {
        return caricaTeam(codice, model);
    }

    private String caricaTeam(String codice, Model model) {
        Persona persona = (Persona) personaAutenticata.getPersona().get();

        // Aggiungi l'attributo se la persona è un amministratore
        if (persona instanceof AmministratoreCittadini){
            model.addAttribute("AmministratoreCittadini", persona);
        }

        // Recupera il team dal codice
        Optional<Team> teamOpt = teamRepository.findById(codice);
        if (teamOpt.isPresent()) {
            Team team = teamOpt.get();
            List<Utente> utenti = team.getUtenti();
            List<AmministratoreCittadini> amministratoriCittadini = team.getAmministratoriCittadini();
            model.addAttribute("team", team);
            model.addAttribute("utenti", utenti);
            model.addAttribute("amministratoriCittadini", amministratoriCittadini);
        }

        return "presentazionerisorse/team";
    }
}
