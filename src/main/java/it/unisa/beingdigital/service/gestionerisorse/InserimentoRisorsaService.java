package it.unisa.beingdigital.service.gestionerisorse;

import it.unisa.beingdigital.storage.entity.Domanda;
import it.unisa.beingdigital.storage.entity.Gioco;
import it.unisa.beingdigital.storage.entity.Lezione;
import it.unisa.beingdigital.storage.entity.MetaInfo;
import it.unisa.beingdigital.storage.entity.Racconto;
import it.unisa.beingdigital.storage.entity.util.Livello;
import it.unisa.beingdigital.storage.repository.ArgomentoRepository;
import it.unisa.beingdigital.storage.repository.DomandaRepository;
import it.unisa.beingdigital.storage.repository.GiocoRepository;
import it.unisa.beingdigital.storage.repository.MetaInfoRepository;
import jakarta.validation.constraints.NotNull;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/**
 * Questa classe rappresenta il service per l'inserimento di una risorsa.
 */

@Service
@Transactional
@Validated
public class InserimentoRisorsaService {

  @Autowired
  private ArgomentoRepository argomentoRepository;

  @Autowired
  private MetaInfoRepository metaInfoRepository;

  @Autowired
  private DomandaRepository domandaRepository;

  @Autowired
  private GiocoRepository giocoRepository;

  /**
   * Implementa la funzionalità d'inserimento di una lezione.
   * Si assume che la corretta formulazione dei parametri sia stata controllata prima
   * di effettuare la chiamata.
   *
   * @param titolo     Titolo della lezione.
   * @param corpo      Testo scritto che compone la lezione.
   * @param copertina  Copertina della lezione.
   * @param metaInfoId Id della meta-info associata alla lezione.
   * @return true se l'inserimento è andato a buon fine, false altrimenti.
   * @throws jakarta.validation.ConstraintViolationException se i parametri risultano null.
   */
  public boolean inserimentoLezione(@NotNull String titolo, @NotNull String corpo,
                                    @NotNull byte[] copertina, @NotNull Long metaInfoId) {
    Optional<MetaInfo> optional = metaInfoRepository.findById(metaInfoId);
    if (optional.isEmpty()) {
      return false;
    }

    Lezione lezione = new Lezione(titolo, corpo, copertina, optional.get());
    argomentoRepository.save(lezione);
    return true;
  }

  /**
   * Implementa la funzionalità d'inserimento di un racconto.
   * Si assume che la corretta formulazione dei parametri sia stata controllata prima
   * di effettuare la chiamata.
   *
   * @param titolo     Titolo del racconto.
   * @param corpo      Testo scritto che compone il racconto.
   * @param copertina  Copertina della lezione.
   * @param metaInfoId Id della meta-info associata al racconto.
   * @return true se l'inserimento è andato a buon fine, false altrimenti.
   * @throws jakarta.validation.ConstraintViolationException se i parametri risultano null.
   */
  public boolean inserimentoRacconto(@NotNull String titolo, @NotNull String corpo,
                                     @NotNull byte[] copertina, @NotNull Long metaInfoId) {
    Optional<MetaInfo> optional = metaInfoRepository.findById(metaInfoId);
    if (optional.isEmpty()) {
      return false;
    }

    Racconto racconto = new Racconto(titolo, corpo, copertina, optional.get());
    argomentoRepository.save(racconto);
    return true;
  }

  /**
   * Implementa la funzionalità d'inserimento di una meta-info.
   * Si assume che la corretta formulazione dei parametri sia stata controllata prima
   * di effettuare la chiamata.
   *
   * @param keyword Nome della meta-info.
   * @param livello Livello associato alla meta-info.
   * @param icona   Immagine rappresentativa della meta-info.
   * @return true se l'inserimento è andata a buon fine, false altrimenti.
   * @throws jakarta.validation.ConstraintViolationException se i parametri risultano null.
   */
  public boolean inserimentoMetaInfo(@NotNull String keyword, @NotNull Livello livello,
                                     @NotNull byte[] icona) {
    if (metaInfoRepository.existsByKeyword(keyword) || livello == Livello.MASTER) {
      return false;
    }

    MetaInfo metaInfo = new MetaInfo(keyword, livello, icona);
    metaInfoRepository.save(metaInfo);
    return true;
  }

  /**
   * Implementa la funzionalità d'inserimento di una domanda.
   * Si assume che la corretta formulazione dei parametri sia stata controllata prima
   * di effettuare la chiamata.
   *
   * @param testo      Testo che compone la domanda.
   * @param corretta   Risposta corretta.
   * @param sbagliata1 Prima risposta sbagliata.
   * @param sbagliata2 Seconda risposta sbagliata.
   * @param sbagliata3 Terza risposta sbagliata.
   * @param metaInfoId Id della meta-info associata alla domanda.
   * @return true se l'inserimento è andata a buon fine, false altrimenti.
   * @throws jakarta.validation.ConstraintViolationException se i parametri risultano null.
   */
  public boolean inserimentoDomanda(@NotNull String testo, @NotNull String corretta,
                                    @NotNull String sbagliata1, @NotNull String sbagliata2,
                                    @NotNull String sbagliata3, @NotNull Long metaInfoId) {
    Optional<MetaInfo> optional = metaInfoRepository.findById(metaInfoId);
    if (optional.isEmpty() || optional.get().getLivello() == Livello.CITTADINANZA_DIGITALE) {
      return false;
    }

    Domanda domanda =
        new Domanda(testo, corretta, sbagliata1, sbagliata2, sbagliata3, optional.get());
    domandaRepository.save(domanda);
    return true;
  }

  /**
   * Implementa la funzionalità d'inserimento di un gioco.
   * Si assume che la corretta formulazione dei parametri sia stata controllata prima
   * di effettuare la chiamata.
   *
   * @param nome       Nome del gioco.
   * @param path       Path del gioco.
   * @param metaInfoId Id della meta-info associata al gioco.
   * @return true se l'inserimento è andato a buon fine, false altrimenti.
   * @throws jakarta.validation.ConstraintViolationException se i parametri risultano null.
   */
  public boolean inserimentoGioco(@NotNull String nome, @NotNull String path,
                                  @NotNull Long metaInfoId) {
    Optional<MetaInfo> optional = metaInfoRepository.findById(metaInfoId);
    if (optional.isEmpty()) {
      return false;
    }

    MetaInfo metaInfo = optional.get();
    if (giocoRepository.existsByMetaInfo(metaInfo)) {
      return false;
    }

    if (giocoRepository.existsByNome(nome)) {
      return false;
    }

    Gioco gioco = new Gioco(nome, path, metaInfo);
    giocoRepository.save(gioco);
    return true;
  }
}
