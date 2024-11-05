package it.unisa.beingdigital.service.presentazionerisorse;

import it.unisa.beingdigital.service.profilo.DatiUtentiService;
import it.unisa.beingdigital.storage.entity.*;
import it.unisa.beingdigital.storage.entity.util.Livello;
import it.unisa.beingdigital.storage.repository.DomandaRepository;
import it.unisa.beingdigital.storage.repository.RispostaRepository;
import it.unisa.beingdigital.storage.repository.UtenteRepository;
import it.unisa.beingdigital.storage.repository.ProgressoUtenteRepository;
import jakarta.validation.constraints.NotNull;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/**
 * Questa classe rappresenta il service per il test.
 */

@Service
@Transactional
@Validated
public class TestService {

  @Autowired
  private RispostaRepository rispostaRepository;

  @Autowired
  private DomandaRepository domandaRepository;

  @Autowired
  private DatiUtentiService datiUtentiService;

  @Autowired
  private UtenteRepository utenteRepository;

  @Autowired
  private ProgressoUtenteRepository progressoUtenteRepository;

  /**
   * Sostituisce le risposte attuali dell'utente con quelle fornite.
   *
   * @param risposte nuove risposte, sottoforma di lista di coppie id domanda - risposta.
   * @param utente   utente a cui sostituire le risposte.
   * @throws IllegalArgumentException se l'id di una domanda non è valido.
   */
  private void replaceRisposte(List<AbstractMap.SimpleEntry<Long, String>> risposte,
                               @NotNull Utente utente) {
    rispostaRepository.deleteByUtente(utente);
    rispostaRepository.flush();

    List<Risposta> rispostaEntities = new ArrayList<>(risposte.size());

    for (Map.Entry<Long, String> risposta : risposte) {
      Domanda domanda =
              domandaRepository.findById(risposta.getKey()).orElseThrow(IllegalArgumentException::new);

      Risposta rispostaEntity = new Risposta(utente, domanda, null);

      if (domanda.getCorretta().equals(risposta.getValue())) {
        rispostaEntity.setIndiceSelezione(0);
      } else if (domanda.getSbagliata1().equals(risposta.getValue())) {
        rispostaEntity.setIndiceSelezione(1);
      } else if (domanda.getSbagliata2().equals(risposta.getValue())) {
        rispostaEntity.setIndiceSelezione(2);
      } else if (domanda.getSbagliata3().equals(risposta.getValue())) {
        rispostaEntity.setIndiceSelezione(3);
      } else {
        throw new IllegalArgumentException();
      }

      rispostaEntities.add(rispostaEntity);
    }

    rispostaRepository.saveAll(rispostaEntities);
  }

  /**
   * Verifica se l'utente ha completato il test al 100% e nel caso gli aumenta il livello
   * associato al sotto-argomento.
   *
   * @param utente Utente a cui aumentare il livello.
   * @param argomento L'argomento associato al sotto-argomento.
   * @return true se il livello è stato aumentato, false altrimenti.
   * @throws IllegalArgumentException se il livello dell'utente non è uno valido per l'aumento.
   */
  private boolean aumentaLivello(@NotNull Utente utente, @NotNull Argomento argomento) {
    if (datiUtentiService.getPercentualeCompletamento(utente) >= 100) {

      ProgressoUtente progresso = progressoUtenteRepository.findByUtenteAndSottoArgomento(utente, argomento.getSottoArgomento())
              .orElse(new ProgressoUtente(new ProgressoUtenteId(utente.getId(), argomento.getSottoArgomento()), utente, Livello.BASE));

      Livello nuovoLivello = Livello.getSuccessivo(progresso.getLivello());
      progresso.setLivello(nuovoLivello);

      progressoUtenteRepository.save(progresso);

      rispostaRepository.deleteByUtente(utente);
      utenteRepository.save(utente);
      return true;
    }
    return false;
  }

  /**
   * Permette di salvare i risultati del test dell'utente fornito.
   * Se il test viene superato al 100% viene anche aumentato il livello dell'utente.
   *
   * @param risposte risposte alle domande del test,
   *                 sottoforma di lista di coppie id domanda - risposta.
   * @param utente   utente che ha effettuato il test.
   * @param argomento argomento associato al test.
   * @return true se il test è stato superato al 100%, false altrimenti.
   * @throws IllegalArgumentException se l'id di una domanda non è valido, oppure se il livello
   *                                  dell'utente non è valido per essere aumentato.
   */
  public boolean test(List<AbstractMap.SimpleEntry<Long, String>> risposte, @NotNull Utente utente, @NotNull Argomento argomento) {
    replaceRisposte(risposte, utente);
    return aumentaLivello(utente, argomento);
  }

}