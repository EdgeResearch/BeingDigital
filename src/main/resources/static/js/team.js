function cambiaStile(tipo) {
    console.log("Tipo selezionato:", tipo);
    var classeDiv = document.getElementById("Classe");
    var gruppoDiv = document.getElementById("Group");

    if (classeDiv && gruppoDiv) {
        if (tipo === "classe") {
            classeDiv.style.display = "flex";
            gruppoDiv.style.display = "none";
            console.log("Mostra classe, nascondi gruppo");
        } else if (tipo === "gruppo") {
            classeDiv.style.display = "none";
            gruppoDiv.style.display = "flex";
            console.log("Nascondi classe, mostra gruppo");
        }
    } else {
        console.warn("Uno o entrambi i div non sono stati trovati:", { classeDiv, gruppoDiv });
    }

    localStorage.setItem("tipoTeam", tipo);

    // Aggiorna il campo hidden tipoTeam
    const hiddenTipoTeam = document.getElementById("hiddenTipoTeam");
    if (hiddenTipoTeam) {
        hiddenTipoTeam.value = tipo;
    }

    validateForm();
}

function validateForm() {
    const nome = document.getElementById('nome').value.trim();
    const email = document.getElementById('email').value.trim();
    const città = document.getElementById('città') ? document.getElementById('città').value.trim() : '';
    const classe = document.getElementById('classe2') ? document.getElementById('classe2').value.trim() : '';
    const scuola = document.getElementById('scuola') ? document.getElementById('scuola').value.trim() : '';

    const selectedRadio = document.querySelector('input[name="selezione"]:checked');
    const tipoTeamInput = document.getElementById('tipoTeam');

    let tipo = null;
    if (selectedRadio) {
        tipo = selectedRadio.value;
        console.log("Tipo selezionato tramite radio button:", tipo);
    } else if (tipoTeamInput) {
        tipo = tipoTeamInput.value;
        console.log("Tipo precompilato:", tipo);
    } else {
        console.warn("Nessun tipo di team selezionato");
    }

    console.log("Valori correnti:");
    console.log("Nome:", nome);
    console.log("Email:", email);
    console.log("Tipo Team:", tipo);
    console.log("Città:", città);
    console.log("Classe:", classe);
    console.log("Scuola:", scuola);

    if (!tipo) {
        console.log("Nessun tipo di team selezionato, pulsante disabilitato");
        disabilitaPulsanteSubmit();
        return;
    }

    let isValid = nome && email && tipo;
    if (tipo === 'classe') {
        isValid = isValid && classe && scuola;
    } else if (tipo === 'gruppo') {
        isValid = isValid && città;
    }

    const submitButton = document.getElementById('confirmTeam');
    if (isValid) {
        submitButton.disabled = false;
        submitButton.classList.remove('hidden');
        submitButton.style.display = 'block';
        submitButton.style.backgroundColor = '#5b6ed9';
        submitButton.style.color = '#fff';
        submitButton.style.cursor = 'pointer';
        console.log("Pulsante abilitato");
    } else {
        disabilitaPulsanteSubmit();
    }
}

function disabilitaPulsanteSubmit() {
    const submitButton = document.getElementById('confirmTeam');
    submitButton.disabled = true;
    submitButton.classList.add('hidden');
    submitButton.style.backgroundColor = '#ccc';
    submitButton.style.color = '#666';
    submitButton.style.cursor = 'not-allowed';
    console.log("Pulsante disabilitato");
}

window.onload = function() {
    const tipoSelezionato = localStorage.getItem("tipoTeam");

    if (tipoSelezionato) {
        const radioButton = document.querySelector(`input[name="selezione"][value="${tipoSelezionato}"]`);
        if (radioButton) {
            radioButton.checked = true;
            cambiaStile(tipoSelezionato); // Chiamata per impostare il corretto stile
        } else {
            console.warn("Radio button con valore " + tipoSelezionato + " non trovato.");
        }
    }

    document.getElementById('nome').addEventListener('input', validateForm);
    document.getElementById('email').addEventListener('input', validateForm);
    document.getElementById('città').addEventListener('input', validateForm);
    document.getElementById('classe2').addEventListener('input', validateForm);
    document.getElementById('scuola').addEventListener('input', validateForm);

    document.querySelectorAll('input[name="selezione"]').forEach(radio => {
        radio.addEventListener('change', (event) => {
            cambiaStile(event.target.value); // Cambia stile e validazione
        });
    });

    validateForm(); // Chiamata iniziale per la validazione
};