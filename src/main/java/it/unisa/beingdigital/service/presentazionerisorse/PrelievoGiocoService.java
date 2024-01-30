package it.unisa.beingdigital.service.presentazionerisorse;

import it.unisa.beingdigital.storage.entity.Gioco;
import it.unisa.beingdigital.storage.entity.Utente;
import it.unisa.beingdigital.storage.entity.util.Livello;
import it.unisa.beingdigital.storage.repository.GiocoRepository;
import jakarta.validation.constraints.NotNull;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/**
 * Questa classe rappresenta il service per il prelievo dei giochi.
 */

@Service
@Transactional(readOnly = true)
@Validated
public class PrelievoGiocoService {

  @Autowired
  private GiocoRepository giocoRepository;

  @Autowired
  private PrelievoMetaInfoService prelievoMetaInfoService;

  public Optional<Gioco> getGioco(@NotNull Long id) {
    return giocoRepository.findById(id);
  }

  /**
   * Implementa la funzionalità di prelievo di tutti i giochi ordinate per livello, keyword
   * e testo.
   *
   * @return lista di giochi.
   */
  public List<Gioco> getAllGiochiSortedByLivelloKeyword() {
    return giocoRepository.findAll().stream()
        .sorted(Comparator.comparing((Gioco gioco) -> gioco.getMetaInfo().getLivello())
            .thenComparing(gioco -> gioco.getMetaInfo().getKeyword())).toList();
  }

  /**
   * Implementa la funzionalità di prelievo di tutti i giochi ordinate per nome.
   * Si assume che la corretta formulazione del livello sia stata controllata prima
   * di effettuare la chiamata.
   *
   * @param livello livello dei giochi.
   * @return lista di giochi.
   * @throws jakarta.validation.ConstraintViolationException se il livello risulta null.
   */
  public List<Gioco> getGiochiSortedByNome(@NotNull Livello livello) {
    if (livello == Livello.MASTER) {
      return giocoRepository.findAll(Sort.by("nome")).stream()
          .filter(gioco -> gioco.getMetaInfo().getLivello() != Livello.CITTADINANZA_DIGITALE)
          .toList();
    } else {
      return giocoRepository.findByMetaInfoLivello(livello, Sort.by("nome"));
    }
  }

  /**
   * Implementa la funzionalità di prelievo di tutti i giochi da studiare di un utente
   * ordinati per nome.
   * Si assume che l'utente sia presente nel DB.
   *
   * @param utente utente per cui prelevare i giochi.
   * @return lista di giochi.
   * @throws jakarta.validation.ConstraintViolationException se l'utente risulta null.
   */
  public List<Gioco> getGiochiDaStudiareSortedByNome(@NotNull Utente utente) {
    return prelievoMetaInfoService.getMetaInfoDaStudiareSortedByLivelloKeyword(utente).stream()
        .map(giocoRepository::findByMetaInfo)
        .map(gioco -> gioco.orElse(null))
        .filter(Objects::nonNull)
        .sorted(Comparator.comparing(Gioco::getNome))
        .toList();
  }
}
