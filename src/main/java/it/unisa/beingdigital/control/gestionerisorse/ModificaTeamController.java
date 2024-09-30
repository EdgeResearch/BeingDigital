package it.unisa.beingdigital.control.gestionerisorse;

import it.unisa.beingdigital.control.gestionerisorse.form.MetaInfoForm;
import it.unisa.beingdigital.control.gestionerisorse.form.TeamForm;
import it.unisa.beingdigital.service.gestionerisorse.ModificaRisorsaService;
import it.unisa.beingdigital.service.presentazionerisorse.PrelievoMetaInfoService;
import it.unisa.beingdigital.service.presentazionerisorse.PrelievoTeamService;
import it.unisa.beingdigital.storage.entity.Classe;
import it.unisa.beingdigital.storage.entity.Gruppo;
import it.unisa.beingdigital.storage.entity.MetaInfo;
import it.unisa.beingdigital.storage.entity.Team;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

/**
 * Questa classe rappresenta il controller per la modifica di un Team.
 */

@Controller
@RequestMapping("/amministratoreCittadini/modificaTeam")
public class ModificaTeamController {

    @Autowired
    private ModificaRisorsaService modificaRisorsaService;

    @Autowired
    private PrelievoTeamService prelievoTeamService;

    /**
     * Implementa il get per la modifica di un Team.
     *
     * @param codice   codice del Team da modificare.
     * @param teamForm form da inserire nel model.
     * @return Stringa rappresentante il path della view da rappresentare.
     * @throws ResponseStatusException se il codice è nullo o non valido.
     */

    @GetMapping
    public String get(@RequestParam String codice, @ModelAttribute TeamForm teamForm, Model model) {
        Optional<Team> optional = prelievoTeamService.getTeam(codice);
        if (optional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Team non trovato");
        }

        Team team = optional.get();

        teamForm.setNome(team.getNome());
        teamForm.setEmail(team.getEmail());

        if (team instanceof Classe) {
            Classe classeTeam = (Classe) team;
            teamForm.setTipoTeam("classe");
            teamForm.setClasse(classeTeam.getClasse());
            teamForm.setScuola(classeTeam.getScuola());
        } else if (team instanceof Gruppo) {
            Gruppo gruppoTeam = (Gruppo) team;
            teamForm.setTipoTeam("gruppo");
            teamForm.setCittà(gruppoTeam.getCittà());
        }

        model.addAttribute("team", team);
        return "gestionerisorse/modificaTeam";
    }
    /**
     * Implementa il post per la modifica di un Team.
     *
     * @param codice        codice del team da modificare.
     * @param teamForm      form contenente i nuovi dati.
     * @param bindingResult risultato della validazione del form.
     * @param model         model da passare alla view.
     * @return Stringa rappresentante il path della view da rappresentare.
     * @throws ResponseStatusException se il codice risulta nullo, se il form non è ben formato.
     */
    @PostMapping
    public String post(@RequestParam String codice,
                       @ModelAttribute @Valid TeamForm teamForm,
                       BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("team", prelievoTeamService.getTeam(codice).orElseThrow());
            return "gestionerisorse/modificaTeam";
        }

        boolean successo = modificaRisorsaService.modificaTeamAmministratore(
                teamForm.getTipoTeam(), codice, teamForm.getNome(), teamForm.getEmail(),
                teamForm.getCittà(), teamForm.getScuola(), teamForm.getClasse());

        if (!successo) {
            model.addAttribute("error", "Modifica fallita.");
            return "gestionerisorse/modificaTeam";
        }

        return "redirect:/team";
    }

    @PostMapping("/espelliUtente")
    public String espelliUtente(@RequestParam String codiceTeam, @RequestParam String idUtente) {

        Optional<Team> optionalTeam = prelievoTeamService.getTeam(codiceTeam);
        if (optionalTeam.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Team non trovato");
        }

        Team team = optionalTeam.get();

        boolean espulsioneSuccesso = modificaRisorsaService.espelliUtententeDalTeam(team, Long.valueOf(idUtente));
        if (!espulsioneSuccesso) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Espulsione fallita");
        }

        return "redirect:/team?codice=" + codiceTeam;
    }
}