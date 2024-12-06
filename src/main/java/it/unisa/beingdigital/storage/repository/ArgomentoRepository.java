package it.unisa.beingdigital.storage.repository;

import it.unisa.beingdigital.storage.entity.Argomento;
import it.unisa.beingdigital.storage.entity.MetaInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ArgomentoRepository extends JpaRepository<Argomento, Long> {

    @Query("SELECT a FROM Argomento a WHERE a.sottoArgomento = :sottoArgomento")
    List<Argomento> findBySottoArgomento(@Param("sottoArgomento") String sottoArgomento);

    void deleteByMetaInfo(MetaInfo metaInfo);
}