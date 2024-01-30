package it.unisa.beingdigital.storage.repository;

import it.unisa.beingdigital.storage.entity.Lezione;

/**
 * Questa interfaccia rappresenta la repository di una lezione.
 * Viene implementata autonomamente da Spring in modo da consentire l'accesso a i dati
 * delle lezioni presenti nel DB.
 */

public interface LezioneRepository extends ArgomentoGenericRepository<Lezione> {
}
