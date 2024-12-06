function cambiaStile(tipo) {

    var classeDiv = document.getElementById("Classe");
    var gruppoDiv = document.getElementById("Group");

    if (classeDiv && gruppoDiv) {
        if (tipo === "classe") {
            classeDiv.style.display = "flex";
            gruppoDiv.style.display = "none";
        } else if (tipo === "gruppo") {
            classeDiv.style.display = "none";
            gruppoDiv.style.display = "flex";
        }
    } else {
        console.warn("Uno o entrambi i div non sono stati trovati:", { classeDiv, gruppoDiv });
    }

    localStorage.setItem("tipoTeam", tipo);

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
    } else if (tipoTeamInput) {
        tipo = tipoTeamInput.value;
    } else {
        console.warn("Nessun tipo di team selezionato");
    }

    if (!tipo) {
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
}

window.onload = function() {
    const tipoSelezionato = localStorage.getItem("tipoTeam");

    if (tipoSelezionato) {
        const radioButton = document.querySelector(`input[name="selezione"][value="${tipoSelezionato}"]`);
        if (radioButton) {
            radioButton.checked = true;
            cambiaStile(tipoSelezionato);
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
            cambiaStile(event.target.value);
        });
    });

    validateForm();
};