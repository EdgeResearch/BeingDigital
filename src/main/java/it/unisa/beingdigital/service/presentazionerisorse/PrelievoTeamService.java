package it.unisa.beingdigital.service.presentazionerisorse;

import it.unisa.beingdigital.storage.entity.*;
import it.unisa.beingdigital.storage.entity.util.Livello;
import it.unisa.beingdigital.storage.repository.*;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/**
 * Questa classe rappresenta il service per il prelievo delle informazioni relative a dei Team.
 */

@Service
@Transactional(readOnly = true)
@Validated
public class PrelievoTeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private AmministratoreCittadiniRepository amministratoreCittadiniRepository;

    @Autowired
    private ProgressoUtenteRepository progressoUtenteRepository;
    @Autowired
    private UtenteRepository utenteRepository;

    public Optional<Team> getTeam(@NotNull String codice) {
        return teamRepository.findById(codice);
    }

    /**
     * Implementa la funzionalità di prelievo di tutte le informazioni di un Team.
     *
     * @return lista di Team.
     */
    public List<Team> getAllTeam() {
        return teamRepository.findAll().stream().toList();
    }

    public List<Team> getTeamsForAmministratore(Long amministratoreId) {
        AmministratoreCittadini amministratoreCittadini = amministratoreCittadiniRepository.findById(amministratoreId)
                .orElseThrow(() -> new IllegalStateException("Amministratore non trovato con id " + amministratoreId));
        return amministratoreCittadini.getTeams();
    }

    public List<Team> getTeamsForUtente(Long utenteId) {
        Utente utente = utenteRepository.findById(utenteId).orElseThrow(() -> new IllegalStateException("Utente non trovato con id " + utenteId));
        return utente.getTeams();
    }

    public List<Utente> getUtentiForTeam(String codice) {
        return utenteRepository.findByTeamsCodice(codice);
    }

    public List<AmministratoreCittadini> getAmministratoriForTeam(String codice) {
        return amministratoreCittadiniRepository.findByTeamsCodice(codice);
    }

    public double calcolaPercentualeUtentiConLivello(@NotNull Team team, @NotNull Livello livello, @NotNull String sottoArgomento) {
        List<Utente> utentiTeam = team.getUtenti();

        if (utentiTeam == null || utentiTeam.isEmpty()) {
            return 0;
        }

        long count = utentiTeam.stream()
                .filter(utente -> {
                    Optional<ProgressoUtente> progresso = progressoUtenteRepository.findByUtenteAndSottoArgomento(utente, sottoArgomento);
                    return progresso.isPresent() && progresso.get().getLivello().equals(livello);
                })
                .count();

        return (int) ((count / (double) utentiTeam.size()) * 100);
    }
}