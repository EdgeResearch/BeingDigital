package it.unisa.beingdigital.service.presentazionerisorse;

import it.unisa.beingdigital.storage.entity.*;
import it.unisa.beingdigital.storage.entity.util.Livello;
import it.unisa.beingdigital.storage.repository.*;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
}