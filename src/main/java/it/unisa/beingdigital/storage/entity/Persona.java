package it.unisa.beingdigital.storage.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Questa classe rappresenta una persona.
 * Per persona si intende il generico utilizzatore del sito.
 */

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@ToString
public class Persona {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String nome;

  @Column(nullable = false)
  private String cognome;

  @Column(nullable = false, length = 319, unique = true)
  private String email;

  @Column(nullable = false)
  private String password;

  @Lob
  @Basic
  @Column(nullable = true, length = 102400)
  private byte[] fotoprofilo;

  @Column(nullable = true, length = 350)
  private String biografia;

  protected Persona(String nome, String cognome, String email, String password, byte[] fotoprofilo, String biografia) {
    this.nome = nome;
    this.cognome = cognome;
    this.email = email;
    this.password = password;
    this.fotoprofilo = fotoprofilo;
    this.biografia = biografia;
  }
}
