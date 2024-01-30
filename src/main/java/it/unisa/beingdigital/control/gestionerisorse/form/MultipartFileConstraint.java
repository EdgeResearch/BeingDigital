package it.unisa.beingdigital.control.gestionerisorse.form;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotazione per la validazione di un MultipartFile.
 */

@Documented
@Constraint(validatedBy = MultipartFileValidator.class)
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MultipartFileConstraint {

  /**
   * Messaggio da comunicare in caso di errore.
   */
  String message() default "File troppo grande o del formato errato";

  /**
   * Permetti di specificare il gruppo di validazione.
   */
  Class<?>[] groups() default {};

  /**
   * Permette di specificare un payload per trasportare le informazioni sui metadati
   * utilizzate da un client di convalida.
   */
  Class<? extends Payload>[] payload() default {};

  /**
   * Dimensione massima del file.
   */
  long maxSize();

  /**
   * Tipo mime del file.
   */
  String mimeType();
}
