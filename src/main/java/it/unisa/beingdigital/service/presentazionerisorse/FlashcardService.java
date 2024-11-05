package it.unisa.beingdigital.service.presentazionerisorse;

import it.unisa.beingdigital.storage.entity.Argomento;
import it.unisa.beingdigital.storage.entity.Flashcard;
import it.unisa.beingdigital.storage.repository.FlashcardRepository;
import it.unisa.beingdigital.storage.repository.ArgomentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class FlashcardService {

    @Autowired
    private FlashcardRepository flashcardRepository;

    @Autowired
    private ArgomentoRepository argomentoRepository;

    /**
     * Recupera una flashcard dal suo ID.
     *
     * @param id ID della flashcard.
     * @return Optional contenente la flashcard se esiste, altrimenti Optional vuoto.
     */
    public Optional<Flashcard> getFlashcardById(Long id) {
        return flashcardRepository.findById(id);
    }

    /**
     * Recupera tutte le flashcard associate a un argomento.
     *
     * @param argomentoId Oggetto Argomento per il quale recuperare le flashcard.
     * @return Lista di flashcard associate all'argomento.
     */
    public List<Flashcard> getFlashCardsByArgomento(Long argomentoId) {
        return flashcardRepository.findByArgomentoId(argomentoId);
    }

    /**
     * Recupera un argomento dal suo ID.
     *
     * @param id ID dell'argomento.
     * @return Optional contenente l'argomento se esiste, altrimenti Optional vuoto.
     */
    public Optional<Argomento> getArgomentoById(Long id) {
        return argomentoRepository.findById(id);
    }
}
