package it.unisa.beingdigital.control.gestionerisorse.form;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

/**
 * Classe per la validazione di un MultipartFile.
 */

public class MultipartFileValidator
    implements ConstraintValidator<MultipartFileConstraint, MultipartFile> {

  private long maxSize;

  private String mimeType;

  @Override
  public void initialize(MultipartFileConstraint constraintAnnotation) {
    ConstraintValidator.super.initialize(constraintAnnotation);

    maxSize = constraintAnnotation.maxSize();
    mimeType = constraintAnnotation.mimeType();
  }

  @Override
  public boolean isValid(MultipartFile multipartFile,
                         ConstraintValidatorContext constraintValidatorContext) {
    if (multipartFile == null || multipartFile.isEmpty()) {
      return true;
    }

    return multipartFile.getSize() <= maxSize && mimeType.equals(multipartFile.getContentType());
  }
}
