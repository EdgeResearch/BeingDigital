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
 * Questa classe rappresenta il form di login.
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class LoginForm {

  @NotBlank
  @Email
  @Size(max = 319)
  private String email;

  @NotBlank
  @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*\\p{Punct})[A-Za-z\\d\\p{Punct}]{8,}$")
  private String password;
  /*La password deve contenere almeno 8 caratteri, di cui almeno una lettera maiuscola,
      almeno una minuscola, almeno un numero e almeno un carattere speciale*/
}
