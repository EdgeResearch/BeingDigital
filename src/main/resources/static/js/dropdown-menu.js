document.addEventListener('DOMContentLoaded', function () {
    const cardBottoms = document.querySelectorAll('.card-bottom');
    let activeDropdown = null;  // Per tenere traccia del dropdown attualmente aperto
    let timeoutId;

    // Funzione per mostrare il dropdown sotto la card-bottom
    function showDropdown(dropdownId, triggerElement) {
        clearTimeout(timeoutId); // Evita che il dropdown si chiuda subito
        if (activeDropdown) {
            activeDropdown.style.display = 'none'; // Nascondi il dropdown attivo precedente
        }
        const dropdown = document.getElementById(dropdownId);
        activeDropdown = dropdown; // Memorizza il dropdown attivo

        // Ottieni le coordinate di card-bottom
        const rect = triggerElement.getBoundingClientRect();

        // Posiziona il dropdown sotto la card-bottom
        dropdown.style.position = 'absolute';
        dropdown.style.left = `${rect.left + window.scrollX - 25}px`;  // Posiziona orizzontalmente in base al div
        dropdown.style.top = `${rect.bottom + window.scrollY}px`;  // Posiziona verticalmente sotto il div
        dropdown.style.display = 'grid'; // Mostra il dropdown
    }

    // Funzione per nascondere il dropdown con ritardo
    function scheduleHideDropdown(dropdownId) {
        timeoutId = setTimeout(function () {
            const dropdown = document.getElementById(dropdownId);
            dropdown.style.display = 'none';
            activeDropdown = null; // Rimuovi il riferimento al dropdown attivo
        }, 200); // Ritardo di 200ms per permettere il passaggio del mouse
    }

    // Aggiungi event listener per ciascuna card-bottom
    cardBottoms.forEach(function (cardBottom) {
        const card = cardBottom.closest('.card');
        const dropdownId = card.getAttribute('data-dropdown');

        // Mostra il dropdown quando il mouse entra nella parte inferiore della card
        cardBottom.addEventListener('mouseenter', function () {
            showDropdown(dropdownId, cardBottom);
        });

        // Pianifica la chiusura del dropdown quando il mouse esce dalla card
        cardBottom.addEventListener('mouseleave', function () {
            scheduleHideDropdown(dropdownId);
        });

        // Gestisce anche il mouseenter e mouseleave sul dropdown stesso
        const dropdown = document.getElementById(dropdownId);
        dropdown.addEventListener('mouseenter', function () {
            clearTimeout(timeoutId); // Evita che si chiuda se il mouse è nel dropdown
        });
        dropdown.addEventListener('mouseleave', function () {
            scheduleHideDropdown(dropdownId); // Chiude il dropdown quando il mouse esce dal dropdown
        });
    });
});