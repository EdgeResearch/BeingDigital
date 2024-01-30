package it.unisa.beingdigital.control.autenticazione;

import it.unisa.beingdigital.service.autenticazione.LogoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Questa classe rappresenta il controller per il logout.
 */

@Controller
@RequestMapping("/logout")
public class LogoutController {

  @Autowired
  private LogoutService logoutService;

  @GetMapping
  public String get() {
    logoutService.logout();
    return "redirect:/";
  }
}
