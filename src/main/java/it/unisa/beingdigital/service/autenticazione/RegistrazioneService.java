package it.unisa.beingdigital.service.autenticazione;

import it.unisa.beingdigital.storage.entity.Utente;
import it.unisa.beingdigital.storage.entity.util.Livello;
import it.unisa.beingdigital.storage.repository.PersonaRepository;
import it.unisa.beingdigital.storage.repository.UtenteRepository;
import jakarta.validation.constraints.NotNull;
import org.jasypt.util.password.PasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/**
 * Questa classe rappresenta il service per il processo di registrazione di un utente.
 */

@Service
@Transactional
@Validated
public class RegistrazioneService {

  @Autowired
  private PersonaRepository personaRepository;

  @Autowired
  private UtenteRepository utenteRepository;

  @Autowired
  private PasswordEncryptor passwordEncryptor;

  /**
   * Implementa la funzionalità di registrazione di un utente.
   * Si assume che la corretta formulazione dei parametri sia stata controllata prima
   * di effettuare la chiamata.
   *
   * @param email    Email dell'utente.
   * @param password Password dell'utente.
   * @param nome     Nome dell'utente.
   * @param cognome  Cognome dell'utente.
   * @return true se la registrazione è andata a buon fine, false altrimenti.
   * @throws jakarta.validation.ConstraintViolationException se uno dei parametri risulta null.
   */

  public boolean registrazione(@NotNull String nome, @NotNull String cognome, @NotNull String email,
                               @NotNull String password) {
    if (personaRepository.existsByEmail(email)) {
      return false;
    }

    Utente utente =
        new Utente(nome, cognome, email, passwordEncryptor.encryptPassword(password), Livello.BASE);

    utenteRepository.save(utente);
    return true;
  }
}
