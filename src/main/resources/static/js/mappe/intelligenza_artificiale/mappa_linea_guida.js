// JavaScript file for creating a GoJS diagram on "Regolamentazione dell'IA"

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
        { key: "Introduzione alla regolamentazione dell'IA", level: 0 },
        { key: "Regolamentazione in Europa", level: 1 },
        { key: "AI Act: Classificazione del rischio (2021)", level: 2 },
        { key: "Rischio inaccettabile", level: 3 },
        { key: "Sorveglianza biometrica, social scoring", level: 4 },
        { key: "Rischio alto", level: 3 },
        { key: "Sistemi in ambiti critici (sanità, giustizia)", level: 4 },
        { key: "Rischio limitato", level: 3 },
        { key: "Chatbot e trasparenza sull'uso dell'IA", level: 4 },
        { key: "Rischio minimo", level: 3 },
        { key: "Sistemi a basso rischio (es. filtri spam)", level: 4 },
        { key: "GDPR e trattamento dati personali", level: 2 },
        { key: "Regolamentazione negli Stati Uniti", level: 1 },
        { key: "Approccio frammentato senza legislazione federale specifica", level: 2 },
        { key: "FTC e tutela dei consumatori", level: 3 },
        { key: "NIST: Framework per affidabilità dei sistemi IA", level: 3 },
        { key: "Regolamentazione a livello statale (es. California)", level: 2 },
        { key: "Altre normative globali", level: 1 },
        { key: "Cina: Gestione della Sicurezza delle Tecnologie Algoritmiche", level: 2 },
        { key: "Canada e Regno Unito: Trasparenza, etica e protezione dati", level: 2 },
        { key: "ONU e OCSE: Linee guida globali per uso etico", level: 2 },
        { key: "Conclusione", level: 0 },
        { key: "Le future regolamentazioni determineranno l'etica dell'IA", level: 1 }
    ], [
        { from: "Introduzione alla regolamentazione dell'IA", to: "Regolamentazione in Europa" },
        { from: "Regolamentazione in Europa", to: "AI Act: Classificazione del rischio (2021)" },
        { from: "AI Act: Classificazione del rischio (2021)", to: "Rischio inaccettabile" },
        { from: "Rischio inaccettabile", to: "Sorveglianza biometrica, social scoring" },
        { from: "AI Act: Classificazione del rischio (2021)", to: "Rischio alto" },
        { from: "Rischio alto", to: "Sistemi in ambiti critici (sanità, giustizia)" },
        { from: "AI Act: Classificazione del rischio (2021)", to: "Rischio limitato" },
        { from: "Rischio limitato", to: "Chatbot e trasparenza sull'uso dell'IA" },
        { from: "AI Act: Classificazione del rischio (2021)", to: "Rischio minimo" },
        { from: "Rischio minimo", to: "Sistemi a basso rischio (es. filtri spam)" },
        { from: "Regolamentazione in Europa", to: "GDPR e trattamento dati personali" },
        { from: "Introduzione alla regolamentazione dell'IA", to: "Regolamentazione negli Stati Uniti" },
        { from: "Regolamentazione negli Stati Uniti", to: "Approccio frammentato senza legislazione federale specifica" },
        { from: "Approccio frammentato senza legislazione federale specifica", to: "FTC e tutela dei consumatori" },
        { from: "Approccio frammentato senza legislazione federale specifica", to: "NIST: Framework per affidabilità dei sistemi IA" },
        { from: "Regolamentazione negli Stati Uniti", to: "Regolamentazione a livello statale (es. California)" },
        { from: "Introduzione alla regolamentazione dell'IA", to: "Altre normative globali" },
        { from: "Altre normative globali", to: "Cina: Gestione della Sicurezza delle Tecnologie Algoritmiche" },
        { from: "Altre normative globali", to: "Canada e Regno Unito: Trasparenza, etica e protezione dati" },
        { from: "Altre normative globali", to: "ONU e OCSE: Linee guida globali per uso etico" },
        { from: "Introduzione alla regolamentazione dell'IA", to: "Conclusione" },
        { from: "Conclusione", to: "Le future regolamentazioni determineranno l'etica dell'IA" }
    ]);
}

window.addEventListener("DOMContentLoaded", init);
