package it.unisa.beingdigital.control.presentazionerisorse;

import it.unisa.beingdigital.storage.entity.AmministratoreCittadini;
import it.unisa.beingdigital.storage.entity.Team;
import it.unisa.beingdigital.storage.entity.Utente;
import it.unisa.beingdigital.storage.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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


    /**
     * Metodo che gestisce l'azione quando l'utente seleziona un team.
     * @param codice codice del team selezionato.
     * @param model Oggetto model per passare i dati alla vista.
     * @return Nome della vista da caricare.
     */
    @PostMapping
    public String selezionaTeam(@RequestParam("codice") String codice, Model model) {
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
