package it.unisa.beingdigital.control.autenticazione.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Questa classe rappresenta il form per la registrazione.
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RegistrazioneForm {

  @NotBlank
  @Size(max = 255)
  private String nome;

  @NotBlank
  @Size(max = 255)
  private String cognome;

  @NotBlank
  @Email
  @Size(max = 319)
  private String email;

  @NotBlank
  @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*\\p{Punct})[A-Za-z\\d\\p{Punct}]{8,}$")
  private String password;
}
