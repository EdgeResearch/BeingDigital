package it.unisa.beingdigital.control.gestionerisorse.form;

import it.unisa.beingdigital.storage.entity.AmministratoreCittadini;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


/**
 * Questa classe rappresenta il form per la creazione di un Team.
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TeamForm {

    @NotBlank
    @Size(max = 255)
    private String nome;

    @NotBlank
    @Size(max = 255)
    private String email;
    private String tipoTeam;
    private String città;
    private String classe;
    private String scuola;
}