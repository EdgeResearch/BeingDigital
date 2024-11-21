document.addEventListener("DOMContentLoaded", function() {
    const titoloCategoria = document.getElementById("titoloCategoria");
    const btnPrivacy = document.getElementById("btnPrivacy");
    const btnIA = document.getElementById("btnIA");
    const messaggio = document.getElementById("messaggio");
    const percentualiContainer = document.querySelector(".percentuali");
    const isTeam = document.querySelector("#diariodibordo").getAttribute("data-is-team") === 'true';
    const codice = document.querySelector("#diariodibordo").getAttribute("data-codice");

    percentualiContainer.style.display = "none";
    titoloCategoria.style.display = "none";

    function setProgressColor(progressElement, percentage, color) {
        if (percentage === 0) {
            progressElement.setAttribute('data-percent', '0');
            progressElement.style.background = "#ddd";
        } else {
            progressElement.setAttribute('data-percent', percentage);
            progressElement.style.background = `conic-gradient(${color} ${percentage}%, #ddd ${percentage}%)`;
        }
    }

    function mostraCategoria(titolo, categoria) {
        messaggio.style.display = "none";
        titoloCategoria.style.display = "block";
        percentualiContainer.style.display = "grid";

        titoloCategoria.textContent = `Risultati relativi a ${titolo}`;

        const progressElements = [
            { element: document.getElementById("percentualeBase"), color: "red", percentage: 0 },
            { element: document.getElementById("percentualeIntermedio"), color: "green", percentage: 0 },
            { element: document.getElementById("percentualeAvanzato"), color: "orange", percentage: 0 },
            { element: document.getElementById("percentualeMaster"), color: "purple", percentage: 0 }
        ];

        progressElements.forEach(item => {
            item.element.textContent = "0%";
            item.element.classList.remove("visible");
            const progressElement = document.getElementById(item.element.id.replace("percentuale", "progress"));
            progressElement.classList.remove("visible");
            setProgressColor(progressElement, 0, item.color);
        });

        const url = isTeam
            ? `/auth/diariodibordo/percentuali/team/${codice}?categoria=${categoria}`
            : `/auth/diariodibordo/percentuali/utente?categoria=${categoria}`;

        fetch(url)
            .then(response => response.json())
            .then(data => {
                const levels = ["base", "intermedio", "avanzato", "master"];
                levels.forEach((level, index) => {
                    const percentage = data[level];
                    const element = document.getElementById(`percentuale${level.charAt(0).toUpperCase() + level.slice(1)}`);
                    const progressElement = document.getElementById(`progress${level.charAt(0).toUpperCase() + level.slice(1)}`);
                    element.textContent = percentage + "%";
                    setProgressColor(progressElement, percentage, progressElements[index].color);
                });

                progressElements.forEach(item => {
                    item.element.classList.add("visible");
                    const progressElement = document.getElementById(item.element.id.replace("percentuale", "progress"));
                    progressElement.classList.add("visible");
                });
            })
            .catch(error => {
                console.error("Si è verificato un errore:", error);
            });
    }

    btnPrivacy.addEventListener("click", () => mostraCategoria("Privacy", "Privacy"));
    btnIA.addEventListener("click", () => mostraCategoria("Intelligenza Artificiale", "IntelligenzaArtificiale"));
});