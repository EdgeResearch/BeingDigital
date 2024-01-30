package it.unisa.beingdigital.storage.repository;

import it.unisa.beingdigital.storage.entity.Domanda;
import it.unisa.beingdigital.storage.entity.MetaInfo;
import it.unisa.beingdigital.storage.entity.Risposta;
import it.unisa.beingdigital.storage.entity.Utente;
import it.unisa.beingdigital.storage.entity.util.RispostaId;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Questa interfaccia rappresenta la repository di una risposta.
 * Viene implementata autonomamente da Spring in modo da consentire l'accesso a i dati delle
 * risposte presenti nel DB.
 */

public interface RispostaRepository extends JpaRepository<Risposta, RispostaId> {

  List<Risposta> findByUtente(Utente utente);

  List<Risposta> findByUtente(Utente utente, Sort sort);

  List<Risposta> findByDomanda(Domanda domanda);

  long countByUtenteAndIndiceSelezione(Utente utente, int indiceSelezione);

  void deleteByUtente(Utente utente);

  void deleteByDomandaMetaInfo(MetaInfo metaInfo);

  void deleteByDomanda(Domanda domanda);
}
