package it.unisa.beingdigital.storage.repository;

import it.unisa.beingdigital.storage.entity.*;
import it.unisa.beingdigital.storage.entity.util.RispostaId;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Questa interfaccia rappresenta la repository di una risposta.
 * Viene implementata autonomamente da Spring in modo da consentire l'accesso a i dati delle
 * risposte presenti nel DB.
 */

public interface RispostaRepository extends JpaRepository<Risposta, RispostaId> {

  List<Risposta> findByUtente(Utente utente);

  List<Risposta> findByUtente(Utente utente, Sort sort);

  long countByUtenteAndIndiceSelezione(Utente utente, int indiceSelezione);

  void deleteByUtente(Utente utente);

  void deleteByDomandaMetaInfo(MetaInfo metaInfo);

  void deleteByDomanda(Domanda domanda);

  @Query("SELECT COUNT(r) FROM Risposta r JOIN r.domanda d JOIN d.metaInfo m JOIN m.argomenti a " +
          "WHERE r.utente = :utente AND r.indiceSelezione = :indiceSelezione AND a.sottoArgomento = :sottoArgomento")
  long countByUtenteAndIndiceSelezioneAndDomanda_MetaInfo_Argomento_SottoArgomento(
          @Param("utente") Utente utente,
          @Param("indiceSelezione") int indiceSelezione,
          @Param("sottoArgomento") String sottoArgomento);
}
