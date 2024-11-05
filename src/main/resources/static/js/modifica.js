function validateArgomento(obj) {
    var titolo = document.getElementById('titolo');
    if (validateTitolo(titolo) && validateSelect()) {
        return true
    } else {
        return false;
    }
}

function validateGioco(obj) {
    var nome = document.getElementById("nome");
    var path = document.getElementById("path");
    if (validateNome(nome) && validatePath(path) && validateMetaInf()) {
        return true;
    } else {
        return false;
    }
}

function validateMeta(obj) {
    var key = document.getElementById("keyword");
    if (validateKey(key) && validateLivello()) {
        return true;
    }
    return false
}

function validateTeam(obj) {
    var nome = document.getElementById("nome");
    var email = document.getElementById("email");
    if (validateTesto(nome) && validation_email(email)) {
        return true;
    }
    return false
}

function validateDomanda(obj) {
    var testo = document.getElementById("testo");
    var cor = document.getElementById("corretta");
    var s1 = document.getElementById("sbagliata1");
    var s2 = document.getElementById("sbagliata2");
    var s3 = document.getElementById("sbagliata3");

    if (validateTesto(testo) && validateTesto(cor) && validateTesto(s1) && validateTesto(s2) && validateTesto(s3) && validateMetaInf()) {
        return true;
    }
    return false
}

function validateTesto(testo) {
    if (testo.value.length != 0 && testo.value.length <= 255) {
        return true;
    } else if (testo.value.length == 0) {
        return false;
    }
    toast("ATTENZIONE <br> Inserisci al massimo 255 caratteri.")
    return false
}

function validateTitolo(titolo) {
    if (titolo.value.length != 0 && titolo.value.length <= 255) {
        return true;
    } else if (titolo.value.length == 0) {
        return false;
    }
    toast("Il titolo risulta essere troppo lungo. <br> Inserisci al massimo 255 caratteri.")
    return false
}

function validateNome(nome) {
    if (nome.value.length != 0 && nome.value.length <= 255) {
        return true;
    }
    toast("Il nome risulta essere troppo lungo. <br> Inserisci al massimo 255 caratteri.")
    return false
}

function validateSelect() {
    var tipo = document.getElementById('tipo');
    var meta = document.getElementById('meta');
    if (tipo.value == -1) {
        toast("ATTENZIONE! <br>Inserire la tipologia.");
        return false
    } else if (meta.value == -1) {
        toast("ATTENZIONE! <br>Inserire la meta-info.");
        return false
    }
    return true;
}

function validateLivello() {
    var livello = document.getElementById('livello');

    if (livello.value == -1) {
        toast("ATTENZIONE! <br>Inserire il livello.");
        return false
    }
    return true;
}

function validateMetaInf() {
    var meta = document.getElementById('meta');

    if (meta.value == -1) {
        toast("ATTENZIONE! <br>Inserire la meta-info.");
        return false
    }
    return true;
}

function validatePath(path) {
    if (path.value.length != 0 && path.value.length <= 255) {

        return true;
    }
    toast("Il path risulta essere troppo lungo. <br> Inserisci al massimo 255 caratteri.")
    return false
}

function validateKey(key) {
    if (key.value.length != 0 && key.value.length <= 255) {

        return true;
    } else if (key.value.length == 0) {
        return false;
    }
    toast("La keyword risulta essere troppo lungo. <br> Inserisci al massimo 255 caratteri.")
    return false
}


function img() {
    toast("ATTEZIONE: inserire l'immagine di peso <br>massimo di 2MB")
}

function icon() {
    toast("ATTEZIONE: inserire l'immagine di peso <br>massimo di 100KB")
}

//toast

function toast(txt) {
    var x = document.getElementById("snackbar");
    x.className = "show";
    x.innerHTML = txt;
    setTimeout(function () {
        x.className = x.className.replace("show", "");
    }, 3000);
}

function validation_email(input) {

    var pattern = /@[^ ]+.[a-z]{2,3}$/;

    if (input == null) {
        return false
    }

    if (input.match(pattern)) {
        return true;
    } else {
        return false;
    }


}