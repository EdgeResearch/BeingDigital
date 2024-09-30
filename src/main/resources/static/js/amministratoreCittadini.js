var team = document.getElementById("team");
var lista = document.getElementById("boxTeam");
var conf = document.getElementById("conf");
var codiceTeamField = document.getElementById("codiceTeam");
var idUtenteField = document.getElementById("idUtente");

function showConferma(campo) {
    document.confForm.action = "/amministratoreCittadini/modificaTeam/espelliUtente";
    document.getElementById('testo-espulsione').innerText = "Sicuro di voler procedere all'espulsione di questo utente?";
    var idUtente = campo.getAttribute('data-id-utente');
    var codiceTeam = campo.closest('.team-box').querySelector("input[name='codiceTeam']").value;

    console.log("ID Utente: ", idUtente);
    console.log("Codice Team: ", codiceTeam);

    lista.style.display = "none";
    team.style.display = "none";
    conf.style.display = "flex";

    codiceTeamField.value = codiceTeam;
    idUtenteField.value = idUtente;
}

function hideConferma() {
    lista.style.display = "flex";
    team.style.display = "flex";
    conf.style.display = "none";
}

function showConferma1(campo){
    document.confForm.action = "/modificaTeamUtente/uscitaTeam";
    document.getElementById('testo-espulsione').innerText = "Sicuro di voler uscire dal team?";

    var idUtente = campo.getAttribute('data-id-utente-loggato');
    var codiceTeam = campo.closest('.team-box').querySelector("input[name='codiceTeam']").value;

    console.log("ID Utente: ", idUtente);
    console.log("Codice Team: ", codiceTeam);

    lista.style.display = "none";
    team.style.display = "none";
    conf.style.display = "flex";

    codiceTeamField.value = codiceTeam;
    idUtenteField.value = idUtente;
}

