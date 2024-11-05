package it.unisa.beingdigital.storage.repository;

import it.unisa.beingdigital.storage.entity.AmministratoreCittadini;
import it.unisa.beingdigital.storage.entity.Team;

import java.util.List;
import java.util.Optional;

import it.unisa.beingdigital.storage.entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Questa interfaccia rappresenta la repository di un Team.
 * Viene implementata autonomamente da Spring in modo da consentire l'accesso a i dati
 * dei team presenti nel DB.
 */

public interface TeamRepository extends JpaRepository<Team, String> {

    Optional<Team> findByCodice(String codice);

    List<Team> findByUtentiContains(Utente utente);

    List<Team> findByAmministratoriCittadiniContains(AmministratoreCittadini amministratoreCittadini);
}

