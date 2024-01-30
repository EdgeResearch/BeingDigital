package it.unisa.beingdigital.control.gestionerisorse.form;

import it.unisa.beingdigital.storage.entity.util.Livello;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

/**
 * Questa classe rappresenta il form per l'inserimento e la modifica di una MetaInfo.
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MetaInfoForm {

  @NotBlank
  @Size(max = 255)
  private String keyword;

  @NotNull
  private Livello livello;

  @MultipartFileConstraint(maxSize = 102400, mimeType = "image/png")
  private MultipartFile icona;
}
