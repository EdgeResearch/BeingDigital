package it.unisa.beingdigital.storage.repository;

import it.unisa.beingdigital.storage.entity.Domanda;
import it.unisa.beingdigital.storage.entity.MetaInfo;
import it.unisa.beingdigital.storage.entity.util.Livello;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Questa interfaccia rappresenta la repository di una domanda.
 * Viene implementata autonomamente da Spring in modo da consentire l'accesso a i
 * dati delle domande presenti nel DB.
 */

public interface DomandaRepository extends JpaRepository<Domanda, Long> {

  List<Domanda> findByMetaInfo(MetaInfo metaInfo);

  long countByMetaInfoLivello(Livello livello);

  List<Domanda> findByMetaInfoLivello(Livello livello);

  void deleteByMetaInfo(MetaInfo metaInfo);
}
