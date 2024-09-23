package it.unisa.beingdigital.control.presentazionerisorse;

import it.unisa.beingdigital.service.presentazionerisorse.FlashcardService;
import it.unisa.beingdigital.storage.entity.FlashCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class FlashcardController {

    @Autowired
    private FlashcardService flashcardService;

    @GetMapping("/flashcards")
    public String getFlashcards(Model model, @RequestParam("argomento_id") Long argomentoId) {
        List<FlashCard> flashcards = flashcardService.findByArgomentoId(argomentoId);
        model.addAttribute("flashcards", flashcards);
        return "flashcard";
    }
}
