// JavaScript file for creating a GoJS diagram

function init() {
    var $ = go.GraphObject.make;
    var myDiagram = $(go.Diagram, "myDiagramDiv", {
        "undoManager.isEnabled": true,
        initialAutoScale: go.Diagram.UniformToFill, // Automatically scale the diagram to fill the viewport
        initialViewportSpot: go.Spot.Center, // Start the diagram centered in the viewport
        initialDocumentSpot: go.Spot.Center, // Center the diagram at the root node
        layout: $(go.TreeLayout, { angle: 90, layerSpacing: 40 })
    });

    function getColor(level) {
        switch (level) {
            case 0: return "#f4e58f"; // Gold for root level
            case 1: return "#a9d8ec"; // SkyBlue for first level
            case 2: return "#a7f3a7"; // LightGreen for second level
            case 3: return "#ffc8d0"; // LightPink for third level
            default: return "#e8e7e7"; // LightGray for deeper levels
        }
    }

    myDiagram.nodeTemplate = $(
        go.Node,
        "Auto",
        $(go.Shape, "RoundedRectangle",
            { stroke: null }, // Remove border
            new go.Binding("fill", "level", getColor)
        ),
        $(
            go.TextBlock,
            {
                margin: 8,
                font: "8pt Montserrat, sans-serif",
                textAlign: "center",
                wrap: go.TextBlock.WrapFit, // Wrap text if it's too long
                maxSize: new go.Size(150, NaN) // Limit the width of the text block
            },
            new go.Binding("text", "key")
        )
    );

    myDiagram.linkTemplate = $(
        go.Link,
        { routing: go.Link.Orthogonal, curve: go.Link.None, corner: 0 }, // Orthogonal routing for straight lines
        $(go.Shape, { strokeWidth: 1, stroke: "#555" }), // Link shape with thinner lines
        $(go.Shape, { toArrow: "Standard", stroke: null, fill: "#555" }) // Arrowhead
    );

    myDiagram.model = new go.GraphLinksModel([
        { key: "Le paure comuni dietro all'Intelligenza Artificiale", level: 0 },
        { key: "Miti sull'Intelligenza Artificiale", level: 1 },
        { key: "L'IA è una 'minaccia' per l'umanità", level: 2 },
        { key: "Si teme che l'IA possa diventare autonoma e fuori controllo", level: 3 },
        { key: "Gli attuali sistemi di IA operano solo sotto supervisione umana", level: 3 },
        { key: "L'IA sostituirà tutti i lavori umani", level: 2 },
        { key: "C'è preoccupazione che l'IA eliminerà milioni di posti di lavoro", level: 3 },
        { key: "L'IA automatizza compiti ripetitivi ma crea anche nuovi lavori", level: 3 },
        { key: "L'IA è autonoma e pensa come un uomo", level: 2 },
        { key: "Si crede che l'IA possa 'pensare' come un essere umano", level: 3 },
        { key: "L'IA esegue compiti specifici senza vera comprensione del mondo", level: 3 },
        { key: "L'IA non ha limiti", level: 2 },
        { key: "Si pensa che l'IA possa risolvere qualsiasi problema", level: 3 },
        { key: "L'IA ha limiti e può avere bias se non addestrata correttamente", level: 3 },
        { key: "Perché l'IA non è una minaccia", level: 1 },
        { key: "Supervisione umana", level: 2 },
        { key: "Tutti i sistemi di IA richiedono supervisione umana", level: 3 },
        { key: "Algoritmi e regole", level: 2 },
        { key: "L'IA segue istruzioni basate su algoritmi creati dagli esseri umani", level: 3 },
        { key: "Discussioni etiche", level: 2 },
        { key: "Il concetto di un'IA 'malvagia' è pura fantascienza", level: 3 },
        { key: "Limiti dell'IA", level: 1 },
        { key: "Non comprende il mondo come gli esseri umani", level: 2 },
        { key: "Può essere soggetta a bias se addestrata con dati incompleti", level: 2 },
        { key: "È necessaria una supervisione costante per evitare errori ingiusti", level: 2 },
        { key: "Conclusione", level: 1 },
        { key: "L'IA non è una minaccia, ma un potente strumento", level: 2 },
        { key: "Le paure nascono da rappresentazioni irreali nei media", level: 2 },
        { key: "Dobbiamo usarla in modo responsabile ed etico", level: 2 }
    ], [
        { from: "Le paure comuni dietro all'Intelligenza Artificiale", to: "Miti sull'Intelligenza Artificiale" },
        { from: "Miti sull'Intelligenza Artificiale", to: "L'IA è una 'minaccia' per l'umanità" },
        { from: "L'IA è una 'minaccia' per l'umanità", to: "Si teme che l'IA possa diventare autonoma e fuori controllo" },
        { from: "L'IA è una 'minaccia' per l'umanità", to: "Gli attuali sistemi di IA operano solo sotto supervisione umana" },
        { from: "Miti sull'Intelligenza Artificiale", to: "L'IA sostituirà tutti i lavori umani" },
        { from: "L'IA sostituirà tutti i lavori umani", to: "C'è preoccupazione che l'IA eliminerà milioni di posti di lavoro" },
        { from: "L'IA sostituirà tutti i lavori umani", to: "L'IA automatizza compiti ripetitivi ma crea anche nuovi lavori" },
        { from: "Miti sull'Intelligenza Artificiale", to: "L'IA è autonoma e pensa come un uomo" },
        { from: "L'IA è autonoma e pensa come un uomo", to: "Si crede che l'IA possa 'pensare' come un essere umano" },
        { from: "L'IA è autonoma e pensa come un uomo", to: "L'IA esegue compiti specifici senza vera comprensione del mondo" },
        { from: "Miti sull'Intelligenza Artificiale", to: "L'IA non ha limiti" },
        { from: "L'IA non ha limiti", to: "Si pensa che l'IA possa risolvere qualsiasi problema" },
        { from: "L'IA non ha limiti", to: "L'IA ha limiti e può avere bias se non addestrata correttamente" },
        { from: "Le paure comuni dietro all'Intelligenza Artificiale", to: "Perché l'IA non è una minaccia" },
        { from: "Perché l'IA non è una minaccia", to: "Supervisione umana" },
        { from: "Supervisione umana", to: "Tutti i sistemi di IA richiedono supervisione umana" },
        { from: "Perché l'IA non è una minaccia", to: "Algoritmi e regole" },
        { from: "Algoritmi e regole", to: "L'IA segue istruzioni basate su algoritmi creati dagli esseri umani" },
        { from: "Perché l'IA non è una minaccia", to: "Discussioni etiche" },
        { from: "Discussioni etiche", to: "Il concetto di un'IA 'malvagia' è pura fantascienza" },
        { from: "Le paure comuni dietro all'Intelligenza Artificiale", to: "Limiti dell'IA" },
        { from: "Limiti dell'IA", to: "Non comprende il mondo come gli esseri umani" },
        { from: "Limiti dell'IA", to: "Può essere soggetta a bias se addestrata con dati incompleti" },
        { from: "Limiti dell'IA", to: "È necessaria una supervisione costante per evitare errori ingiusti" },
        { from: "Le paure comuni dietro all'Intelligenza Artificiale", to: "Conclusione" },
        { from: "Conclusione", to: "L'IA non è una minaccia, ma un potente strumento" },
        { from: "Conclusione", to: "Le paure nascono da rappresentazioni irreali nei media" },
        { from: "Conclusione", to: "Dobbiamo usarla in modo responsabile ed etico" }
    ]);
}

window.addEventListener("DOMContentLoaded", init);
