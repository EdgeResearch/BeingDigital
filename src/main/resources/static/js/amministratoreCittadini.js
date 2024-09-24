var lista = document.getElementById("boxTeam");
var conf = document.getElementById("conf");

function showConferma(campo) {
    var idUtente = campo.getAttribute('data-id-utente');
    var codiceTeam = document.querySelector("input[name='codiceTeam']").value;

    console.log("ID Utente: ", idUtente);
    console.log("Codice Team: ", codiceTeam);

    lista.style.display = "none";
    conf.style.display = "flex";


    var idUtenteField = document.querySelector("input[name='idUtente']");
    idUtenteField.value = idUtente;
}

function hideConferma() {
    lista.style.display = "flex";
    conf.style.display = "none";
}
