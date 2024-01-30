package it.unisa.beingdigital.storage.repository;

import it.unisa.beingdigital.storage.entity.Admin;

/**
 * Questa interfaccia rappresenta la repository di un admin.
 * Viene implementata autonomamente da Spring in modo da consentire l'accesso
 * a i dati degli admin presenti nel DB.
 */

public interface AdminRepository extends PersonaGenericRepository<Admin> {
}
