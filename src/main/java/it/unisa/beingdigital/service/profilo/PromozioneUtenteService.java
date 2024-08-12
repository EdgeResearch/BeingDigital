package it.unisa.beingdigital.service.profilo;

import it.unisa.beingdigital.storage.entity.Admin;
import it.unisa.beingdigital.storage.entity.Utente;
import it.unisa.beingdigital.storage.repository.AdminRepository;
import it.unisa.beingdigital.storage.repository.UtenteRepository;
import jakarta.validation.constraints.NotNull;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/**
 * Questa classe rappresenta il service per la promozione di un account di utente, che diventa
 * un account admin.
 */

@Service
@Transactional
@Validated
public class PromozioneUtenteService {

  @Autowired
  private UtenteRepository utenteRepository;

  @Autowired
  private AdminRepository adminRepository;

  @Autowired
  private CancellazioneAccountService cancellazioneAccountService;

  /**
   * Implementa la funzionalità di promozione di un account utente.
   * Si assume che la corretta formulazione dell'id sia stata controllata prima
   * di effettuare la chiamata.
   *
   * @param id L'id dell'utente da promuovere.
   * @return true se la promozione è andata a buon fine, false altrimenti.
   * @throws jakarta.validation.ConstraintViolationException se l'id risulta null.
   */
  public boolean promozioneUtente(@NotNull Long id) {
    Optional<Utente> optional = utenteRepository.findById(id);
    if (optional.isEmpty()) {
      return false;
    }

    Utente utente = optional.get();
    cancellazioneAccountService.cancellazioneUtente(id);
    utenteRepository.flush();
    Admin admin = new Admin(utente.getNome(), utente.getCognome(), utente.getEmail(),
        utente.getPassword());
    adminRepository.save(admin);
    return true;
  }
}
