package it.unisa.beingdigital.control.presentazionerisorse;

import it.unisa.beingdigital.service.presentazionerisorse.PrelievoTeamService;
import it.unisa.beingdigital.service.profilo.DatiUtentiService;
import it.unisa.beingdigital.storage.entity.*;
import it.unisa.beingdigital.service.autenticazione.util.PersonaAutenticata;
import it.unisa.beingdigital.storage.entity.util.Livello;
import it.unisa.beingdigital.storage.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/auth/diariodibordo")
public class DiarioDiBordoController {

    @Autowired
    private PersonaAutenticata personaAutenticata;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private PrelievoTeamService prelievoTeamService;

    @Autowired
    private DatiUtentiService datiUtentiService;

    @GetMapping
    public String showDiarioDiBordo(Model model, @RequestParam(required = false) String codice) {
        Persona persona = personaAutenticata.getPersona().get();

        if (persona instanceof AmministratoreCittadini) {
            Optional<Team> teamOptional = teamRepository.findByCodice(codice);

            if (teamOptional.isPresent()) {
                Team team = teamOptional.get();
                model.addAttribute("team", team);

                if (team instanceof Gruppo) {
                    Gruppo gruppo = (Gruppo) team;
                    model.addAttribute("gruppo", gruppo);
                } else if (team instanceof Classe) {
                    Classe classe = (Classe) team;
                    model.addAttribute("classe", classe);
                }
                List<Utente> utenti = prelievoTeamService.getUtentiForTeam(team.getCodice());

                model.addAttribute("percentualeBase", datiUtentiService.getPercentualeUtenti(Livello.BASE));
                model.addAttribute("percentualeIntermedio", datiUtentiService.getPercentualeUtenti(Livello.INTERMEDIO));
                model.addAttribute("percentualeAvanzato", datiUtentiService.getPercentualeUtenti(Livello.AVANZATO));
                model.addAttribute("percentualeMaster", datiUtentiService.getPercentualeUtenti(Livello.MASTER));
            }

            model.addAttribute("isTeam", true);
            model.addAttribute("AmministratoreCittadini", persona);
        } else if (persona instanceof Utente) {
            Utente utente = (Utente) persona;
            model.addAttribute("Utente", utente);
            model.addAttribute("isTeam", false);
            model.addAttribute("livelloUtente", utente.getLivello().toString().toLowerCase());
        }

        return "/presentazionerisorse/diarioDiBordo";
    }
}