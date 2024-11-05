package it.unisa.beingdigital.storage.repository;

import it.unisa.beingdigital.storage.entity.Utente;
import it.unisa.beingdigital.storage.entity.util.Livello;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Questa interfaccia rappresenta la repository di un utente.
 * Viene implementata autonomamente da Spring in modo da consentire l'accesso a i dati degli
 * utenti presenti nel DB.
 */

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Long> {

  List<Utente> findAll();

  long countByLivello(Livello livello);

  List<Utente> findByTeamsCodice(String codice);
}
