package it.unisa.beingdigital.storage.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Questa classe rappresenta un argomento.
 * Un argomento può essere un racconto o una lezione.
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@ToString
public class Argomento {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String titolo;

  @Lob
  @Basic
  @Column(nullable = false, length = 16777215)
  private String corpo;

  @Lob
  @Basic
  @Column(nullable = false, length = 2097152)
  private byte[] copertina;

  @ManyToOne
  @JoinColumn(nullable = false)
  private MetaInfo metaInfo;

  protected Argomento(String titolo, String corpo, byte[] copertina, MetaInfo metaInfo) {
    this.titolo = titolo;
    this.corpo = corpo;
    this.copertina = copertina;
    this.metaInfo = metaInfo;
  }
}
