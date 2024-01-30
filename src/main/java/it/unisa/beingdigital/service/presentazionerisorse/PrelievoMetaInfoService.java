package it.unisa.beingdigital.service.presentazionerisorse;

import it.unisa.beingdigital.storage.entity.Domanda;
import it.unisa.beingdigital.storage.entity.Gioco;
import it.unisa.beingdigital.storage.entity.MetaInfo;
import it.unisa.beingdigital.storage.entity.Risposta;
import it.unisa.beingdigital.storage.entity.Utente;
import it.unisa.beingdigital.storage.entity.util.Livello;
import it.unisa.beingdigital.storage.repository.DomandaRepository;
import it.unisa.beingdigital.storage.repository.GiocoRepository;
import it.unisa.beingdigital.storage.repository.MetaInfoRepository;
import it.unisa.beingdigital.storage.repository.RispostaRepository;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/**
 * Questa classe rappresenta il service per il prelievo delle meta-info.
 */

@Service
@Transactional(readOnly = true)
@Validated
public class PrelievoMetaInfoService {

  @Autowired
  private MetaInfoRepository metaInfoRepository;

  @Autowired
  private GiocoRepository giocoRepository;

  @Autowired
  private DomandaRepository domandaRepository;

  @Autowired
  private RispostaRepository rispostaRepository;

  public Optional<MetaInfo> getMetaInfo(@NotNull Long id) {
    return metaInfoRepository.findById(id);
  }

  /**
   * Implementa la funzionalità di prelievo di tutti le meta-info ordinate per livello e keyword.
   *
   * @return lista di meta-info.
   */
  public List<MetaInfo> getAllMetaInfoSortedByLivelloKeyword() {
    return metaInfoRepository.findAll().stream()
        .sorted(Comparator.comparing(MetaInfo::getLivello).thenComparing(MetaInfo::getKeyword))
        .toList();
  }

  public List<MetaInfo> getMetaInfoSortedByLivelloKeywordWithoutCittadinanza() {
    return getAllMetaInfoSortedByLivelloKeyword().stream()
        .filter(metaInfo -> metaInfo.getLivello() != Livello.CITTADINANZA_DIGITALE).toList();
  }

  /**
   * Implementa la funzionalità di prelievo di tutte le meta-info non associate a nessun gioco.
   * Si assume che la corretta formulazione dell'id sia stata controllata prima
   * di effettuare la chiamata.
   *
   * @param includeMetaInfoId id di una meta-info che deve obbligatoriamente apparire nella lista.
   * @return lista di meta-info.
   */
  public List<MetaInfo> getMetaInfoSenzaGiocoSortedByLivelloKeyword(Long includeMetaInfoId) {
    Set<MetaInfo> metaInfoConGioco =
        giocoRepository.findAll().stream().map(Gioco::getMetaInfo)
            .filter(metaInfo -> !metaInfo.getId().equals(includeMetaInfoId))
            .collect(Collectors.toSet());

    Set<MetaInfo> metaInfo = new HashSet<>(metaInfoRepository.findAll());
    metaInfo.removeAll(metaInfoConGioco);

    return metaInfo.stream()
        .sorted(Comparator.comparing(MetaInfo::getLivello).thenComparing(MetaInfo::getKeyword))
        .toList();
  }

  /**
   * Implementa la funzionalità di prelievo di tutte le meta-info da studiare da un utente
   * ordinate per livello della meta-info e keyword.
   * Si assume che l'utente sia presente nel DB.
   *
   * @param utente Utente per cui prelevare le meta-info.
   * @return Lista di MetaInfo.
   * @throws jakarta.validation.ConstraintViolationException se l'utente risulta null.
   */
  public List<MetaInfo> getMetaInfoDaStudiareSortedByLivelloKeyword(@NotNull Utente utente) {
    List<MetaInfo> metaInfoDaStudiare;

    if (utente.getLivello() != Livello.MASTER) {
      Set<Domanda> domandeEsatte =
          rispostaRepository.findByUtente(utente).stream()
              .filter(risposta -> risposta.getIndiceSelezione() == 0).map(Risposta::getDomanda)
              .collect(Collectors.toSet());

      List<MetaInfo> metaInfoLivello =
          metaInfoRepository.findByLivello(utente.getLivello(), Sort.by("keyword"));
      metaInfoDaStudiare = new ArrayList<>();
      for (MetaInfo metaInfo : metaInfoLivello) {
        Set<Domanda> domandeMetaInfo = new HashSet<>(domandaRepository.findByMetaInfo(metaInfo));
        if (domandeMetaInfo.isEmpty()) {
          metaInfoDaStudiare.add(metaInfo);
          continue;
        }
        domandeMetaInfo.removeAll(domandeEsatte);
        if (!domandeMetaInfo.isEmpty()) {
          metaInfoDaStudiare.add(metaInfo);
        }
      }
    } else {
      metaInfoDaStudiare = getAllMetaInfoSortedByLivelloKeyword().stream()
          .filter(metaInfo -> metaInfo.getLivello() != Livello.CITTADINANZA_DIGITALE).toList();
    }

    return metaInfoDaStudiare;
  }
}
