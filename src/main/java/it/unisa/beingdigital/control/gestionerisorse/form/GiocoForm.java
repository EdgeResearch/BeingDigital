package it.unisa.beingdigital.control.gestionerisorse.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Questa classe rappresenta il form per l'inserimento e la modifica di un Gioco.
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class GiocoForm {

  @NotBlank
  @Size(max = 255)
  private String nome;

  @NotBlank
  @Size(max = 255)
  private String path;

  @NotNull
  private Long metaInfoId;
}
