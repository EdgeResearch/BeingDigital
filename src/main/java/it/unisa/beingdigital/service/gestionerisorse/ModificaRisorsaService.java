package it.unisa.beingdigital.service.gestionerisorse;

import it.unisa.beingdigital.storage.entity.Argomento;
import it.unisa.beingdigital.storage.entity.Domanda;
import it.unisa.beingdigital.storage.entity.Gioco;
import it.unisa.beingdigital.storage.entity.MetaInfo;
import it.unisa.beingdigital.storage.entity.util.Livello;
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
 * Questa classe rappresenta il service per la modifica di una risorsa.
 */

@Service
@Transactional
@Validated
public class ModificaRisorsaService {

  @Autowired
  private ArgomentoRepository argomentoRepository;

  @Autowired
  private MetaInfoRepository metaInfoRepository;

  @Autowired
  private DomandaRepository domandaRepository;

  @Autowired
  private GiocoRepository giocoRepository;

  @Autowired
  private RispostaRepository rispostaRepository;

  /**
   * Implementa la funzionalità di modifica di un argomento.
   * Si assume che la corretta formulazione dei parametri sia stata controllata prima
   * di effettuare la chiamata.
   * Tutti i parametri, tranne id, possono essere nulli, se non si vuole modificare quel dato.
   *
   * @param id         id dell'argomento da modificare.
   * @param titolo     Titolo dell'argomento.
   * @param corpo      Testo scritto che compone l'argomento.
   * @param copertina  Copertina dell'argomento.
   * @param metaInfoId Id della meta-info associata all'argomento.
   * @return true se la modifica è andato a buon fine, false altrimenti.
   * @throws jakarta.validation.ConstraintViolationException se l'id risulta null.
   */
  public boolean modificaArgomento(@NotNull Long id, String titolo, String corpo, byte[] copertina,
                                   Long metaInfoId) {
    Optional<Argomento> optionalArgomento = argomentoRepository.findById(id);
    if (optionalArgomento.isEmpty()) {
      return false;
    }

    Argomento argomento = optionalArgomento.get();

    if (metaInfoId != null) {
      Optional<MetaInfo> optionalMetaInfo = metaInfoRepository.findById(metaInfoId);
      if (optionalMetaInfo.isEmpty()) {
        return false;
      }

      argomento.setMetaInfo(optionalMetaInfo.get());
    }

    if (titolo != null) {
      argomento.setTitolo(titolo);
    }

    if (corpo != null) {
      argomento.setCorpo(corpo);
    }

    if (copertina != null) {
      argomento.setCopertina(copertina);
    }

    return true;
  }

  /**
   * Implementa la funzionalità di modifica di una meta-info.
   * Si assume che la corretta formulazione dei parametri sia stata controllata prima
   * di effettuare la chiamata.
   * Tutti i parametri, tranne id, possono essere nulli, se non si vuole modificare quel dato.
   *
   * @param id      id della metainfo da modificare.
   * @param keyword Nome della meta-info.
   * @param livello Livello associato alla meta-info.
   * @param icona   Immagine rappresentativa della meta-info.
   * @return true se la modifica è andata a buon fine, false altrimenti.
   * @throws jakarta.validation.ConstraintViolationException se l'id risulta null.
   */
  public boolean modificaMetaInfo(@NotNull Long id, String keyword, Livello livello, byte[] icona) {
    if (livello == Livello.MASTER) {
      return false;
    }

    Optional<MetaInfo> optional = metaInfoRepository.findById(id);
    if (optional.isEmpty()) {
      return false;
    }

    MetaInfo metaInfo = optional.get();

    if (keyword != null) {
      Optional<MetaInfo> optionalAltro = metaInfoRepository.findByKeyword(keyword);
      if (optionalAltro.isPresent() && !optionalAltro.get().equals(metaInfo)) {
        return false;
      }
      metaInfo.setKeyword(keyword);
    }

    if (livello != null) {
      if (livello != metaInfo.getLivello()) {
        rispostaRepository.deleteByDomandaMetaInfo(metaInfo);
        metaInfo.setLivello(livello);
      }
    }

    if (icona != null) {
      metaInfo.setIcona(icona);
    }

    return true;
  }

