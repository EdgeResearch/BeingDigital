package it.unisa.beingdigital.storage.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Questa classe rappresenta una FlashCard.
 * Ogni FlashCard contiene delle domande e risposte,
 * e fa riferimento a un argomento.
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class FlashCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    private List<String> domande;

    @ElementCollection
    private List<String> risposte;

    @ManyToOne
    @JoinColumn(name = "argomento_id", nullable = false)
    private Argomento argomento;

    public FlashCard(List<String> domande, List<String> risposte, Argomento argomento) {
        this.domande = domande;
        this.risposte = risposte;
        this.argomento = argomento;
    }
}