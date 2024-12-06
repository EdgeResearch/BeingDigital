package it.unisa.beingdigital.control.profilo;

import it.unisa.beingdigital.storage.entity.Persona;
import it.unisa.beingdigital.storage.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.Optional;

@Controller
public class ImmagineController {

    @Autowired
    private PersonaRepository personaRepository;

    @GetMapping("/fotoprofilo/{id}")
    public ResponseEntity<byte[]> getFotoProfilo(@PathVariable Long id) {
        Optional<Persona> persona = personaRepository.findById(id);

        if (persona.isEmpty() || persona.get().getFotoprofilo() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "image/jpeg");
        return new ResponseEntity<>(persona.get().getFotoprofilo(), headers, HttpStatus.OK);
    }
}