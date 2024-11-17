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
        { key: "Equità (Fairness) nell'IA", level: 0 },
        { key: "Dimensioni della fairness nell'IA", level: 1 },
        { key: "Equità nel trattamento", level: 2 },
        { key: "Garantire che individui con caratteristiche simili ricevano trattamenti simili dagli algoritmi", level: 3 },
        { key: "Disparità di impatto", level: 2 },
        { key: "Misura in cui un algoritmo produce risultati diversi per gruppi protetti", level: 3 },
        { key: "Equità nei risultati", level: 2 },
        { key: "Assicurare che le decisioni portino a risultati equi per diverse popolazioni", level: 3 },
        { key: "Tipi di fairness", level: 1 },
        { key: "Fairness Demografica", level: 2 },
        { key: "Le decisioni degli algoritmi non devono variare significativamente tra gruppi demografici", level: 3 },
        { key: "Equal Opportunity", level: 2 },
        { key: "Garantire che i tassi di veri positivi siano simili tra i gruppi", level: 3 },
        { key: "Equalized Odds", level: 2 },
        { key: "Tassi simili di veri positivi e falsi positivi tra diversi gruppi", level: 3 },
        { key: "Importanza della Fairness", level: 1 },
        { key: "Giustizia Sociale", level: 2 },
        { key: "Gli algoritmi che perpetuano il bias possono contribuire a disuguaglianze sociali", level: 3 },
        { key: "Fiducia del Pubblico", level: 2 },
        { key: "I sistemi di IA che mostrano bias possono erodere la fiducia degli utenti", level: 3 },
        { key: "Compliance Legale", level: 2 },
        { key: "Leggi e regolamenti richiedono che le decisioni automatizzate siano giuste", level: 3 },
        { key: "Sfide nella realizzazione della fairness", level: 1 },
        { key: "Definizioni Ambigue", level: 2 },
        { key: "La fairness può essere interpretata in modi diversi", level: 3 },
        { key: "Compromessi", level: 2 },
        { key: "Perseguire la fairness può portare a compromessi rispetto all'accuratezza", level: 3 },
        { key: "Dati Non Rappresentativi", level: 2 },
        { key: "Dati biased portano a algoritmi che riflettono quei bias", level: 3 },
        { key: "Conclusione", level: 1 },
        { key: "Garantire che le tecnologie siano sviluppate e utilizzate in modo giusto e responsabile", level: 2 }
    ], [
        { from: "Equità (Fairness) nell'IA", to: "Dimensioni della fairness nell'IA" },
        { from: "Dimensioni della fairness nell'IA", to: "Equità nel trattamento" },
        { from: "Equità nel trattamento", to: "Garantire che individui con caratteristiche simili ricevano trattamenti simili dagli algoritmi" },
        { from: "Dimensioni della fairness nell'IA", to: "Disparità di impatto" },
        { from: "Disparità di impatto", to: "Misura in cui un algoritmo produce risultati diversi per gruppi protetti" },
        { from: "Dimensioni della fairness nell'IA", to: "Equità nei risultati" },
        { from: "Equità nei risultati", to: "Assicurare che le decisioni portino a risultati equi per diverse popolazioni" },
        { from: "Equità (Fairness) nell'IA", to: "Tipi di fairness" },
        { from: "Tipi di fairness", to: "Fairness Demografica" },
        { from: "Fairness Demografica", to: "Le decisioni degli algoritmi non devono variare significativamente tra gruppi demografici" },
        { from: "Tipi di fairness", to: "Equal Opportunity" },
        { from: "Equal Opportunity", to: "Garantire che i tassi di veri positivi siano simili tra i gruppi" },
        { from: "Tipi di fairness", to: "Equalized Odds" },
        { from: "Equalized Odds", to: "Tassi simili di veri positivi e falsi positivi tra diversi gruppi" },
        { from: "Equità (Fairness) nell'IA", to: "Importanza della Fairness" },
        { from: "Importanza della Fairness", to: "Giustizia Sociale" },
        { from: "Giustizia Sociale", to: "Gli algoritmi che perpetuano il bias possono contribuire a disuguaglianze sociali" },
        { from: "Importanza della Fairness", to: "Fiducia del Pubblico" },
        { from: "Fiducia del Pubblico", to: "I sistemi di IA che mostrano bias possono erodere la fiducia degli utenti" },
        { from: "Importanza della Fairness", to: "Compliance Legale" },
        { from: "Compliance Legale", to: "Leggi e regolamenti richiedono che le decisioni automatizzate siano giuste" },
        { from: "Equità (Fairness) nell'IA", to: "Sfide nella realizzazione della fairness" },
        { from: "Sfide nella realizzazione della fairness", to: "Definizioni Ambigue" },
        { from: "Definizioni Ambigue", to: "La fairness può essere interpretata in modi diversi" },
        { from: "Sfide nella realizzazione della fairness", to: "Compromessi" },
        { from: "Compromessi", to: "Perseguire la fairness può portare a compromessi rispetto all'accuratezza" },
        { from: "Sfide nella realizzazione della fairness", to: "Dati Non Rappresentativi" },
        { from: "Dati Non Rappresentativi", to: "Dati biased portano a algoritmi che riflettono quei bias" },
        { from: "Equità (Fairness) nell'IA", to: "Conclusione" },
        { from: "Conclusione", to: "Garantire che le tecnologie siano sviluppate e utilizzate in modo giusto e responsabile" }
    ]);
}

window.addEventListener("DOMContentLoaded", init);
