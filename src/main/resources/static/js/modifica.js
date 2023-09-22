function validateArgomento(obj) {
    var titolo = document.getElementById('titolo');
    if (validateTitolo(titolo) && validateSelect()) {
        console.log("va")
        return true
    } else {
        return false;
    }
}

function validateGioco(obj) {
    var nome = document.getElementById("nome");
    var path = document.getElementById("path");
    if (validateNome(nome) && validatePath(path)) {
        console.log("Vai");
        return true;
    } else {
        return false;
    }
}

function validateMeta(obj) {
    var key = document.getElementById("keyword");
    if (validateNome(key)) {
        console.log("Vai");
        return true;
    } else {
        return false;
    }
}

function validateTitolo(titolo) {
    if (titolo.value.length != 0 && titolo.value.length <= 255) {
        console.log("La lunghezza del titolo è: " + titolo.value.length)
        return true;
    } else if (titolo.value.length == 0) {
        return false;
    }
    toast("Il titolo risulta essere troppo lungo. <br> Inserisci al massimo 255 caratteri.")
    return false
}

function validateNome(nome) {
    if (nome.value.length != 0 && nome.value.length <= 255) {
        console.log("La lunghezza del titolo è: " + nome.value.length)
        return true;
    }
    toast("Il nome risulta essere troppo lungo. <br> Inserisci al massimo 255 caratteri.")
    return false
}

function validateSelect() {
    var tipo = document.getElementById('tipo');
    var meta = document.getElementById('meta');
    console.log(tipo.value);
    console.log(meta.value);
    if (tipo.value == -1) {
        toast("ATTENZIONE! <br>Inserire la tipologia.");
        return false
    } else if (meta.value == -1) {
        toast("ATTENZIONE! <br>Inserire la meta-info.");
        return false
    }
    return true;
}

function validatePath(path) {
    if (path.value.length != 0 && path.value.length <= 255) {
        console.log("La lunghezza del path è: " + path.value.length)
        return true;
    }
    toast("Il path risulta essere troppo lungo. <br> Inserisci al massimo 255 caratteri.")
    return false
}

function validateKey(key) {
    if (key.value.length != 0 && key.value.length <= 255) {
        console.log("La lunghezza della key è: " + key.value.length)
        return true;
    }
    toast("La keyword risulta essere troppo lungo. <br> Inserisci al massimo 255 caratteri.")
    return false
}


function img() {
    toast("ATTEZIONE: inserire l'immagine di peso <br>massimo di 2MB")
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
