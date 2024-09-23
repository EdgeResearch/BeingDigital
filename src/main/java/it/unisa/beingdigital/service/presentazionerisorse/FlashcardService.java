package it.unisa.beingdigital.service.presentazionerisorse;
import it.unisa.beingdigital.storage.entity.FlashCard;
import it.unisa.beingdigital.storage.repository.FlashcardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FlashcardService {

    @Autowired
    private FlashcardRepository flashcardRepository;

    // Metodo per trovare flashcard per argomentoId
    public List<FlashCard> findByArgomentoId(Long argomentoId) {
        return flashcardRepository.findByArgomentoId(argomentoId);
    }
}
