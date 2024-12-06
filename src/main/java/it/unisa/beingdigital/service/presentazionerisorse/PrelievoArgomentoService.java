package it.unisa.beingdigital.service.presentazionerisorse;

import it.unisa.beingdigital.storage.entity.Argomento;
import it.unisa.beingdigital.storage.entity.Lezione;
import it.unisa.beingdigital.storage.entity.MetaInfo;
import it.unisa.beingdigital.storage.entity.Racconto;
import it.unisa.beingdigital.storage.entity.Utente;
import it.unisa.beingdigital.storage.entity.util.Livello;
import it.unisa.beingdigital.storage.repository.ArgomentoRepository;
import it.unisa.beingdigital.storage.repository.LezioneRepository;
import it.unisa.beingdigital.storage.repository.MetaInfoRepository;
import it.unisa.beingdigital.storage.repository.RaccontoRepository;
import jakarta.validation.constraints.NotNull;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/**
 * Questa classe rappresenta il service per il prelievo di un argomento.
 */
@Service
@Transactional(readOnly = true)
@Validated
public class PrelievoArgomentoService {

  @Autowired
  private LezioneRepository lezioneRepository;

  @Autowired
  private RaccontoRepository raccontoRepository;

  @Autowired
  private ArgomentoRepository argomentoRepository;

  @Autowired
  private MetaInfoRepository metaInfoRepository;

  @Autowired
  private PrelievoMetaInfoService prelievoMetaInfoService;

  /**
   * Implementa la funzionalità di prelievo di una lezione.
   * Si assume che la corretta formulazione dell'id sia stata controllata prima
   * di effettuare la chiamata.
   *
   * @param id Id della lezione.
   * @return Optional vuoto se la lezione non esiste, pieno altrimenti.
   * @throws jakarta.validation.ConstraintViolationException se l'id risulta null.
   */
  public Optional<Lezione> getLezione(@NotNull Long id) {
    return lezioneRepository.findById(id);
  }

  /**
   * Implementa la funzionalità di prelievo di un racconto.
   * Si assume che la corretta formulazione dell'id sia stata controllata prima
   * di effettuare la chiamata.
   *
   * @param id Id del racconto.
   * @return Optional vuoto se la lezione non esiste, pieno altrimenti.
   * @throws jakarta.validation.ConstraintViolationException se l'id risulta null.
   */
  public Optional<Racconto> getRacconto(@NotNull Long id) {
    return raccontoRepository.findById(id);
  }

  /**
   * Implementa la funzionalità di prelievo di un argomento.
   * Si assume che la corretta formulazione dell'id sia stata controllata prima
   * di effettuare la chiamata.
   *
   * @param id Id dell'argomento.
   * @return Optional vuoto se l'argomento non esiste, pieno altrimenti.
   * @throws jakarta.validation.ConstraintViolationException se l'id risulta null.
   */
  public Optional<Argomento> getArgomento(@NotNull Long id) {
    return argomentoRepository.findById(id);
  }

  /**
   * Implementa la funzionalità di prelievo di tutte le lezioni ordinate per livello della
   * meta-info, keyword e id della lezione.
   *
   * @return lista delle lezioni.
   */
  public List<Lezione> getAllLezioniSortedByLivelloKeywordId() {
    return lezioneRepository.findAll().stream()
            .sorted(Comparator.comparing((Lezione lezione) -> lezione.getMetaInfo().getLivello())
                    .thenComparing(lezione -> lezione.getMetaInfo().getKeyword())
                    .thenComparing(Lezione::getId))
            .toList();
  }

  /**
   * Implementa la funzionalità di prelievo di tutti i racconti ordinati per titolo.
   * Si assume che la corretta formulazione del livello sia stata controllata prima
   * di effettuare la chiamata.
   *
   * @param livello Id della meta-info.
   * @return lista del racconto.
   * @throws jakarta.validation.ConstraintViolationException se il livello risulta null.
   */
  public List<Racconto> getRaccontiSortedByTitolo(@NotNull Livello livello) {
    if (livello == Livello.MASTER) {
      return raccontoRepository.findAll(Sort.by("titolo")).stream()
              .filter(racconto -> racconto.getMetaInfo().getLivello() != Livello.CITTADINANZA_DIGITALE)
              .toList();
    } else {
      return raccontoRepository.findByMetaInfoLivello(livello, Sort.by("titolo"));
    }
  }

