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
        { key: "Le Origini dell'Idea di Intelligenza Artificiale", level: 0 },
        { key: "Alan Turing e il Test di Turing", level: 1 },
        { key: "Alan Turing (1912-1954)", level: 2 },
        { key: "Decifrazione dei codici Enigma", level: 3 },
        { key: "Il Test di Turing", level: 2 },
        { key: "Scopo del Test di Turing", level: 3 },
        { key: "Come funziona il Test di Turing", level: 3 },
        { key: "La Nascita dell'IA come Disciplina Scientifica", level: 1 },
        { key: "Conferenza al Dartmouth College (1956)", level: 2 },
        { key: "John McCarthy e Marvin Minsky", level: 3 },
        { key: "Obiettivi della Conferenza", level: 3 },
        { key: "Sviluppi e Sfide negli Anni '60 e '70", level: 1 },
        { key: "Sistemi Esperti", level: 2 },
        { key: "Inverni dell'IA", level: 2 },
        { key: "La Vittoria di Deep Blue (1997)", level: 1 },
        { key: "Deep Blue sconfigge Garry Kasparov", level: 2 },
        { key: "Significato della Vittoria di Deep Blue", level: 3 },
        { key: "L'Avvento del Machine Learning e dei Big Data (Anni 2000)", level: 1 },
        { key: "Computer più potenti e Big Data", level: 2 },
        { key: "Machine Learning", level: 2 },
        { key: "Alti e Bassi nella Storia dell'IA", level: 1 },
        { key: "Successi e sfide della storia dell'IA", level: 2 }
    ], [
        { from: "Le Origini dell'Idea di Intelligenza Artificiale", to: "Alan Turing e il Test di Turing" },
        { from: "Alan Turing e il Test di Turing", to: "Alan Turing (1912-1954)" },
        { from: "Alan Turing (1912-1954)", to: "Decifrazione dei codici Enigma" },
        { from: "Alan Turing e il Test di Turing", to: "Il Test di Turing" },
        { from: "Il Test di Turing", to: "Scopo del Test di Turing" },
        { from: "Il Test di Turing", to: "Come funziona il Test di Turing" },
        { from: "Le Origini dell'Idea di Intelligenza Artificiale", to: "La Nascita dell'IA come Disciplina Scientifica" },
        { from: "La Nascita dell'IA come Disciplina Scientifica", to: "Conferenza al Dartmouth College (1956)" },
        { from: "Conferenza al Dartmouth College (1956)", to: "John McCarthy e Marvin Minsky" },
        { from: "Conferenza al Dartmouth College (1956)", to: "Obiettivi della Conferenza" },
        { from: "Le Origini dell'Idea di Intelligenza Artificiale", to: "Sviluppi e Sfide negli Anni '60 e '70" },
        { from: "Sviluppi e Sfide negli Anni '60 e '70", to: "Sistemi Esperti" },
        { from: "Sviluppi e Sfide negli Anni '60 e '70", to: "Inverni dell'IA" },
        { from: "Le Origini dell'Idea di Intelligenza Artificiale", to: "La Vittoria di Deep Blue (1997)" },
        { from: "La Vittoria di Deep Blue (1997)", to: "Deep Blue sconfigge Garry Kasparov" },
        { from: "Deep Blue sconfigge Garry Kasparov", to: "Significato della Vittoria di Deep Blue" },
        { from: "Le Origini dell'Idea di Intelligenza Artificiale", to: "L'Avvento del Machine Learning e dei Big Data (Anni 2000)" },
        { from: "L'Avvento del Machine Learning e dei Big Data (Anni 2000)", to: "Computer più potenti e Big Data" },
        { from: "L'Avvento del Machine Learning e dei Big Data (Anni 2000)", to: "Machine Learning" },
        { from: "Le Origini dell'Idea di Intelligenza Artificiale", to: "Alti e Bassi nella Storia dell'IA" },
        { from: "Alti e Bassi nella Storia dell'IA", to: "Successi e sfide della storia dell'IA" }
    ]);
}

window.addEventListener("DOMContentLoaded", init);
