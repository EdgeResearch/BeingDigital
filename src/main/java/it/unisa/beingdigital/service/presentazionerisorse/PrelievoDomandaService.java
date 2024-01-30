package it.unisa.beingdigital.service.presentazionerisorse;

import it.unisa.beingdigital.storage.entity.Domanda;
import it.unisa.beingdigital.storage.entity.util.Livello;
import it.unisa.beingdigital.storage.repository.DomandaRepository;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/**
 * Questa classe rappresenta il service per il prelievo delle domande.
 */

@Service
@Transactional(readOnly = true)
@Validated
public class PrelievoDomandaService {

  @Autowired
  private DomandaRepository domandaRepository;

  public Optional<Domanda> getDomanda(@NotNull Long id) {
    return domandaRepository.findById(id);
  }

  /**
   * Implementa la funzionalità di prelievo di tutte le domande ordinate per livello, keyword
   * e testo.
   *
   * @return lista di domande.
   */
  public List<Domanda> getAllDomandeSortedByLivelloKeywordTesto() {
    return domandaRepository.findAll().stream()
        .sorted(Comparator.comparing((Domanda domanda) -> domanda.getMetaInfo().getLivello())
            .thenComparing(domanda -> domanda.getMetaInfo().getKeyword())
            .thenComparing(Domanda::getTesto)).toList();
  }

  /**
   * Implementa la funzionalità di prelievo di una domanda in modo casuale.
   * Si assume che la corretta formulazione del livello sia stata controllata prima
   * di effettuare la chiamata.
   *
   * @param livello livello delle domande.
   * @return lista di domande.
   * @throws jakarta.validation.ConstraintViolationException se livello risulta null.
   */
  public List<Domanda> getDomandeRandom(@NotNull Livello livello) {
    List<Domanda> domande = new ArrayList<>(domandaRepository.findByMetaInfoLivello(livello));
    Collections.shuffle(domande);
    return domande;
  }
}
