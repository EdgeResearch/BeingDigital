package it.unisa.beingdigital.service.presentazionerisorse;

import it.unisa.beingdigital.storage.entity.Risposta;
import it.unisa.beingdigital.storage.entity.Utente;
import it.unisa.beingdigital.storage.repository.RispostaRepository;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/**
 * Questa classe rappresenta il service per il prelievo delle risposte.
 */

@Service
@Transactional(readOnly = true)
@Validated
public class PrelievoRispostaService {

  @Autowired
  private RispostaRepository rispostaRepository;

  /**
   * Implementa la funzionalità di prelievo di tutte le domande di un utente ordinate per keyword
   * e testo.
   * Si assume che l'utente sia presente nel DB.
   *
   * @param utente utente per cui prelevare le domande.
   * @return lista di risposte.
   * @throws jakarta.validation.ConstraintViolationException se l'utente risulta null.
   */
  public List<Risposta> getRisposteSortedByKeywordTesto(@NotNull Utente utente) {
    return rispostaRepository.findByUtente(utente,
        Sort.by("domandaMetaInfoKeyword", "domandaTesto"));
  }
}
