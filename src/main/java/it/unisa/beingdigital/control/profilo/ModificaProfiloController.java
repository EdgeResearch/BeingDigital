package it.unisa.beingdigital.control.profilo;

import it.unisa.beingdigital.control.profilo.form.ModificaProfiloForm;
import it.unisa.beingdigital.service.autenticazione.CheckPasswordService;
import it.unisa.beingdigital.service.autenticazione.util.PersonaAutenticata;
import it.unisa.beingdigital.service.profilo.ModificaProfiloService;
import it.unisa.beingdigital.storage.entity.*;
import it.unisa.beingdigital.storage.entity.util.Livello;
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

import java.io.IOException;
import java.util.List;

/**
 * Questa classe rappresenta il controller per la modifica di un account.
 */

@Controller
@RequestMapping("/auth/modificaProfilo")
public class ModificaProfiloController {

  @Autowired
  private PersonaAutenticata personaAutenticata;

  @Autowired
  private ModificaProfiloService modificaProfiloService;

  @Autowired
  private CheckPasswordService checkPasswordService;

  /**
   * Implementa il get per la modifica dell'account dell'persona autenticata.
   *
   * @param modificaProfiloForm Form contenente i dati della persona autenticata.
   * @return Stringa rappresentante il path della view da rappresentare.
   */
  @GetMapping
  public String get(@ModelAttribute ModificaProfiloForm modificaProfiloForm, Model model) {
    Persona persona = personaAutenticata.getPersona().get();

    modificaProfiloForm.setNome(persona.getNome());
    modificaProfiloForm.setCognome(persona.getCognome());
    modificaProfiloForm.setEmail(persona.getEmail());

    if (persona instanceof Utente) {
      model.addAttribute("utente", persona);
    }

    return "profilo/modificaProfilo";
  }

  /**
   * Implementa il post per la modifica dell'account dell'persona autenticata.
   *
   * @param modificaProfiloForm Form contenente i dati della persona autenticata.
   * @param bindingResult       Risultato della validazione del form.
   * @return Stringa rappresentante il path della view da rappresentare.
   * @throws ResponseStatusException se il form non risulta valido.
   */
  @PostMapping
  public String post(@ModelAttribute @Valid ModificaProfiloForm modificaProfiloForm,
                     BindingResult bindingResult, Model model) throws IOException {

    if (bindingResult.hasErrors()) {
      bindingResult.getAllErrors().forEach(error -> {
        System.out.println("Errore nel binding: " + error.getDefaultMessage());
        System.out.println("Codice errore: " + error.getCode());
        System.out.println("Oggetto con errore: " + error.getObjectName());
      });
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    Persona persona = personaAutenticata.getPersona().get();
    String passwordNuova = null;

    if (persona instanceof Utente) {
      Utente utente = (Utente) persona;
      if (!modificaProfiloForm.getPasswordAttuale().isEmpty()) {
        if (!checkPasswordService.checkPassword(utente, modificaProfiloForm.getPasswordAttuale())) {
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (!modificaProfiloForm.getPasswordNuova().isEmpty()) {
          passwordNuova = modificaProfiloForm.getPasswordNuova();
        }
      }

      byte[] imageBytes = null;
      if (modificaProfiloForm.getFotoprofilo() != null && !modificaProfiloForm.getFotoprofilo().isEmpty()) {
        imageBytes = modificaProfiloForm.getFotoprofilo().getBytes();
      }

      if (!modificaProfiloService.modificaProfilo(utente, modificaProfiloForm.getNome(),
              modificaProfiloForm.getCognome(), modificaProfiloForm.getEmail(), passwordNuova, imageBytes)) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
      }

      return caricaDatiPersonali(model, utente);

    } else if (persona instanceof AmministratoreCittadini) {
      AmministratoreCittadini amministratoreCittadini = (AmministratoreCittadini) persona;
      if (!modificaProfiloForm.getPasswordAttuale().isEmpty()) {
        if (!checkPasswordService.checkPassword(amministratoreCittadini, modificaProfiloForm.getPasswordAttuale())) {
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (!modificaProfiloForm.getPasswordNuova().isEmpty()) {
          passwordNuova = modificaProfiloForm.getPasswordNuova();
        }
      }

      byte[] imageBytes = null;
      if (modificaProfiloForm.getFotoprofilo() != null && !modificaProfiloForm.getFotoprofilo().isEmpty()) {
        imageBytes = modificaProfiloForm.getFotoprofilo().getBytes();
      }

      if (!modificaProfiloService.modificaProfilo(amministratoreCittadini, modificaProfiloForm.getNome(),
              modificaProfiloForm.getCognome(), modificaProfiloForm.getEmail(), passwordNuova, imageBytes)) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
      }

      return caricaDatiPersonali(model, amministratoreCittadini);
    }
    return caricaDatiPersonali(model, persona);
  }

  private String caricaDatiPersonali(Model model, Persona persona) {
    if (persona instanceof Admin) {
      model.addAttribute("admin", persona);
    } else if (persona instanceof AmministratoreCittadini) {
      model.addAttribute("amministratoreCittadini", persona);
    } else if (persona instanceof Utente) {
      model.addAttribute("utente", persona);
    }
    return "profilo/profilo";
  }
}
