package it.unisa.beingdigital.service.autenticazione;

import it.unisa.beingdigital.storage.repository.PersonaRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/**
 * Questa classe rappresenta il service per il controllo dell'esistenza delle email.
 */

@Service
@Transactional(readOnly = true)
@Validated
public class ExistsEmailService {

  @Autowired
  private PersonaRepository personaRepository;

  public boolean existsPersonaByEmail(@NotNull String email) {
    return personaRepository.existsByEmail(email);
  }
}
