package it.unisa.beingdigital.storage.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

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
  private String sottoArgomento;

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

  @Column(nullable = true)
  private String mappa;

  @ElementCollection
  @CollectionTable(name = "flashcard_domande", joinColumns = @JoinColumn(name = "argomento_id"))
  @Column(name = "flashcard_domande")
  private List<String> flashcard_domande;

  @ElementCollection
  @CollectionTable(name = "flashcard_risposte", joinColumns = @JoinColumn(name = "argomento_id"))
  @Column(name = "flashcard_risposte")
  private List<String> flashcard_risposte;

  protected Argomento(String sottoArgomento,String titolo, String corpo, byte[] copertina, MetaInfo metaInfo, String mappa, List<String>flashcard_domande, List<String>flashcard_risposte) {
    this.sottoArgomento = sottoArgomento;
    this.titolo = titolo;
    this.corpo = corpo;
    this.copertina = copertina;
    this.metaInfo = metaInfo;
    this.mappa = mappa;
    this.flashcard_domande = flashcard_domande;
    this.flashcard_risposte = flashcard_risposte;
  }
}
