package it.unisa.beingdigital.service.gestionerisorse;

import it.unisa.beingdigital.service.autenticazione.util.PersonaAutenticata;
import it.unisa.beingdigital.storage.entity.*;
import it.unisa.beingdigital.storage.entity.util.Livello;
import it.unisa.beingdigital.storage.repository.*;
import jakarta.validation.constraints.NotNull;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/**
 * Questa classe rappresenta il service per l'inserimento di una risorsa.
 */

@Service
@Transactional
@Validated
public class InserimentoRisorsaService {

  @Autowired
  private ArgomentoRepository argomentoRepository;

  @Autowired
  private MetaInfoRepository metaInfoRepository;

  @Autowired
  private DomandaRepository domandaRepository;

  @Autowired
  private GiocoRepository giocoRepository;

  @Autowired
  private TeamRepository teamRepository;

  @Autowired
  private PersonaAutenticata personaAutenticata;

  /**
   * Implementa la funzionalità d'inserimento di una lezione.
   * Si assume che la corretta formulazione dei parametri sia stata controllata prima
   * di effettuare la chiamata.
   *
   * @param titolo     Titolo della lezione.
   * @param corpo      Testo scritto che compone la lezione.
   * @param copertina  Copertina della lezione.
   * @param metaInfoId Id della meta-info associata alla lezione.
   * @param mappa      Mappa associata alla lezione.
   *
   * @return true se l'inserimento è andato a buon fine, false altrimenti.
   * @throws jakarta.validation.ConstraintViolationException se i parametri risultano null.
   */
  public boolean inserimentoLezione(@NotNull String sottoArgomento,@NotNull String titolo, @NotNull String corpo,
                                    @NotNull byte[] copertina, @NotNull Long metaInfoId, String mappa) {
    Optional<MetaInfo> optional = metaInfoRepository.findById(metaInfoId);
    if (optional.isEmpty()) {
      return false;
    }

    Lezione lezione = new Lezione(sottoArgomento,titolo, corpo, copertina, optional.get(), mappa);
    argomentoRepository.save(lezione);
    return true;
  }

  /**
   * Implementa la funzionalità d'inserimento di un racconto.
   * Si assume che la corretta formulazione dei parametri sia stata controllata prima
   * di effettuare la chiamata.
   *
   * @param titolo     Titolo del racconto.
   * @param corpo      Testo scritto che compone il racconto.
   * @param copertina  Copertina della lezione.
   * @param metaInfoId Id della meta-info associata al racconto.
   * @return true se l'inserimento è andato a buon fine, false altrimenti.
   * @throws jakarta.validation.ConstraintViolationException se i parametri risultano null.
   */
  public boolean inserimentoRacconto(@NotNull String sottoArgomento, @NotNull String titolo, @NotNull String corpo,
                                     @NotNull byte[] copertina, @NotNull Long metaInfoId, String mappa){
    Optional<MetaInfo> optional = metaInfoRepository.findById(metaInfoId);
    if (optional.isEmpty()) {
      return false;
    }

    Racconto racconto = new Racconto(sottoArgomento,titolo, corpo, copertina, optional.get(), mappa);
    argomentoRepository.save(racconto);
    return true;
  }

  /**
   * Implementa la funzionalità d'inserimento di una meta-info.
   * Si assume che la corretta formulazione dei parametri sia stata controllata prima
   * di effettuare la chiamata.
   *
   * @param keyword Nome della meta-info.
   * @param livello Livello associato alla meta-info.
   * @param icona   Immagine rappresentativa della meta-info.
   * @return true se l'inserimento è andata a buon fine, false altrimenti.
   * @throws jakarta.validation.ConstraintViolationException se i parametri risultano null.
   */
  public boolean inserimentoMetaInfo(@NotNull String keyword, @NotNull Livello livello,
                                     @NotNull byte[] icona) {
    if (metaInfoRepository.existsByKeyword(keyword) || livello == Livello.MASTER) {
      return false;
    }

    MetaInfo metaInfo = new MetaInfo(keyword, livello, icona);
    metaInfoRepository.save(metaInfo);
    return true;
  }

