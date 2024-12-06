package it.unisa.beingdigital.control.presentazionerisorse;

import it.unisa.beingdigital.service.presentazionerisorse.PrelievoTeamService;
import it.unisa.beingdigital.service.presentazionerisorse.ProgressoUtenteService;
import it.unisa.beingdigital.service.profilo.DatiUtentiService;
import it.unisa.beingdigital.storage.entity.*;
import it.unisa.beingdigital.service.autenticazione.util.PersonaAutenticata;
import it.unisa.beingdigital.storage.entity.util.Livello;
import it.unisa.beingdigital.storage.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/auth/diariodibordo")
public class DiarioDiBordoController {

    @Autowired
    private PersonaAutenticata personaAutenticata;

    @Autowired
    private PrelievoTeamService prelievoTeamService;

    @Autowired
    private DatiUtentiService datiUtentiService;

    @Autowired
    private ProgressoUtenteService progressoUtenteService;

    @Autowired
    private TeamRepository teamRepository;

    @GetMapping
    public String showDiarioDiBordo(Model model, @RequestParam(required = false) String codice) {

        Persona persona = personaAutenticata.getPersona()
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        if (persona instanceof AmministratoreCittadini) {
            Optional<Team> teamOptional = teamRepository.findByCodice(codice);

            if (teamOptional.isPresent()) {
                Team team = teamOptional.get();
                model.addAttribute("team", team);

                if (team instanceof Gruppo) {
                    model.addAttribute("gruppo", team);
                } else if (team instanceof Classe) {
                    model.addAttribute("classe", team);
                }

                model.addAttribute("percentualeBase", datiUtentiService.getPercentualeUtenti(Livello.BASE));
                model.addAttribute("percentualeIntermedio", datiUtentiService.getPercentualeUtenti(Livello.INTERMEDIO));
                model.addAttribute("percentualeAvanzato", datiUtentiService.getPercentualeUtenti(Livello.AVANZATO));
                model.addAttribute("percentualeMaster", datiUtentiService.getPercentualeUtenti(Livello.MASTER));
            }

            model.addAttribute("isTeam", true);
            model.addAttribute("AmministratoreCittadini", persona);

        } else if (persona instanceof Utente) {
            model.addAttribute("isTeam", false);
            Utente utente = (Utente) persona;

            model.addAttribute("Utente", utente);
            model.addAttribute("livelloUtente", Livello.BASE.toString().toLowerCase());
        }

        return "presentazionerisorse/diarioDiBordo";
    }

    @GetMapping("/percentuali/team/{codice}")
    @ResponseBody
    public ResponseEntity<Map<String, Double>> getPercentualiPerTeam(
            @PathVariable String codice,
            @RequestParam String categoria) {
        Persona persona = personaAutenticata.getPersona()
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        if (!(persona instanceof AmministratoreCittadini)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Map<String, Double> percentuali = new HashMap<>();
        Optional<Team> teamOptional = teamRepository.findByCodice(codice);

        if (teamOptional.isPresent()) {
            Team team = teamOptional.get();
            for (Livello livello : Livello.values()) {
                double percentuale = prelievoTeamService.calcolaPercentualeUtentiConLivello(team, livello, categoria);
                percentuali.put(livello.toString().toLowerCase(), percentuale);
            }
        }

        return ResponseEntity.ok(percentuali);
    }

    @GetMapping("/percentuali/utente")
    @ResponseBody
    public ResponseEntity<Map<String, Double>> getPercentualiPerUtente(@RequestParam String categoria) {
        Persona persona = personaAutenticata.getPersona()
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        if (!(persona instanceof Utente)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Map<String, Double> percentuali = new HashMap<>();
        Utente utente = (Utente) persona;

        ProgressoUtente progressoUtente = progressoUtenteService.getProgressoUtenteBySottoArgomento(utente, categoria);
        Livello livelloProgresso = (progressoUtente != null) ? progressoUtente.getLivello() : Livello.BASE;

        for (Livello livello : Livello.values()) {
            if (livello.ordinal() < livelloProgresso.ordinal()) {
                percentuali.put(livello.toString().toLowerCase(), 100.0);
            } else if (livello == livelloProgresso) {
                percentuali.put(livello.toString().toLowerCase(),
                        datiUtentiService.calcolaPercentualeCompletamento(utente, categoria));
            } else {
                percentuali.put(livello.toString().toLowerCase(), 0.0);
            }
        }

        return ResponseEntity.ok(percentuali);
    }
}