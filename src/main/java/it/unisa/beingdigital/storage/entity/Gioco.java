package it.unisa.beingdigital.storage.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Questa classe rappresenta un gioco.
 * Ogni gioco ha una meta-info associata e può essere giocato da ogni utente di equal livello della
 * sua meta-info.
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Gioco {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String nome;

  @Column(nullable = false)
  private String path;

  @OneToOne
  @JoinColumn(nullable = false)
  private MetaInfo metaInfo;

  /**
   * Costruttore per gioco.
   *
   * @param nome     nome del gioco.
   * @param path     path della cartella contenente il gioco compilato, all'interno del server.
   * @param metaInfo metainfo a cui fa riferimento.
   */
  public Gioco(String nome, String path, MetaInfo metaInfo) {
    this.nome = nome;
    this.path = path;
    this.metaInfo = metaInfo;
  }
}
