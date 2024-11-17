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
        { key: "Differenza tra IA, Machine Learning e Deep Learning", level: 0 },
        { key: "Intelligenza Artificiale (IA)", level: 1 },
        { key: "Simulazione di capacità cognitive umane", level: 2 },
        { key: "Assistenti virtuali, Sistemi di raccomandazione, Veicoli autonomi", level: 3 },
        { key: "Machine Learning (ML)", level: 1 },
        { key: "Apprendimento dai dati senza programmazione esplicita", level: 2 },
        { key: "Tipi di Machine Learning", level: 2 },
        { key: "Apprendimento supervisionato", level: 3 },
        { key: "Classificazione di email come spam o non spam", level: 4 },
        { key: "Apprendimento non supervisionato", level: 3 },
        { key: "Clustering e raggruppamento di dati simili", level: 4 },
        { key: "Apprendimento per rinforzo", level: 3 },
        { key: "Giochi e robotica con feedback positivo o negativo", level: 4 },
        { key: "Deep Learning (DL)", level: 1 },
        { key: "Reti neurali artificiali per analizzare grandi quantità di dati", level: 2 },
        { key: "Caratteristiche del Deep Learning", level: 2 },
        { key: "Utilizza reti neurali profonde", level: 3 },
        { key: "Apprende autonomamente da grandi quantità di dati", level: 3 },
        { key: "Ottimizzato per compiti complessi come riconoscimento facciale", level: 3 },
        { key: "Struttura delle Reti Neurali Artificiali", level: 2 },
        { key: "Strato d'ingresso, Strati nascosti, Strato di uscita", level: 3 }
    ], [
        { from: "Differenza tra IA, Machine Learning e Deep Learning", to: "Intelligenza Artificiale (IA)" },
        { from: "Intelligenza Artificiale (IA)", to: "Simulazione di capacità cognitive umane" },
        { from: "Simulazione di capacità cognitive umane", to: "Assistenti virtuali, Sistemi di raccomandazione, Veicoli autonomi" },
        { from: "Differenza tra IA, Machine Learning e Deep Learning", to: "Machine Learning (ML)" },
        { from: "Machine Learning (ML)", to: "Apprendimento dai dati senza programmazione esplicita" },
        { from: "Machine Learning (ML)", to: "Tipi di Machine Learning" },
        { from: "Tipi di Machine Learning", to: "Apprendimento supervisionato" },
        { from: "Apprendimento supervisionato", to: "Classificazione di email come spam o non spam" },
        { from: "Tipi di Machine Learning", to: "Apprendimento non supervisionato" },
        { from: "Apprendimento non supervisionato", to: "Clustering e raggruppamento di dati simili" },
        { from: "Tipi di Machine Learning", to: "Apprendimento per rinforzo" },
        { from: "Apprendimento per rinforzo", to: "Giochi e robotica con feedback positivo o negativo" },
        { from: "Differenza tra IA, Machine Learning e Deep Learning", to: "Deep Learning (DL)" },
        { from: "Deep Learning (DL)", to: "Reti neurali artificiali per analizzare grandi quantità di dati" },
        { from: "Deep Learning (DL)", to: "Caratteristiche del Deep Learning" },
        { from: "Caratteristiche del Deep Learning", to: "Utilizza reti neurali profonde" },
        { from: "Caratteristiche del Deep Learning", to: "Apprende autonomamente da grandi quantità di dati" },
        { from: "Caratteristiche del Deep Learning", to: "Ottimizzato per compiti complessi come riconoscimento facciale" },
        { from: "Deep Learning (DL)", to: "Struttura delle Reti Neurali Artificiali" },
        { from: "Struttura delle Reti Neurali Artificiali", to: "Strato d'ingresso, Strati nascosti, Strato di uscita" }
    ]);
}

window.addEventListener("DOMContentLoaded", init);
