package it.unisa.beingdigital.control.autenticazione;

import it.unisa.beingdigital.control.autenticazione.form.RegistrazioneForm;
import it.unisa.beingdigital.service.autenticazione.RegistrazioneService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

/**
 * Questa classe rappresenta il controller per la registrazione di un utente.
 */

@Controller
@RequestMapping("/registrazione")
public class RegistrazioneController {

  @Autowired
  private RegistrazioneService registrazioneService;

  /**
   * Implementa il post per la registrazione.
   *
   * @param registrazioneForm form di registrazione.
   * @param bindingResult     risultato della validazione di registrazioneForm.
   * @return Stringa rappresentante il path della view da rappresentare.
   * @throws ResponseStatusException se il form è malformato o se la registrazione non va
   *                                 a buon fine
   */
  @PostMapping
  public String post(@ModelAttribute @Valid RegistrazioneForm registrazioneForm,
                     BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    if (!registrazioneService.registrazione(registrazioneForm.getNome(),
        registrazioneForm.getCognome(), registrazioneForm.getEmail(),
        registrazioneForm.getPassword())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
    return "redirect:/login?registrazione=true";
  }
}