  /**
   * Implementa la funzionalità d'inserimento di una domanda.
   * Si assume che la corretta formulazione dei parametri sia stata controllata prima
   * di effettuare la chiamata.
   *
   * @param testo      Testo che compone la domanda.
   * @param corretta   Risposta corretta.
   * @param sbagliata1 Prima risposta sbagliata.
   * @param sbagliata2 Seconda risposta sbagliata.
   * @param sbagliata3 Terza risposta sbagliata.
   * @param metaInfoId Id della meta-info associata alla domanda.
   * @return true se l'inserimento è andata a buon fine, false altrimenti.
   * @throws jakarta.validation.ConstraintViolationException se i parametri risultano null.
   */
  public boolean inserimentoDomanda(@NotNull String testo, @NotNull String corretta,
                                    @NotNull String sbagliata1, @NotNull String sbagliata2,
                                    @NotNull String sbagliata3, @NotNull Long metaInfoId) {
    Optional<MetaInfo> optional = metaInfoRepository.findById(metaInfoId);
    if (optional.isEmpty() || optional.get().getLivello() == Livello.CITTADINANZA_DIGITALE) {
      return false;
    }

    Domanda domanda =
        new Domanda(testo, corretta, sbagliata1, sbagliata2, sbagliata3, optional.get());
    domandaRepository.save(domanda);
    return true;
  }

  /**
   * Implementa la funzionalità d'inserimento di un gioco.
   * Si assume che la corretta formulazione dei parametri sia stata controllata prima
   * di effettuare la chiamata.
   *
   * @param nome       Nome del gioco.
   * @param path       Path del gioco.
   * @param metaInfoId Id della meta-info associata al gioco.
   * @return true se l'inserimento è andato a buon fine, false altrimenti.
   * @throws jakarta.validation.ConstraintViolationException se i parametri risultano null.
   */
  public boolean inserimentoGioco(@NotNull String nome, @NotNull String path,
                                  @NotNull Long metaInfoId) {
    Optional<MetaInfo> optional = metaInfoRepository.findById(metaInfoId);
    if (optional.isEmpty()) {
      return false;
    }

    MetaInfo metaInfo = optional.get();
    if (giocoRepository.existsByMetaInfo(metaInfo)) {
      return false;
    }

    if (giocoRepository.existsByNome(nome)) {
      return false;
    }

    Gioco gioco = new Gioco(nome, path, metaInfo);
    giocoRepository.save(gioco);
    return true;
  }

  /**
   * metodo per generare il codice del team
   */
  private String generateShortCode(int length) {
    String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    SecureRandom random = new SecureRandom();
    StringBuilder code = new StringBuilder(length);

    for (int i = 0; i < length; i++) {
      int index = random.nextInt(chars.length());
      code.append(chars.charAt(index));
    }

    return code.toString();
  }

  /**
   * Implementa la funzionalità di creazione di un Team.
   * Si assume che la corretta formulazione dei parametri sia stata controllata prima
   * di effettuare la chiamata.
   * Tutti i parametri, tranne codice, possono essere nulli, se non si vuole modificare quel dato.
   *
   * @param nome      nome del Team.
   * @param email     tipo di email che bisogna possedere per poter partecipare al team.
   * @param tipoTeam  identifica se il team è un Gruppo o una Classe.
   * @return true se la creazione è andata a buon fine, false altrimenti.
   * @throws jakarta.validation.ConstraintViolationException se il codice risulta null.
   */
  public boolean inserimentoTeam(@NotNull String nome, @NotNull String email, String tipoTeam, String città, String classe1, String scuola){

    String codice = generateShortCode(7);

    List<AmministratoreCittadini> amministratoriCittadini = new ArrayList<>();

    Optional<Persona> personaOpt = personaAutenticata.getPersona();

    personaOpt.ifPresent(persona -> {

      if (persona instanceof HibernateProxy) {
        persona = (Persona) ((HibernateProxy) persona).getHibernateLazyInitializer().getImplementation();
      }

      if (persona instanceof AmministratoreCittadini) {
        AmministratoreCittadini amministratoreCittadini = (AmministratoreCittadini) persona;
        amministratoriCittadini.add(amministratoreCittadini);
      }
    });

    if (tipoTeam.equals("gruppo")){
      codice = "G-" + codice;
      Gruppo gruppo = new Gruppo(codice, nome, null, amministratoriCittadini, email, città);
      teamRepository.save(gruppo);
    } else if (tipoTeam.equals("classe")){
      codice = "C-" + codice;
      Classe classe = new Classe(codice, nome, null, amministratoriCittadini, email, classe1, scuola);
      teamRepository.save(classe);
    }

    Optional<Team> optional = teamRepository.findById(codice);

    if (optional.isEmpty()){
      return false;
    }
    return true;
  }

}
