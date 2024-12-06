package it.unisa.beingdigital.control.presentazionerisorse;

import it.unisa.beingdigital.service.presentazionerisorse.FlashcardService;
import it.unisa.beingdigital.storage.entity.Argomento;
import it.unisa.beingdigital.storage.entity.Flashcard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;

/**
 * Controller per la gestione delle FlashCard.
 */
@Controller
@RequestMapping("/flashcard")
public class FlashcardController {

    @Autowired
    private FlashcardService flashcardService;

    /**
     * Visualizza una singola flashcard.
     *
     * @param id    ID della flashcard da visualizzare.
     * @param model Modello da passare alla vista.
     * @return Nome della vista per visualizzare la flashcard.
     */
    @GetMapping("/{id}")
    public String getFlashCard(@PathVariable Long id, Model model) {
        Optional<Flashcard> optionalFlashCard = flashcardService.getFlashcardById(id);

        if (optionalFlashCard.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "FlashCard non trovata");
        }

        model.addAttribute("flashCard", optionalFlashCard.get());
        return "presentazionerisorse/flashcard";
    }

    /**
     * Visualizza tutte le flashcard associate a un argomento.
     *
     * @param argomentoId ID dell'argomento associato.
     * @param model       Modello da passare alla vista.
     * @return Nome della vista per visualizzare le flashcard.
     */
    @GetMapping
    public String getFlashCardsByArgomento(@RequestParam("argomento_id") Long argomentoId, Model model) {
        Optional<Argomento> optionalArgomento = flashcardService.getArgomentoById(argomentoId);

        if (optionalArgomento.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Argomento non valido");
        }

        List<Flashcard> flashCards = flashcardService.getFlashCardsByArgomento(optionalArgomento.get().getId());
        model.addAttribute("flashCards", flashCards);
        model.addAttribute("argomento", optionalArgomento.get());

        return "presentazionerisorse/flashcards";
    }
}
