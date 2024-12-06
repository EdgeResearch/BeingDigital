package it.unisa.beingdigital.control.gestionerisorse;

import it.unisa.beingdigital.service.presentazionerisorse.ProgressoUtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class ProgressoUtenteController {

    @Autowired
    private ProgressoUtenteService progressoUtenteService;

    @PostMapping("/inizializza-progressi")
    public ResponseEntity<String> inizializzaProgressi() {
        progressoUtenteService.inizializzaProgressi();
        return ResponseEntity.ok("Progressi inizializzati per tutti gli utenti.");
    }
}