  /**
   * Implementa la funzionalità di prelievo di tutti i racconti ordinati per livello della
   * meta-info, keyword e titolo del racconto.
   *
   * @return lista dei racconti.
   */
  public List<Racconto> getAllRaccontiSortedByLivelloKeywordTitolo() {
    return raccontoRepository.findAll().stream()
            .sorted(Comparator.comparing((Racconto racconto) -> racconto.getMetaInfo().getLivello())
                    .thenComparing(racconto -> racconto.getMetaInfo().getKeyword())
                    .thenComparing(Racconto::getTitolo)).toList();
  }

  /**
   * Implementa la funzionalità di prelievo di tutte le lezioni da studiare da un utente
   * ordinate per livello della meta-info, keyword e id della lezione.
   * Si assume che l'utente sia presente nel DB.
   *
   * @param utente Utente per cui prelevare le lezioni.
   * @return Lista di coppie chiave-valore ordinata.
   * @throws jakarta.validation.ConstraintViolationException se l'utente risulta null.
   */
  public List<Map.Entry<MetaInfo, List<Lezione>>> getLezioniDaStudiarePerMetaInfoSortedByLivelloKeywordId(@NotNull Utente utente) {
    return getLezioniPerMetaInfoSortedById(
            prelievoMetaInfoService.getMetaInfoDaStudiareSortedByLivelloKeyword(utente));
  }

  /**
   * Implementa la funzionalità di prelievo di tutte le lezioni per un livello ordinate per livello
   * della meta-info, keyword e id della lezione.
   * Si assume che la corretta formulazione del livello sia stata controllata prima
   * di effettuare la chiamata.
   *
   * @param livello livello delle lezioni da prelevare.
   * @return Lista di coppie chiave-valore ordinata.
   * @throws jakarta.validation.ConstraintViolationException se il livello risulta null.
   */
  public List<Map.Entry<MetaInfo, List<Lezione>>> getLezioniPerMetaInfoSortedByLivelloKeywordId(@NotNull Livello livello) {
    List<MetaInfo> metaInfos;
    if (livello == Livello.MASTER) {
      metaInfos = prelievoMetaInfoService.getAllMetaInfoSortedByLivelloKeyword().stream()
              .filter(metaInfo -> metaInfo.getLivello() != Livello.CITTADINANZA_DIGITALE).toList();
    } else {
      metaInfos = metaInfoRepository.findByLivello(livello, Sort.by("keyword"));
    }

    return getLezioniPerMetaInfoSortedById(metaInfos);
  }

  private List<Map.Entry<MetaInfo, List<Lezione>>> getLezioniPerMetaInfoSortedById(@NotNull List<MetaInfo> metaInfo) {
    List<Map.Entry<MetaInfo, List<Lezione>>> lezioniPerMetaInfo = new ArrayList<>();
    for (MetaInfo metaInfo1 : metaInfo) {
      List<Lezione> lezioni = lezioneRepository.findByMetaInfo(metaInfo1, Sort.by("id"));
      if (!lezioni.isEmpty()) {
        lezioniPerMetaInfo.add(new AbstractMap.SimpleEntry<>(metaInfo1, lezioni));
      }
    }

    return lezioniPerMetaInfo;
  }

  /**
   * Implementa la funzionalità di prelievo di tutte le lezioni per un livello ordinate per livello
   * della meta-info, keyword e id della lezione.
   * Si assume che la corretta formulazione del livello sia stata controllata prima
   * di effettuare la chiamata.
   *
   * @param livello livello delle lezioni da prelevare.
   * @return Lista di coppie chiave-valore ordinata.
   * @throws jakarta.validation.ConstraintViolationException se il livello risulta null.
   */
  public List<Map.Entry<MetaInfo, List<Lezione>>> getLezioniPerMetaInfoSortedByLivelloKeywordIdAndSottoArgomento(
          @NotNull Livello livello, String sottoArgomento) {

    List<MetaInfo> metaInfos;
    if (livello == Livello.MASTER) {
      metaInfos = prelievoMetaInfoService.getAllMetaInfoSortedByLivelloKeyword().stream()
              .filter(metaInfo -> metaInfo.getLivello() != Livello.CITTADINANZA_DIGITALE).toList();
    } else {
      metaInfos = metaInfoRepository.findByLivello(livello, Sort.by("keyword"));
    }

    return metaInfos.stream()
            .map(metaInfo -> Map.entry(
                    metaInfo,
                    lezioneRepository.findByMetaInfoAndSottoArgomento(metaInfo, sottoArgomento).stream()
                            .sorted(Comparator.comparing(Lezione::getId))
                            .toList()
            ))
            .filter(entry -> !entry.getValue().isEmpty())
            .toList();
  }
}
