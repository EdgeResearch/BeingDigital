package it.unisa.beingdigital.storage.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Questa classe rappresenta una FlashCard.
 * Ogni FlashCard contiene delle domande e risposte,
 * e fa riferimento a un argomento.
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Flashcard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String domande;

    @Column(nullable = false)
    private String risposte;

    @ManyToOne
    @JoinColumn(name = "argomento_id", nullable = false)
    private Argomento argomento;

    public Flashcard(String domande, String risposte, Argomento argomento) {
        this.domande = domande;
        this.risposte = risposte;
        this.argomento = argomento;
    }
}