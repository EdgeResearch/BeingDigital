package it.unisa.beingdigital.storage.repository;

import it.unisa.beingdigital.storage.entity.AmministratoreCittadini;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Questa interfaccia rappresenta la repository di un amministratore di cittadini.
 * Viene implementata autonomamente da Spring in modo da consentire l'accesso
 * a i dati degli amministratori dri cittadini presenti nel DB.
 */

public interface AmministratoreCittadiniRepository extends JpaRepository<AmministratoreCittadini, Long> {

    List<AmministratoreCittadini> findByTeamsCodice(String codice);

}