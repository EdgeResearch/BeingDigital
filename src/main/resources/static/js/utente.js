var lista = document.getElementById("list");
var conf = document.getElementById("conf");


function showConferma() {
    lista.style.display = "none";
    conf.style.display = "flex";
}

function hideConferma() {
    lista.style.display = "flex";
    conf.style.display = "none";
}

