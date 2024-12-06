// JavaScript file for creating a GoJS diagram on "Explainable AI (XAI)"

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
        { key: "Cos'è l'Explainable AI?", level: 0 },
        { key: "Importanza dell'interpretabilità", level: 1 },
        { key: "Fiducia e trasparenza: Migliora la fiducia nei sistemi IA", level: 2 },
        { key: "Conformità normativa: Richiesta da regolamentazioni come l'AI Act", level: 2 },
        { key: "Etica e responsabilità: Prevenire decisioni discriminatorie", level: 2 },
        { key: "Metodi per rendere l'IA interpretabile", level: 1 },
        { key: "Modelli interpretabili per natura: Regressioni lineari, alberi decisionali", level: 2 },
        { key: "Tecniche di interpretabilità post-hoc", level: 2 },
        { key: "LIME: Modelli locali interpretabili", level: 3 },
        { key: "SHAP: Importanza delle feature nel modello", level: 3 },
        { key: "Sfide nell'implementazione dell'XAI", level: 1 },
        { key: "Compromesso tra precisione e interpretabilità: Modelli complessi più difficili da spiegare", level: 2 },
        { key: "Bias e interpretazioni errate: Rischio di spiegazioni non accurate", level: 2 },
        { key: "Costi e complessità: Implementazione costosa e tecnicamente complessa", level: 2 },
        { key: "Conclusione", level: 0 },
        { key: "XAI è fondamentale per fiducia e trasparenza nei sistemi IA", level: 1 }
    ], [
        { from: "Cos'è l'Explainable AI?", to: "Importanza dell'interpretabilità" },
        { from: "Importanza dell'interpretabilità", to: "Fiducia e trasparenza: Migliora la fiducia nei sistemi IA" },
        { from: "Importanza dell'interpretabilità", to: "Conformità normativa: Richiesta da regolamentazioni come l'AI Act" },
        { from: "Importanza dell'interpretabilità", to: "Etica e responsabilità: Prevenire decisioni discriminatorie" },
        { from: "Cos'è l'Explainable AI?", to: "Metodi per rendere l'IA interpretabile" },
        { from: "Metodi per rendere l'IA interpretabile", to: "Modelli interpretabili per natura: Regressioni lineari, alberi decisionali" },
        { from: "Metodi per rendere l'IA interpretabile", to: "Tecniche di interpretabilità post-hoc" },
        { from: "Tecniche di interpretabilità post-hoc", to: "LIME: Modelli locali interpretabili" },
        { from: "Tecniche di interpretabilità post-hoc", to: "SHAP: Importanza delle feature nel modello" },
        { from: "Cos'è l'Explainable AI?", to: "Sfide nell'implementazione dell'XAI" },
        { from: "Sfide nell'implementazione dell'XAI", to: "Compromesso tra precisione e interpretabilità: Modelli complessi più difficili da spiegare" },
        { from: "Sfide nell'implementazione dell'XAI", to: "Bias e interpretazioni errate: Rischio di spiegazioni non accurate" },
        { from: "Sfide nell'implementazione dell'XAI", to: "Costi e complessità: Implementazione costosa e tecnicamente complessa" },
        { from: "Cos'è l'Explainable AI?", to: "Conclusione" },
        { from: "Conclusione", to: "XAI è fondamentale per fiducia e trasparenza nei sistemi IA" }
    ]);
}

window.addEventListener("DOMContentLoaded", init);