  /**
   * Implementa la funzionalità di modifica di una domanda.
   * Si assume che la corretta formulazione dei parametri sia stata controllata prima
   * di effettuare la chiamata.
   * Tutti i parametri, tranne id, possono essere nulli, se non si vuole modificare quel dato.
   *
   * @param id         id della domanda da modificare.
   * @param testo      Testo che compone la domanda.
   * @param corretta   Risposta corretta.
   * @param sbagliata1 Prima risposta sbagliata.
   * @param sbagliata2 Seconda risposta sbagliata.
   * @param sbagliata3 Terza risposta sbagliata.
   * @param metaInfoId Id della meta-info associata alla domanda.
   * @return true se la modifica è andata a buon fine, false altrimenti.
   * @throws jakarta.validation.ConstraintViolationException se l'id risulta null.
   */
  public boolean modificaDomanda(@NotNull Long id, String testo, String corretta, String sbagliata1,
                                 String sbagliata2, String sbagliata3, Long metaInfoId) {
    Optional<Domanda> optional = domandaRepository.findById(id);
    if (optional.isEmpty()) {
      return false;
    }

    Domanda domanda = optional.get();

    if (metaInfoId != null) {
      Optional<MetaInfo> optionalMetaInfo = metaInfoRepository.findById(metaInfoId);
      if (optionalMetaInfo.isEmpty()
          || optionalMetaInfo.get().getLivello() == Livello.CITTADINANZA_DIGITALE) {
        return false;
      }

      domanda.setMetaInfo(optionalMetaInfo.get());
    }

    if (testo != null) {
      domanda.setTesto(testo);
    }

    if (corretta != null) {
      domanda.setCorretta(corretta);
    }

    if (sbagliata1 != null) {
      domanda.setSbagliata1(sbagliata1);
    }

    if (sbagliata2 != null) {
      domanda.setSbagliata2(sbagliata2);
    }

    if (sbagliata3 != null) {
      domanda.setSbagliata3(sbagliata3);
    }

    return true;
  }

  /**
   * Implementa la funzionalità di modifica di un gioco.
   * Si assume che la corretta formulazione dei parametri sia stata controllata prima
   * di effettuare la chiamata.
   * Tutti i parametri, tranne id, possono essere nulli, se non si vuole modificare quel dato.
   *
   * @param id         id del gioco da modificare.
   * @param nome       Nome del gioco.
   * @param path       Path del gioco.
   * @param metaInfoId Id della meta-info associata al gioco.
   * @return true se la modifica è andato a buon fine, false altrimenti.
   * @throws jakarta.validation.ConstraintViolationException se l'id risulta null.
   */
  public boolean modificaGioco(@NotNull Long id, String nome, String path, Long metaInfoId) {
    Optional<Gioco> optional = giocoRepository.findById(id);
    if (optional.isEmpty()) {
      return false;
    }

    Gioco gioco = optional.get();

    if (nome != null) {
      Optional<Gioco> optionalAltro = giocoRepository.findByNome(nome);
      if (optionalAltro.isPresent() && !optionalAltro.get().equals(gioco)) {
        return false;
      }
    }

    if (metaInfoId != null) {
      Optional<MetaInfo> optionalMetaInfo = metaInfoRepository.findById(metaInfoId);
      if (optionalMetaInfo.isEmpty()) {
        return false;
      }

      Optional<Gioco> optionalGioco = giocoRepository.findByMetaInfo(optionalMetaInfo.get());
      if (optionalGioco.isPresent() && !optionalGioco.get().equals(gioco)) {
        return false;
      }

      gioco.setMetaInfo(optionalMetaInfo.get());
    }

    if (nome != null) {
      gioco.setNome(nome);
    }

    if (path != null) {
      gioco.setPath(path);
    }

    return true;
  }
}
