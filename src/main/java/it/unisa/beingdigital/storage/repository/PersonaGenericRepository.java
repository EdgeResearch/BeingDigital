package it.unisa.beingdigital.storage.repository;

import it.unisa.beingdigital.storage.entity.Persona;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Questa interfaccia rappresenta la repository di una persona di tipo generico.
 */

@NoRepositoryBean
public interface PersonaGenericRepository<T extends Persona> extends JpaRepository<T, Long> {

  Optional<T> findByEmail(String email);

  boolean existsByEmail(String email);
}
