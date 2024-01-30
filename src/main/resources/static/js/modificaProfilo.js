function showHide(password) {

    let p_nuova = document.getElementById('p_nuova');
    let p_vecchia = document.getElementById('p_vecchia');
    let occhio_vecchio = document.getElementById('occhio_vecchio');
    let occhio_nuovo = document.getElementById('occhio_nuovo');

    if (password) {
        if (p_vecchia.type == 'password') {
            p_vecchia.setAttribute('type', 'text');
            occhio_vecchio.src = '/img/hide.png';
        } else {
            p_vecchia.setAttribute('type', 'password');
            occhio_vecchio.src = '/img/view.png';
        }
    }

    if (!password) {
        if (p_nuova.type == 'password') {
            p_nuova.setAttribute('type', 'text');
            occhio_nuovo.src = '/img/hide.png';
        } else {
            p_nuova.setAttribute('type', 'password');
            occhio_nuovo.src = '/img/view.png';
        }
    }
}

function validation_name(input) {
    console.log("nome")

    var pattern = /^[A-Za-z]+$/;

    if (input == null) {
        toast()
        return false
    }

    if (input.match(pattern)) {
        return true;
    } else {
        toast("Il nome non rispetta la sintassi");
        return false;
    }
}

function validation_surname(input) {
    console.log("cognome")
    var pattern = /^[A-Za-z ,.']+$/i;

    if (input == null) {
        toast()
        return false
    }

    if (input.match(pattern)) {
        return true;
    } else {
        toast("Il cognome non rispetta la sintassi");
        return false;
    }
}


function validation_email(input) {

    var pattern = /^[^ ]+@[^ ]+.[a-z]{2,3}$/;
    console.log("email")
    if (input == null) {
        return false
    }

    if (input.match(pattern)) {
        return true;
    } else {
        return false;
    }
}

function validation_password(input) {
    console.log("password")
    var pattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*\p{P})[A-Za-z\d\p{P}]{8,}$/u;

    if (input.value.length == 0) {
        return true
    }

    if (input.match(pattern)) {
        return true;
    } else {
        console.log("P sbagliata")
        toast("La password deve contenere almeno 8 caratteri, di cui almeno una lettera maiuscola, almeno una minuscola, almeno un numero e almeno un carattere speciale")
        return false;
    }
}

function toast(txt) {
    var x = document.getElementById("snackbar");
    x.className = "show";
    x.innerHTML = txt;
    setTimeout(function () {
        x.className = x.className.replace("show", "");
    }, 3000);
}

function validate(obj) {

    var email = document.getElementById("email").value;
    var txt_em = document.getElementById("email");
    var nome = document.getElementById("nome").value;
    var txt_n = document.getElementById("nome");
    var cognome = document.getElementById("cognome").value;
    var txt_c = document.getElementById("cognome");
    var password_v = document.getElementById("p_vecchia").value;
    var txt_pv = document.getElementById("p_vecchia");
    var password_n = document.getElementById("p_nuova").value;
    var txt_pn = document.getElementById("p_nuova");

    if (validation_email(email) && validation_name(nome) && validation_surname(cognome) && validation_password(password_v) && validation_password(password_n) && validEmail && validPassword) {
        console.log("entra1");

        return true
    } else {
        console.log("entra2: em" + validation_email(email) + " pv " + validation_password(password_v) + " pn " + validation_password(password_n) + " nome " + validation_name(nome) + " cognome " + validation_surname(cognome));
        if (!validation_email(email))
            txt_em.style.color = "#c80e00";
        if (!validation_password(password_v))
            txt_pv.style.color = "#c80e00";
        if (!validation_password(password_n))
            txt_pn.style.color = "#c80e00";
        if (!validation_name(nome))
            txt_n.style.color = "#c80e00";
        if (!validation_surname(cognome))
            txt_c.style.color = "#c80e00";

        return false;
    }
}


//ajax
let validEmail = true;
$('#email').on("change", function () {
    validEmail = false;
    console.log(this.value);
    var txt_em = document.getElementById("email");

    var vecchia = document.getElementById('email_v').value
    if (vecchia.match(this.value)) {
        validEmail = true;
        txt_em.style.color = "#78c800";
        return;
    }


    if (!validation_email(this.value)) {
        txt_em.style.color = "#c80e00";
        return;
    }

    $.post('/existsEmail', {email: this.value}, function (data) {

        if (data.result == true) {
            txt_em.style.color = "#c80e00";
            toast("Email già esistente.")
        } else {
            validEmail = true;
            txt_em.style.color = "#78c800";
            toast("Email valida.")
        }

    });
})

//ajax
let validPassword = true;
$('#p_vecchia').on("change", function () {
    validPassword = false;
    console.log(this.value);
    var txt_p = document.getElementById("p_vecchia");


    if (!validation_password(this.value)) {
        txt_p.style.color = "#c80e00";
        return;
    }

    if (this.value.length == 0) {
        validPassword = true;
        return
    }

    $.post('/auth/checkPassword', {password: this.value}, function (data) {

        if (data.result == true) {
            txt_p.style.color = "#78c800";
            toast("Password attuale corretta.")
            validPassword = true;
        } else {
            txt_p.style.color = "#c80e00";
            toast("Password attuale sbagliata. <br> Riprovare")
        }

    });
})