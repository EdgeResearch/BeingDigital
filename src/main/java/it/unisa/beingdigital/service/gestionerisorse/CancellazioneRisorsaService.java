package it.unisa.beingdigital.service.gestionerisorse;

import it.unisa.beingdigital.storage.entity.Domanda;
import it.unisa.beingdigital.storage.entity.MetaInfo;
import it.unisa.beingdigital.storage.repository.ArgomentoRepository;
import it.unisa.beingdigital.storage.repository.DomandaRepository;
import it.unisa.beingdigital.storage.repository.GiocoRepository;
import it.unisa.beingdigital.storage.repository.MetaInfoRepository;
import it.unisa.beingdigital.storage.repository.RispostaRepository;
import jakarta.validation.constraints.NotNull;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/**
 * Questa classe rappresenta il service per la cancellazione di una risorsa.
 */

@Service
@Transactional
@Validated
public class CancellazioneRisorsaService {

  @Autowired
  private MetaInfoRepository metaInfoRepository;

  @Autowired
  private ArgomentoRepository argomentoRepository;

  @Autowired
  private GiocoRepository giocoRepository;

  @Autowired
  private DomandaRepository domandaRepository;

  @Autowired
  private RispostaRepository rispostaRepository;

  /**
   * Implementa la funzionalità di cancellazione di una meta-info.
   * Si assume che la corretta formulazione dell'id sia stata controllata prima
   * di effettuare la chiamata.
   *
   * @param id Id della meta-info.
   * @return true se la cancellazione è andata a buon fine, false altrimenti.
   * @throws jakarta.validation.ConstraintViolationException se l'id risulta null.
   */
  public boolean cancellazioneMetaInfo(@NotNull Long id) {
    Optional<MetaInfo> optional = metaInfoRepository.findById(id);
    if (optional.isEmpty()) {
      return false;
    }

    MetaInfo metaInfo = optional.get();
    giocoRepository.deleteByMetaInfo(metaInfo);
    argomentoRepository.deleteByMetaInfo(metaInfo);
    domandaRepository.deleteByMetaInfo(metaInfo);
    rispostaRepository.deleteByDomandaMetaInfo(metaInfo);
    metaInfoRepository.delete(metaInfo);
    return true;
  }

  /**
   * Implementa la funzionalità di cancellazione di un argomento.
   * Si assume che la corretta formulazione dell'id sia stata controllata prima
   * di effettuare la chiamata.
   *
   * @param id Id di un argomento.
   * @return true se la cancellazione è andata a buon fine, false altrimenti.
   * @throws jakarta.validation.ConstraintViolationException se l'id risulta null.
   */
  public boolean cancellazioneArgomento(@NotNull Long id) {
    if (!argomentoRepository.existsById(id)) {
      return false;
    }

    argomentoRepository.deleteById(id);
    return true;
  }

  /**
   * Implementa la funzionalità di cancellazione di un gioco.
   * Si assume che la corretta formulazione dell'id sia stata controllata prima
   * di effettuare la chiamata.
   *
   * @param id Id di un gioco.
   * @return true se la cancellazione è andata a buon fine, false altrimenti.
   * @throws jakarta.validation.ConstraintViolationException se l'id risulta null.
   */
  public boolean cancellazioneGioco(@NotNull Long id) {
    if (!giocoRepository.existsById(id)) {
      return false;
    }

    giocoRepository.deleteById(id);
    return true;
  }

  /**
   * Implementa la funzionalità di cancellazione di una domanda.
   * Si assume che la corretta formulazione dell'id sia stata controllata prima
   * di effettuare la chiamata.
   *
   * @param id Id di una domanda.
   * @return true se la cancellazione è andata a buon fine, false altrimenti.
   * @throws jakarta.validation.ConstraintViolationException se l'id risulta null.
   */

  public boolean cancellazioneDomanda(@NotNull Long id) {
    Optional<Domanda> optional = domandaRepository.findById(id);
    if (optional.isEmpty()) {
      return false;
    }

    Domanda domanda = optional.get();
    rispostaRepository.deleteByDomanda(domanda);
    domandaRepository.delete(domanda);
    return true;
  }
}
