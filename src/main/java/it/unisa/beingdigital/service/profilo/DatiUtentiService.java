package it.unisa.beingdigital.service.profilo;

import it.unisa.beingdigital.storage.entity.ProgressoUtente;
import it.unisa.beingdigital.storage.entity.Utente;
import it.unisa.beingdigital.storage.entity.util.Livello;
import it.unisa.beingdigital.storage.repository.DomandaRepository;
import it.unisa.beingdigital.storage.repository.ProgressoUtenteRepository;
import it.unisa.beingdigital.storage.repository.RispostaRepository;
import it.unisa.beingdigital.storage.repository.UtenteRepository;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Transactional(readOnly = true)
@Validated
public class DatiUtentiService {

  @Autowired
  private UtenteRepository utenteRepository;

  @Autowired
  private RispostaRepository rispostaRepository;

  @Autowired
  private DomandaRepository domandaRepository;

  @Autowired
  private ProgressoUtenteRepository progressoUtenteRepository;

  public List<Utente> getAllUtenti() {
    return utenteRepository.findAll();
  }

  public int getPercentualeUtenti(@NotNull Livello livello) {
    long totaleUtenti = utenteRepository.count();
    long utentiLivello = utenteRepository.countByLivello(livello);

    return (int) (utentiLivello / (double) totaleUtenti * 100);
  }

  public int getPercentualeCompletamento(@NotNull Utente utente) {
    long risposteEsatte = rispostaRepository.countByUtenteAndIndiceSelezione(utente, 0);
    long domandeTotali = domandaRepository.countByMetaInfoLivello(utente.getLivello());

    return (int) (risposteEsatte / (double) domandeTotali * 100);
  }

  public double calcolaPercentualeCompletamento(@NotNull Utente utente, String sottoArgomento) {
    Optional<ProgressoUtente> progressoUtente = progressoUtenteRepository.findByUtenteAndSottoArgomento(utente, sottoArgomento);
    if (progressoUtente.isEmpty()) {
      throw new IllegalArgumentException("Nessun progresso trovato per l'utente e il sottoargomento specificati");
    }

    Livello livelloUtente = progressoUtente.get().getLivello();

    long risposteEsatte = rispostaRepository.countByUtenteAndIndiceSelezioneAndDomanda_MetaInfo_Argomento_SottoArgomento(
            utente, 0, sottoArgomento);

    long domandeTotali = domandaRepository.countByMetaInfo_LivelloAndArgomento_SottoArgomento(
            livelloUtente, sottoArgomento);

    if (domandeTotali == 0) {
      return 0;
    }

    return (int) (risposteEsatte / (double) domandeTotali * 100);
  }
}