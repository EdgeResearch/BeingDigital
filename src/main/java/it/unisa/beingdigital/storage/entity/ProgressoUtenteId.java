package it.unisa.beingdigital.storage.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ProgressoUtenteId implements Serializable {

    private Long utenteId;
    private String sottoArgomento;

    public ProgressoUtenteId() {
    }

    public ProgressoUtenteId(Long utenteId, String sottoArgomento) {
        this.utenteId = utenteId;
        this.sottoArgomento = sottoArgomento;
    }

    public String getSottoArgomento() {
        return sottoArgomento;
    }
}