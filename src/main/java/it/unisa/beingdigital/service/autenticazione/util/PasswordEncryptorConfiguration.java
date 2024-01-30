package it.unisa.beingdigital.service.autenticazione.util;

import org.jasypt.util.password.PasswordEncryptor;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Questa classe fornisce l'istanza del bean singleton PasswordEncryptor.
 */

@Configuration
public class PasswordEncryptorConfiguration {

  @Bean
  public PasswordEncryptor passwordEncryptor() {
    return new StrongPasswordEncryptor();
  }
}
