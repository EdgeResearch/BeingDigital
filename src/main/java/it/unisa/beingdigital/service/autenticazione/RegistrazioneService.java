package it.unisa.beingdigital.service.autenticazione;

import it.unisa.beingdigital.storage.entity.Utente;
import it.unisa.beingdigital.storage.entity.util.Livello;
import it.unisa.beingdigital.storage.repository.PersonaRepository;
import it.unisa.beingdigital.storage.repository.UtenteRepository;
import it.unisa.beingdigital.service.presentazionerisorse.ProgressoUtenteService;
import jakarta.validation.constraints.NotNull;
import org.jasypt.util.password.PasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

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
  private ProgressoUtenteService progressoUtenteService;

  @Autowired
  private PasswordEncryptor passwordEncryptor;

  private static final String STOCK_IMAGE_PATH = "static/img/user_stock.jpeg";

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

    byte[] immagineDefault = loadStockImage();

    Utente utente =
            new Utente(nome, cognome, email, passwordEncryptor.encryptPassword(password), Livello.BASE, immagineDefault, "");

    utenteRepository.save(utente);

    progressoUtenteService.inizializzaProgressiPerUtente(utente);

    return true;
  }

  /**
   * Carica l'immagine di profilo stock dalle risorse del progetto.
   *
   * @return L'immagine stock come array di byte, o null se non riesce a caricare il file.
   */
  private byte[] loadStockImage() {
    try {
      ClassPathResource resource = new ClassPathResource(STOCK_IMAGE_PATH);
      return Files.readAllBytes(resource.getFile().toPath());
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }
}