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
        { key: "Applicazioni di IA nella vita quotidiana", level: 0 },
        { key: "Assistenti virtuali", level: 1 },
        { key: "Siri, Alexa, Google Assistant", level: 2 },
        { key: "Riconoscimento vocale e comprensione linguistica", level: 3 },
        { key: "Sistemi di raccomandazione", level: 1 },
        { key: "Netflix, Amazon", level: 2 },
        { key: "Suggerimenti personalizzati basati sul comportamento utente", level: 3 },
        { key: "Social media", level: 1 },
        { key: "Facebook, Instagram, TikTok", level: 2 },
        { key: "Algoritmi di personalizzazione del feed", level: 3 },
        { key: "Auto a guida autonoma", level: 1 },
        { key: "Tesla e sistemi di assistenza alla guida", level: 2 },
        { key: "Sensori e IA per interpretare l'ambiente", level: 3 },
        { key: "Riconoscimento facciale", level: 1 },
        { key: "Sblocco smartphone, tagging automatico", level: 2 },
        { key: "Analisi delle caratteristiche del volto", level: 3 },
        { key: "Traduzione automatica", level: 1 },
        { key: "Google Translate", level: 2 },
        { key: "Deep learning per traduzioni contestuali", level: 3 },
        { key: "Diagnostica medica", level: 1 },
        { key: "Analisi immagini mediche", level: 2 },
        { key: "Rilevamento di malattie tramite IA", level: 3 },
        { key: "Chatbot", level: 1 },
        { key: "Servizio clienti online", level: 2 },
        { key: "Risposta automatica alle domande frequenti", level: 3 }
    ], [
        { from: "Applicazioni di IA nella vita quotidiana", to: "Assistenti virtuali" },
        { from: "Assistenti virtuali", to: "Siri, Alexa, Google Assistant" },
        { from: "Siri, Alexa, Google Assistant", to: "Riconoscimento vocale e comprensione linguistica" },
        { from: "Applicazioni di IA nella vita quotidiana", to: "Sistemi di raccomandazione" },
        { from: "Sistemi di raccomandazione", to: "Netflix, Amazon" },
        { from: "Netflix, Amazon", to: "Suggerimenti personalizzati basati sul comportamento utente" },
        { from: "Applicazioni di IA nella vita quotidiana", to: "Social media" },
        { from: "Social media", to: "Facebook, Instagram, TikTok" },
        { from: "Facebook, Instagram, TikTok", to: "Algoritmi di personalizzazione del feed" },
        { from: "Applicazioni di IA nella vita quotidiana", to: "Auto a guida autonoma" },
        { from: "Auto a guida autonoma", to: "Tesla e sistemi di assistenza alla guida" },
        { from: "Tesla e sistemi di assistenza alla guida", to: "Sensori e IA per interpretare l'ambiente" },
        { from: "Applicazioni di IA nella vita quotidiana", to: "Riconoscimento facciale" },
        { from: "Riconoscimento facciale", to: "Sblocco smartphone, tagging automatico" },
        { from: "Sblocco smartphone, tagging automatico", to: "Analisi delle caratteristiche del volto" },
        { from: "Applicazioni di IA nella vita quotidiana", to: "Traduzione automatica" },
        { from: "Traduzione automatica", to: "Google Translate" },
        { from: "Google Translate", to: "Deep learning per traduzioni contestuali" },
        { from: "Applicazioni di IA nella vita quotidiana", to: "Diagnostica medica" },
        { from: "Diagnostica medica", to: "Analisi immagini mediche" },
        { from: "Analisi immagini mediche", to: "Rilevamento di malattie tramite IA" },
        { from: "Applicazioni di IA nella vita quotidiana", to: "Chatbot" },
        { from: "Chatbot", to: "Servizio clienti online" },
        { from: "Servizio clienti online", to: "Risposta automatica alle domande frequenti" }
    ]);
}

window.addEventListener("DOMContentLoaded", init);
