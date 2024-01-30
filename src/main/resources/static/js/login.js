let siginBtn = document.querySelector('.siginBtn');
let sigupBtn = document.querySelector('.sigupBtn');
let body = document.querySelector('body');

sigupBtn.onclick = function () {
    body.classList.add('slide');
}

siginBtn.onclick = function () {
    body.classList.remove('slide');
}


let passwordLog = document.getElementById('passwordLog');
let passwordReg = document.getElementById('passwordReg');
let occhioLog = document.getElementById('occhioLog');
let occhioReg = document.getElementById('occhioReg');

function showHide(password) {
    if (password) {
        if (passwordLog.type == 'password') {
            passwordLog.setAttribute('type', 'text');
            occhioLog.src = '/img/hide.png';
        } else {
            passwordLog.setAttribute('type', 'password');
            occhioLog.src = '/img/view.png';
        }
    }

    if (!password) {
        if (passwordReg.type == 'password') {
            passwordReg.setAttribute('type', 'text');
            occhioReg.src = '/img/hide.png';
        } else {
            passwordReg.setAttribute('type', 'password');
            occhioReg.src = '/img/view.png';
        }
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

// validazione

function validation_name(input) {

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

    var pattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*\p{P})[A-Za-z\d\p{P}]{8,}$/u;

    if (input == null) {
        toast()
        return false
    }

    if (input.match(pattern)) {
        return true;
    } else {
        console.log("P sbagliata")
        toast("La password deve contenere almeno 8 caratteri, di cui almeno una lettera maiuscola, almeno una minuscola, almeno un numero e almeno un carattere speciale")
        return false;
    }
}

function validate(obj) {

    var email = document.getElementById("LogEmail").value;
    var txt_em = document.getElementById("LogEmail");
    var password = document.getElementById("passwordLog").value;
    var txt_p = document.getElementById("passwordLog");

    if (validation_email(email) && validation_password(password)) {
        console.log("entra1");

        return true;
    } else {
        console.log("entra2");
        txt_em.style.color = "#FF0000FF";
        txt_p.style.color = "#FF0000FF";

        return false;
    }


}


function validateAll(obj) {

    var email = document.getElementById("RegEmail").value;
    var txt_em = document.getElementById("RegEmail");
    var password = document.getElementById("passwordReg").value;
    var txt_p = document.getElementById("passwordReg");
    var nome = document.getElementById("RegNome").value;
    var txt_n = document.getElementById("RegNome");
    var cognome = document.getElementById("RegCognome").value;
    var txt_c = document.getElementById("RegCognome");

    if (validation_email(email) && validation_password(password) && validation_name(nome) && validation_surname(cognome) && validEmail) {
        console.log("entra1");

        return true;
    } else {
        console.log("entra2");
        if (!validation_email(email))
            txt_em.style.color = "#FF0000FF";
        if (!validation_password(password))
            txt_p.style.color = "#FF0000FF";
        if (!validation_name(nome))
            txt_n.style.color = "#FF0000FF";
        if (!validation_surname(cognome))
            txt_c.style.color = "#FF0000FF";

        return false;
    }


}

//ajax
let validEmail = false;
$('#RegEmail').on("change", function () {
    validEmail = false;
    console.log(this.value);

    if (!validation_email(this.value)) {
        return;
    }

    $.post('/existsEmail', {email: this.value}, function (data) {
        var txt_em = document.getElementById("RegEmail");
        if (data.result == true) {
            txt_em.style.color = "#FF0000FF";
            toast("Email già esistente.")
        } else {
            validEmail = true;
            txt_em.style.color = "#3cff46";
            toast("Email valida.")
        }

    });
})