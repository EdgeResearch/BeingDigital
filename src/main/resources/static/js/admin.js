var lista = document.getElementById("list");
var conf = document.getElementById("conf");
var per = document.getElementById("percentuali");
var acc = document.getElementById("acc");
var prom = document.getElementById("prom");
var promAdmin = document.getElementById("promAdmin");



function showConferma(campo) {
    lista.style.display = "none";
    per.style.display = "none";

    conf.style.display = "flex";


    var eliminato = document.getElementById("utenteEliminato");
    eliminato.value = campo.id;
}

function hideConferma() {
    lista.style.display = "flex";
    per.style.display = "flex";
    conf.style.display = "none";
}


function showConferma2() {

    lista.style.display = "none";
    per.style.display = "none";

    acc.style.display = "flex";

}

function hideConferma2() {
    lista.style.display = "flex";
    per.style.display = "flex";
    acc.style.display = "none";
}


function showConferma3(campo) {

    lista.style.display = "none";
    per.style.display = "none";

    prom.style.display = "flex";


    var eliminato = document.getElementById("utentePromosso");
    eliminato.value = campo.id;

}

function hideConferma3() {
    lista.style.display = "flex";
    per.style.display = "flex";
    prom.style.display = "none";
}

function showConferma4(campo) {

    lista.style.display = "none";
    per.style.display = "none";

    promAdmin.style.display = "flex";


    var eliminato = document.getElementById("utenteAdminPromosso");
    eliminato.value = campo.id;

}

function hideConferma4() {
    lista.style.display = "flex";
    per.style.display = "flex";
    promAdmin.style.display = "none";
}