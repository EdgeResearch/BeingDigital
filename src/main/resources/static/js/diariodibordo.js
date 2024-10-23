document.addEventListener("DOMContentLoaded", function() {
    var titoloCategoria = document.getElementById("titoloCategoria");
    var btnPrivacy = document.getElementById("btnPrivacy");
    var btnIA = document.getElementById("btnIA");
    var percentualiPrivacy = document.getElementById("percentualiPrivacy");

    var livelloUtente = document.getElementById("diariodibordo").getAttribute("data-livello");

    function mostraCategoria(titolo, mostraPercentuali) {
        titoloCategoria.textContent = titolo;
        percentualiPrivacy.style.display = mostraPercentuali ? "grid" : "none";
    }

    impostaPercentuali(livelloUtente);

    // Event listener per il pulsante Privacy
    btnPrivacy.addEventListener("click", function() {
        mostraCategoria("Risultati su Privacy e Sicurezza Online", true);
    });

    // Event listener per il pulsante IA
    btnIA.addEventListener("click", function() {
        mostraCategoria("Risultati su Intelligenza Artificiale", false);
    });
});