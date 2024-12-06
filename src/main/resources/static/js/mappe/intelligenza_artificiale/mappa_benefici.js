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
        { key: "Benefici dell'IA per risolvere problemi complessi", level: 0 },
        { key: "Settore sanitario", level: 1 },
        { key: "Diagnosi rapide e accurate", level: 2 },
        { key: "Algoritmi per analisi immagini mediche", level: 3 },
        { key: "Accesso alle cure a distanza", level: 2 },
        { key: "Assistenti virtuali e chatbot IA", level: 3 },
        { key: "Ricerca di farmaci più efficiente", level: 2 },
        { key: "IA per simulare reazioni chimiche", level: 3 },
        { key: "Sviluppo di vaccini per COVID-19", level: 2 },
        { key: "IA per accelerare la ricerca di vaccini", level: 3 },
        { key: "Diagnosi di malattie rare", level: 2 },
        { key: "IA per identificare correlazioni nei dati medici", level: 3 },
        { key: "Settore ambientale", level: 1 },
        { key: "Previsioni climatiche", level: 2 },
        { key: "Algoritmi per prevedere cambiamenti climatici", level: 3 },
        { key: "Previsioni disastri naturali", level: 2 },
        { key: "Sistemi IA per allerta precoce", level: 3 },
        { key: "Ottimizzazione delle risorse", level: 2 },
        { key: "IA per ridurre l'inquinamento e ottimizzare l'energia", level: 3 },
        { key: "Agricoltura intelligente", level: 2 },
        { key: "IA per migliorare le previsioni e ottimizzare l'irrigazione", level: 3 },
        { key: "Settore dell'istruzione", level: 1 },
        { key: "Accesso disomogeneo all'istruzione", level: 2 },
        { key: "Contenuti educativi personalizzati con IA", level: 3 },
        { key: "Monitoraggio del progresso degli studenti", level: 2 },
        { key: "Sistemi di valutazione in tempo reale", level: 3 },
        { key: "Prevenire i conflitti", level: 1 },
        { key: "Previsione tensioni sociali", level: 2 },
        { key: "IA per analizzare dati sociali e geopolitici", level: 3 },
        { key: "Strategie diplomatiche basate su IA", level: 2 },
        { key: "IA per prevenire conflitti attraverso modelli storici", level: 3 },
        { key: "Conclusione: IA come strumento, non minaccia", level: 0 },
        { key: "IA non è intrinsecamente 'cattiva'", level: 1 },
        { key: "Dipende da progettazione, uso e regolamentazione", level: 2 },
        { key: "Ruolo nel risolvere problemi globali", level: 1 },
        { key: "Settori beneficiati: salute, ambiente, istruzione", level: 2 },
        { key: "Bilanciare rischi e benefici", level: 1 },
        { key: "Regolamentazione adeguata per un futuro equo", level: 2 }
    ], [
        { from: "Benefici dell'IA per risolvere problemi complessi", to: "Settore sanitario" },
        { from: "Settore sanitario", to: "Diagnosi rapide e accurate" },
        { from: "Diagnosi rapide e accurate", to: "Algoritmi per analisi immagini mediche" },
        { from: "Settore sanitario", to: "Accesso alle cure a distanza" },
        { from: "Accesso alle cure a distanza", to: "Assistenti virtuali e chatbot IA" },
        { from: "Settore sanitario", to: "Ricerca di farmaci più efficiente" },
        { from: "Ricerca di farmaci più efficiente", to: "IA per simulare reazioni chimiche" },
        { from: "Settore sanitario", to: "Sviluppo di vaccini per COVID-19" },
        { from: "Sviluppo di vaccini per COVID-19", to: "IA per accelerare la ricerca di vaccini" },
        { from: "Settore sanitario", to: "Diagnosi di malattie rare" },
        { from: "Diagnosi di malattie rare", to: "IA per identificare correlazioni nei dati medici" },
        { from: "Benefici dell'IA per risolvere problemi complessi", to: "Settore ambientale" },
        { from: "Settore ambientale", to: "Previsioni climatiche" },
        { from: "Previsioni climatiche", to: "Algoritmi per prevedere cambiamenti climatici" },
        { from: "Settore ambientale", to: "Previsioni disastri naturali" },
        { from: "Previsioni disastri naturali", to: "Sistemi IA per allerta precoce" },
        { from: "Settore ambientale", to: "Ottimizzazione delle risorse" },
        { from: "Ottimizzazione delle risorse", to: "IA per ridurre l'inquinamento e ottimizzare l'energia" },
        { from: "Settore ambientale", to: "Agricoltura intelligente" },
        { from: "Agricoltura intelligente", to: "IA per migliorare le previsioni e ottimizzare l'irrigazione" },
        { from: "Benefici dell'IA per risolvere problemi complessi", to: "Settore dell'istruzione" },
        { from: "Settore dell'istruzione", to: "Accesso disomogeneo all'istruzione" },
        { from: "Accesso disomogeneo all'istruzione", to: "Contenuti educativi personalizzati con IA" },
        { from: "Settore dell'istruzione", to: "Monitoraggio del progresso degli studenti" },
        { from: "Monitoraggio del progresso degli studenti", to: "Sistemi di valutazione in tempo reale" },
        { from: "Benefici dell'IA per risolvere problemi complessi", to: "Prevenire i conflitti" },
        { from: "Prevenire i conflitti", to: "Previsione tensioni sociali" },
        { from: "Previsione tensioni sociali", to: "IA per analizzare dati sociali e geopolitici" },
        { from: "Prevenire i conflitti", to: "Strategie diplomatiche basate su IA" },
        { from: "Strategie diplomatiche basate su IA", to: "IA per prevenire conflitti attraverso modelli storici" },
        { from: "Benefici dell'IA per risolvere problemi complessi", to: "Conclusione: IA come strumento, non minaccia" },
        { from: "Conclusione: IA come strumento, non minaccia", to: "IA non è intrinsecamente 'cattiva'" },
        { from: "IA non è intrinsecamente 'cattiva'", to: "Dipende da progettazione, uso e regolamentazione" },
        { from: "Conclusione: IA come strumento, non minaccia", to: "Ruolo nel risolvere problemi globali" },
        { from: "Ruolo nel risolvere problemi globali", to: "Settori beneficiati: salute, ambiente, istruzione" },
        { from: "Conclusione: IA come strumento, non minaccia", to: "Bilanciare rischi e benefici" },
        { from: "Bilanciare rischi e benefici", to: "Regolamentazione adeguata per un futuro equo" }
    ]);
}

window.addEventListener("DOMContentLoaded", init);
