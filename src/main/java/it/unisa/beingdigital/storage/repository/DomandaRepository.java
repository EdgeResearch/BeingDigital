package it.unisa.beingdigital.storage.repository;

import it.unisa.beingdigital.storage.entity.Domanda;
import it.unisa.beingdigital.storage.entity.MetaInfo;
import it.unisa.beingdigital.storage.entity.util.Livello;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Questa interfaccia rappresenta la repository di una domanda.
 * Viene implementata autonomamente da Spring in modo da consentire l'accesso a i
 * dati delle domande presenti nel DB.
 */

public interface DomandaRepository extends JpaRepository<Domanda, Long> {

  List<Domanda> findByMetaInfo(MetaInfo metaInfo);

  long countByMetaInfoLivello(Livello livello);

  List<Domanda> findByMetaInfoLivello(Livello livello);

  @Query("SELECT d FROM Domanda d JOIN d.metaInfo m JOIN m.argomenti a WHERE a.sottoArgomento = :sottoArgomento AND m.livello = :livello")
  List<Domanda> findAllBySottoArgomentoAndLivello(@Param("sottoArgomento") String sottoArgomento, @Param("livello") Livello livello);

  void deleteByMetaInfo(MetaInfo metaInfo);

  @Query("SELECT COUNT(d) FROM Domanda d JOIN d.metaInfo m JOIN m.argomenti a WHERE m.livello = :livello AND a.sottoArgomento = :sottoArgomento")
  long countByMetaInfo_LivelloAndArgomento_SottoArgomento(@Param("livello") Livello livello, @Param("sottoArgomento") String sottoArgomento);
}