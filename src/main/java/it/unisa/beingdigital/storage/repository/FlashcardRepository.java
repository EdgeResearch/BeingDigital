package it.unisa.beingdigital.storage.repository;

import it.unisa.beingdigital.storage.entity.FlashCard;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FlashcardRepository extends JpaRepository<FlashCard, Long> {

    // Trova tutte le flashcard per un dato argomento_id
    List<FlashCard> findByArgomentoId(Long argomentoId);
}
