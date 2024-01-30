package it.unisa.beingdigital.storage.repository;

import it.unisa.beingdigital.storage.entity.Argomento;

/**
 * Questa interfaccia rappresenta la repository di un argomento.
 * Viene implementata autonomamente da Spring in modo da consentire l'accesso a i dati
 * degli argomenti presenti nel DB.
 */

public interface ArgomentoRepository extends ArgomentoGenericRepository<Argomento> {
}
