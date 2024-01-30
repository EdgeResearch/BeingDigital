var lista = document.getElementById("list");
var arg = document.getElementById("argomentoRimosso");
var meta = document.getElementById("mammeta");
var gioco = document.getElementById("giocoRimosso");
var dom = document.getElementById("domandaRimossa");

function showConfermaDom(key) {
    console.log("è stata chiamata la domanda con id: " + key.id);
    document.getElementById('id_dom').value = key.id;
    document.getElementById('dom').value = "domanda";
    lista.style.display = "none";
    dom.style.display = "flex";
}

function hideConfermaDom() {
    lista.style.display = "flex";
    dom.style.display = "none";
}

function showConfermaArg(key) {
    console.log("è stata chiamata l'argomento con id: " + key.id);
    document.getElementById('id_arg').value = key.id;
    document.getElementById('arg').value = "argomento";
    lista.style.display = "none";
    arg.style.display = "flex";
}

function hideConfermaArg() {
    lista.style.display = "flex";
    arg.style.display = "none";
}

function showConfermaMeta(key) {
    console.log("è stata chiamata la meta-info con id: " + key.id);
    document.getElementById('id_meta').value = key.id;
    document.getElementById('meta').value = "metainfo";
    lista.style.display = "none";
    meta.style.display = "flex";
}

function hideConfermaMeta() {
    lista.style.display = "flex";
    meta.style.display = "none";
}

function showConfermaGioco(key) {
    console.log("è stata chiamata il gioco con id: " + key.id);
    document.getElementById('id_gioco').value = key.id;
    document.getElementById('gioco').value = "gioco";
    lista.style.display = "none";
    gioco.style.display = "flex";
}

function hideConfermaGioco() {
    lista.style.display = "flex";
    gioco.style.display = "none";
}

//filtri

var LezioneBox = document.getElementById("boxLezioni");
var RaccontiBox = document.getElementById("boxRacconti");
var MetaBox = document.getElementById("boxMeta");
var DomandeBox = document.getElementById("boxDomande");
var GiochiBox = document.getElementById("boxGiochi");

function Tutti() {
    LezioneBox.classList.remove("none");
    RaccontiBox.classList.remove("none");
    MetaBox.classList.remove("none");
    DomandeBox.classList.remove("none");
    GiochiBox.classList.remove("none");
}

function Meta() {
    LezioneBox.classList.add("none");
    RaccontiBox.classList.add("none");
    MetaBox.classList.remove("none");
    DomandeBox.classList.add("none");
    GiochiBox.classList.add("none");
}

function Lezioni() {
    LezioneBox.classList.remove("none");
    RaccontiBox.classList.add("none");
    MetaBox.classList.add("none");
    DomandeBox.classList.add("none");
    GiochiBox.classList.add("none");
}

function Racconti() {
    LezioneBox.classList.add("none");
    RaccontiBox.classList.remove("none");
    MetaBox.classList.add("none");
    DomandeBox.classList.add("none");
    GiochiBox.classList.add("none");
}

function Giochi() {
    LezioneBox.classList.add("none");
    RaccontiBox.classList.add("none");
    MetaBox.classList.add("none");
    DomandeBox.classList.add("none");
    GiochiBox.classList.remove("none");
}

function Domande() {
    LezioneBox.classList.add("none");
    RaccontiBox.classList.add("none");
    MetaBox.classList.add("none");
    DomandeBox.classList.remove("none");
    GiochiBox.classList.add("none");
}