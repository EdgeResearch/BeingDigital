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
        { key: "Intelligenza Artificiale (IA)", level: 0 },
        { key: "Introduzione all'IA", level: 1 },
        { key: "Capacità di pensare autonomamente", level: 2 },
        { key: "Apprendimento dai dati e miglioramento", level: 2 },
        { key: "IA vs Intelligenza Umana", level: 1 },
        { key: "Osservazione: Alta per umani, limitata per IA", level: 2 },
        { key: "Apprendimento: Intuitivo per umani, algoritmico per IA", level: 2 },
        { key: "Adattamento: Flessibile per umani, limitato per IA", level: 2 },
        { key: "Tipi di IA", level: 1 },
        { key: "IA Debole", level: 2 },
        { key: "Compiti specifici come riconoscimento facciale e Deep Blue", level: 3 },
        { key: "IA Forte", level: 2 },
        { key: "Concetto teorico di capacità cognitive umane", level: 3 },
        { key: "IA Debole vs IA Forte", level: 1 },
        { key: "IA Debole elabora grandi quantità di dati velocemente", level: 2 },
        { key: "Conclusione", level: 1 },
        { key: "Potenziale di crescita e limitazioni attuali", level: 2 }
    ], [
        { from: "Intelligenza Artificiale (IA)", to: "Introduzione all'IA" },
        { from: "Introduzione all'IA", to: "Capacità di pensare autonomamente" },
        { from: "Introduzione all'IA", to: "Apprendimento dai dati e miglioramento" },
        { from: "Intelligenza Artificiale (IA)", to: "IA vs Intelligenza Umana" },
        { from: "IA vs Intelligenza Umana", to: "Osservazione: Alta per umani, limitata per IA" },
        { from: "IA vs Intelligenza Umana", to: "Apprendimento: Intuitivo per umani, algoritmico per IA" },
        { from: "IA vs Intelligenza Umana", to: "Adattamento: Flessibile per umani, limitato per IA" },
        { from: "Intelligenza Artificiale (IA)", to: "Tipi di IA" },
        { from: "Tipi di IA", to: "IA Debole" },
        { from: "IA Debole", to: "Compiti specifici come riconoscimento facciale e Deep Blue" },
        { from: "Tipi di IA", to: "IA Forte" },
        { from: "IA Forte", to: "Concetto teorico di capacità cognitive umane" },
        { from: "Intelligenza Artificiale (IA)", to: "IA Debole vs IA Forte" },
        { from: "IA Debole vs IA Forte", to: "IA Debole elabora grandi quantità di dati velocemente" },
        { from: "Intelligenza Artificiale (IA)", to: "Conclusione" },
        { from: "Conclusione", to: "Potenziale di crescita e limitazioni attuali" }
    ]);
}

window.addEventListener("DOMContentLoaded", init);