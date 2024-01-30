package it.unisa.beingdigital.control.presentazionerisorse;

import it.unisa.beingdigital.service.presentazionerisorse.PrelievoArgomentoService;
import it.unisa.beingdigital.storage.entity.Argomento;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

/**
 * Questa classe rappresenta il controller per il prelievo della copertina di un argomento.
 */

@Controller
@RequestMapping("/copertina")
public class CopertinaController {

  @Autowired
  private PrelievoArgomentoService prelievoArgomentoService;

  /**
   * Implementa il get per la visualizzazione della copertina.
   *
   * @param idArgomento Id dell'argomento.
   * @param response    Oggetto risposta.
   * @throws ResponseStatusException se l'id dell'argomento risulta nullo.
   */
  @GetMapping
  public void get(@RequestParam Long idArgomento, HttpServletResponse response) throws IOException {
    Optional<Argomento> optional = prelievoArgomentoService.getArgomento(idArgomento);
    if (optional.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    response.setContentType("image/jpeg");
    response.getOutputStream().write(optional.get().getCopertina());
  }
}
