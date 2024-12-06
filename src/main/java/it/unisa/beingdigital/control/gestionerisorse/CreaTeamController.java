package it.unisa.beingdigital.control.gestionerisorse;

import it.unisa.beingdigital.control.gestionerisorse.form.TeamForm;
import it.unisa.beingdigital.service.gestionerisorse.InserimentoRisorsaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;


/**
 * Questa classe rappresenta il controller per la creazione di un Team.
 */

@Controller
@RequestMapping("/amministratoreCittadini/creazioneTeam")
public class CreaTeamController {

    @Autowired
    private InserimentoRisorsaService inserimentoRisorsaService;

    @GetMapping
    public String get(@ModelAttribute TeamForm teamForm, Model model){
        model.addAttribute("isEditing", false);
        return "gestionerisorse/modificaTeam";
    }

    /**
     * Implementa il post per l'inserimento di un team.
     *
     * @param teamForm      form rappresentante il team da inserire.
     * @param bindingResult risultato della validazione di teamForm.
     * @return Stringa rappresentante il path della view da rappresentare.
     * @throws ResponseStatusException se teamForm è mal formato o l'inserimento non va a buon fine.
     */
    @PostMapping
    public String post(@ModelAttribute @Valid TeamForm teamForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (teamForm.getNome() == null || teamForm.getEmail() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (!inserimentoRisorsaService.inserimentoTeam(teamForm.getNome(), teamForm.getEmail(), teamForm.getTipoTeam(), teamForm.getCittà(), teamForm.getClasse(), teamForm.getScuola())){
            return "gestionerisorse/modificaTeam";
        }

        return "redirect:/team";
    }
}
