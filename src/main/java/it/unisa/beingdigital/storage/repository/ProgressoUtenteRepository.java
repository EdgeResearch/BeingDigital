package it.unisa.beingdigital.storage.repository;

import it.unisa.beingdigital.storage.entity.ProgressoUtente;
import it.unisa.beingdigital.storage.entity.ProgressoUtenteId;
import it.unisa.beingdigital.storage.entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface ProgressoUtenteRepository extends JpaRepository<ProgressoUtente, ProgressoUtenteId> {

    @Query("SELECT p FROM ProgressoUtente p WHERE p.utente = ?1 AND p.id.sottoArgomento = ?2")
    Optional<ProgressoUtente> findByUtenteAndSottoArgomento(Utente utente, String sottoArgomento);

}