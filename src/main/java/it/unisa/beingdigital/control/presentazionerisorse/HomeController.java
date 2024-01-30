package it.unisa.beingdigital.control.presentazionerisorse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Questa classe rappresenta il controller per la visualizzazione della homepage.
 */

@Controller
@RequestMapping("/")
public class HomeController {

  @GetMapping
  public String get() {
    return "presentazionerisorse/homepage";
  }
}
