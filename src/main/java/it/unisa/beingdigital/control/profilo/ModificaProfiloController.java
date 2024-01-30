package it.unisa.beingdigital.control.profilo;

import it.unisa.beingdigital.control.profilo.form.ModificaProfiloForm;
import it.unisa.beingdigital.service.autenticazione.CheckPasswordService;
import it.unisa.beingdigital.service.autenticazione.util.PersonaAutenticata;
import it.unisa.beingdigital.service.profilo.ModificaProfiloService;
import it.unisa.beingdigital.storage.entity.Persona;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

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
  public String get(@ModelAttribute ModificaProfiloForm modificaProfiloForm) {
    Persona persona = personaAutenticata.getPersona().get();

    modificaProfiloForm.setNome(persona.getNome());
    modificaProfiloForm.setCognome(persona.getCognome());
    modificaProfiloForm.setEmail(persona.getEmail());
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
                     BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    Persona persona = personaAutenticata.getPersona().get();

    String passwordNuova = null;

    if (!modificaProfiloForm.getPasswordAttuale().isEmpty()) {
      if (!checkPasswordService.checkPassword(persona, modificaProfiloForm.getPasswordAttuale())) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
      }

      if (!modificaProfiloForm.getPasswordNuova().isEmpty()) {
        passwordNuova = modificaProfiloForm.getPasswordNuova();
      }
    }

    if (!modificaProfiloService.modificaProfilo(persona, modificaProfiloForm.getNome(),
        modificaProfiloForm.getCognome(), modificaProfiloForm.getEmail(), passwordNuova)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    return "redirect:/auth/areaPersonale";
  }
}
