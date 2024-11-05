package it.unisa.beingdigital.service.presentazionerisorse;

import it.unisa.beingdigital.storage.entity.Argomento;
import it.unisa.beingdigital.storage.entity.ProgressoUtente;
import it.unisa.beingdigital.storage.entity.ProgressoUtenteId;
import it.unisa.beingdigital.storage.entity.Utente;
import it.unisa.beingdigital.storage.entity.util.Livello;
import it.unisa.beingdigital.storage.repository.ArgomentoRepository;
import it.unisa.beingdigital.storage.repository.ProgressoUtenteRepository;
import it.unisa.beingdigital.storage.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProgressoUtenteService {

    @Autowired
    private ProgressoUtenteRepository progressoUtenteRepository;

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private ArgomentoRepository argomentoRepository;

    public void inizializzaProgressi() {
        List<Utente> utenti = utenteRepository.findAll();
        List<Argomento> argomenti = argomentoRepository.findAll();

        for (Utente utente : utenti) {
            for (Argomento argomento : argomenti) {
                ProgressoUtenteId id = new ProgressoUtenteId(utente.getId(), argomento.getSottoArgomento());
                ProgressoUtente progresso = new ProgressoUtente();
                progresso.setId(id);
                progresso.setUtente(utente);
                progresso.setLivello(Livello.BASE);
                progressoUtenteRepository.save(progresso);
            }
        }
    }

    public void inizializzaProgressiPerUtente(Utente utente) {
        List<Argomento> argomenti = argomentoRepository.findAll();

        for (Argomento argomento : argomenti) {
            ProgressoUtente progresso = new ProgressoUtente();
            ProgressoUtenteId id = new ProgressoUtenteId(utente.getId(), argomento.getSottoArgomento());
            progresso.setId(id);
            progresso.setUtente(utente);
            progresso.setLivello(Livello.BASE);
            progressoUtenteRepository.save(progresso);
        }
    }

    /**
     * Recupera il livello di progresso di un utente per un determinato sotto-argomento.
     *
     * @param utente L'utente per cui recuperare il progresso.
     * @param sottoArgomento Il sotto-argomento di interesse.
     * @return L'istanza di ProgressoUtente contenente il livello di progresso per il sotto-argomento.
     */
    public ProgressoUtente getProgressoUtenteBySottoArgomento(Utente utente, String sottoArgomento) {
        return progressoUtenteRepository.findByUtenteAndSottoArgomento(utente, sottoArgomento)
                .orElse(null);
    }

}