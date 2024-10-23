package it.unisa.beingdigital.control.profilo;

import it.unisa.beingdigital.control.profilo.form.ModificaProfiloForm;
import it.unisa.beingdigital.service.autenticazione.CheckPasswordService;
import it.unisa.beingdigital.service.autenticazione.util.PersonaAutenticata;
import it.unisa.beingdigital.service.profilo.ModificaProfiloService;
import it.unisa.beingdigital.storage.entity.Persona;
import it.unisa.beingdigital.storage.entity.Utente;
import it.unisa.beingdigital.storage.entity.AmministratoreCittadini;
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

/**
 * Controller per la modifica del profilo della persona autenticata.
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

  @PostMapping
  public String post(@ModelAttribute @Valid ModificaProfiloForm modificaProfiloForm,
                     BindingResult bindingResult, Model model) throws IOException {

    if (bindingResult.hasErrors()) {
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
    if (persona instanceof Utente) {
      model.addAttribute("utente", persona);
    } else if (persona instanceof AmministratoreCittadini) {
      model.addAttribute("amministratoreCittadini", persona);
    }
    return "profilo/profilo";
  }
}