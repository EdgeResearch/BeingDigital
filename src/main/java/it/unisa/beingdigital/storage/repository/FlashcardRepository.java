package it.unisa.beingdigital.storage.repository;

import it.unisa.beingdigital.storage.entity.Flashcard;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlashcardRepository extends JpaRepository<Flashcard, Long> {

    List<Flashcard> findByArgomentoId(Long argomentoId);
}
