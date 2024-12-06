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
        { key: "Etica e bias negli algoritmi di IA", level: 0 },
        { key: "Cos'è il bias negli algoritmi di IA?", level: 1 },
        { key: "Dati di addestramento", level: 2 },
        { key: "Bias derivante da dati con pregiudizi o non rappresentativi", level: 3 },
        { key: "Scelte degli algoritmi", level: 2 },
        { key: "Alcuni algoritmi più suscettibili ai bias", level: 3 },
        { key: "Interpretazione dei risultati", level: 2 },
        { key: "Interpretazioni errate possono introdurre bias", level: 3 },
        { key: "Tipi di bias negli algoritmi di IA", level: 1 },
        { key: "Bias di selezione", level: 2 },
        { key: "Dati di addestramento non rappresentativi della popolazione generale", level: 3 },
        { key: "Esempio: Riconoscimento facciale su persone di una sola etnia", level: 3 },
        { key: "Bias di misura", level: 2 },
        { key: "Metriche inaccurate o influenzate da fattori esterni", level: 3 },
        { key: "Esempio: Algoritmo di assunzione con pregiudizi culturali", level: 3 },
        { key: "Bias di conferma", level: 2 },
        { key: "Modelli che confermano ipotesi preesistenti", level: 3 },
        { key: "Esempio: Ricercatore che cerca pattern specifici ignorando anomalie", level: 3 },
        { key: "Bias cognitivi", level: 2 },
        { key: "Pregiudizi umani influenzano la progettazione degli algoritmi", level: 3 },
        { key: "Le conseguenze del bias negli algoritmi di IA", level: 1 },
        { key: "Discriminazione", level: 2 },
        { key: "Algoritmi che discriminano gruppi possono portare a risultati ingiusti", level: 3 },
        { key: "Erosione della Fiducia", level: 2 },
        { key: "Bias mina la fiducia del pubblico nell'IA", level: 3 },
        { key: "Rafforzamento delle Disuguaglianze", level: 2 },
        { key: "Bias perpetua disuguaglianze esistenti nella società", level: 3 },
        { key: "Etica e responsabilità nell'IA", level: 1 },
        { key: "Giustizia", level: 2 },
        { key: "Garantire che gli algoritmi siano equi e non discriminatori", level: 3 },
        { key: "Trasparenza", level: 2 },
        { key: "Decisioni algoritmiche spiegabili e comprensibili", level: 3 },
        { key: "Responsabilità", level: 2 },
        { key: "Stabilire responsabilità per le decisioni prese dagli algoritmi", level: 3 },
        { key: "Privacy", level: 2 },
        { key: "Protezione dei dati personali deve essere prioritaria", level: 3 },
        { key: "Strategia di mitigazione dei bias", level: 1 },
        { key: "Preprocessing", level: 2 },
        { key: "Mitigazione del bias nei dati", level: 3 },
        { key: "Bilanciamento dei dati", level: 3 },
        { key: "Rimozione di attributi sensibili", level: 3 },
        { key: "Dati sintetici per gruppi sottorappresentati", level: 3 },
        { key: "In-processing", level: 2 },
        { key: "Mitigazione durante l'addestramento del modello", level: 3 },
        { key: "Regularizzazione per incoraggiare l'equità", level: 3 },
        { key: "Algoritmi fairness-aware", level: 3 },
        { key: "Post-processing", level: 2 },
        { key: "Mitigazione dopo l'addestramento del modello", level: 3 },
        { key: "Correzione dei risultati per ridurre disparità", level: 3 },
        { key: "Equalized odds", level: 3 },
        { key: "Conclusione", level: 1 },
        { key: "L'importanza dell'etica e della riduzione del bias", level: 2 },
        { key: "Garantire un'IA responsabile per un futuro equo", level: 3 }
    ], [
        { from: "Etica e bias negli algoritmi di IA", to: "Cos'è il bias negli algoritmi di IA?" },
        { from: "Cos'è il bias negli algoritmi di IA?", to: "Dati di addestramento" },
        { from: "Dati di addestramento", to: "Bias derivante da dati con pregiudizi o non rappresentativi" },
        { from: "Cos'è il bias negli algoritmi di IA?", to: "Scelte degli algoritmi" },
        { from: "Scelte degli algoritmi", to: "Alcuni algoritmi più suscettibili ai bias" },
        { from: "Cos'è il bias negli algoritmi di IA?", to: "Interpretazione dei risultati" },
        { from: "Interpretazione dei risultati", to: "Interpretazioni errate possono introdurre bias" },
        { from: "Etica e bias negli algoritmi di IA", to: "Tipi di bias negli algoritmi di IA" },
        { from: "Tipi di bias negli algoritmi di IA", to: "Bias di selezione" },
        { from: "Bias di selezione", to: "Dati di addestramento non rappresentativi della popolazione generale" },
        { from: "Bias di selezione", to: "Esempio: Riconoscimento facciale su persone di una sola etnia" },
        { from: "Tipi di bias negli algoritmi di IA", to: "Bias di misura" },
        { from: "Bias di misura", to: "Metriche inaccurate o influenzate da fattori esterni" },
        { from: "Bias di misura", to: "Esempio: Algoritmo di assunzione con pregiudizi culturali" },
        { from: "Tipi di bias negli algoritmi di IA", to: "Bias di conferma" },
        { from: "Bias di conferma", to: "Modelli che confermano ipotesi preesistenti" },
        { from: "Bias di conferma", to: "Esempio: Ricercatore che cerca pattern specifici ignorando anomalie" },
        { from: "Tipi di bias negli algoritmi di IA", to: "Bias cognitivi" },
        { from: "Bias cognitivi", to: "Pregiudizi umani influenzano la progettazione degli algoritmi" },
        { from: "Etica e bias negli algoritmi di IA", to: "Le conseguenze del bias negli algoritmi di IA" },
        { from: "Le conseguenze del bias negli algoritmi di IA", to: "Discriminazione" },
        { from: "Discriminazione", to: "Algoritmi che discriminano gruppi possono portare a risultati ingiusti" },
        { from: "Le conseguenze del bias negli algoritmi di IA", to: "Erosione della Fiducia" },
        { from: "Erosione della Fiducia", to: "Bias mina la fiducia del pubblico nell'IA" },
        { from: "Le conseguenze del bias negli algoritmi di IA", to: "Rafforzamento delle Disuguaglianze" },
        { from: "Rafforzamento delle Disuguaglianze", to: "Bias perpetua disuguaglianze esistenti nella società" },
        { from: "Etica e bias negli algoritmi di IA", to: "Etica e responsabilità nell'IA" },
        { from: "Etica e responsabilità nell'IA", to: "Giustizia" },
        { from: "Giustizia", to: "Garantire che gli algoritmi siano equi e non discriminatori" },
        { from: "Etica e responsabilità nell'IA", to: "Trasparenza" },
        { from: "Trasparenza", to: "Decisioni algoritmiche spiegabili e comprensibili" },
        { from: "Etica e responsabilità nell'IA", to: "Responsabilità" },
        { from: "Responsabilità", to: "Stabilire responsabilità per le decisioni prese dagli algoritmi" },
        { from: "Etica e responsabilità nell'IA", to: "Privacy" },
        { from: "Privacy", to: "Protezione dei dati personali deve essere prioritaria" },
        { from: "Etica e bias negli algoritmi di IA", to: "Strategia di mitigazione dei bias" },
        { from: "Strategia di mitigazione dei bias", to: "Preprocessing" },
        { from: "Preprocessing", to: "Mitigazione del bias nei dati" },
        { from: "Mitigazione del bias nei dati", to: "Bilanciamento dei dati" },
        { from: "Mitigazione del bias nei dati", to: "Rimozione di attributi sensibili" },
        { from: "Mitigazione del bias nei dati", to: "Dati sintetici per gruppi sottorappresentati" },
        { from: "Strategia di mitigazione dei bias", to: "In-processing" },
        { from: "In-processing", to: "Mitigazione durante l'addestramento del modello" },
        { from: "Mitigazione durante l'addestramento del modello", to: "Regularizzazione per incoraggiare l'equità" },
        { from: "Mitigazione durante l'addestramento del modello", to: "Algoritmi fairness-aware" },
        { from: "Strategia di mitigazione dei bias", to: "Post-processing" },
        { from: "Post-processing", to: "Mitigazione dopo l'addestramento del modello" },
        { from: "Mitigazione dopo l'addestramento del modello", to: "Correzione dei risultati per ridurre disparità" },
        { from: "Mitigazione dopo l'addestramento del modello", to: "Equalized odds" },
        { from: "Etica e bias negli algoritmi di IA", to: "Conclusione" },
        { from: "Conclusione", to: "L'importanza dell'etica e della riduzione del bias" },
        { from: "L'importanza dell'etica e della riduzione del bias", to: "Garantire un'IA responsabile per un futuro equo" }
    ]);
}

window.addEventListener("DOMContentLoaded", init);
