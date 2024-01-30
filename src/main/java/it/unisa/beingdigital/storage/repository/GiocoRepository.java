package it.unisa.beingdigital.storage.repository;

import it.unisa.beingdigital.storage.entity.Gioco;
import it.unisa.beingdigital.storage.entity.MetaInfo;
import it.unisa.beingdigital.storage.entity.util.Livello;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Questa interfaccia rappresenta la repository di un gioco.
 * Viene implementata autonomamente da Spring in modo da consentire l'accesso a i
 * dati dei giochi presenti nel DB.
 */

public interface GiocoRepository extends JpaRepository<Gioco, Long> {

  Optional<Gioco> findByNome(String nome);

  Optional<Gioco> findByMetaInfo(MetaInfo metaInfo);

  List<Gioco> findByMetaInfoLivello(Livello livello, Sort sort);

  void deleteByMetaInfo(MetaInfo metaInfo);

  boolean existsByNome(String nome);

  boolean existsByMetaInfo(MetaInfo metaInfo);
}
