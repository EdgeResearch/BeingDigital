package it.unisa.beingdigital.storage.entity;

import it.unisa.beingdigital.storage.entity.util.Livello;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MetaInfo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String keyword;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Livello livello;

  @OneToMany(mappedBy = "metaInfo")
  private List<Argomento> argomenti;

  @Lob
  @Basic
  @Column(nullable = false, length = 102400)
  private byte[] icona;

  public MetaInfo(String keyword, Livello livello, byte[] icona) {
    this.keyword = keyword;
    this.livello = livello;
    this.icona = icona;
  }
}