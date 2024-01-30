package it.unisa.beingdigital.storage.repository;

import it.unisa.beingdigital.storage.entity.Argomento;
import it.unisa.beingdigital.storage.entity.MetaInfo;
import it.unisa.beingdigital.storage.entity.util.Livello;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Questa interfaccia rappresenta la repository di un argomento di tipo generico.
 */

@NoRepositoryBean
public interface ArgomentoGenericRepository<T extends Argomento> extends JpaRepository<T, Long> {

  List<T> findByMetaInfo(MetaInfo metaInfo, Sort sort);

  void deleteByMetaInfo(MetaInfo metaInfo);

  List<T> findByMetaInfoLivello(Livello livello, Sort sort);
}
