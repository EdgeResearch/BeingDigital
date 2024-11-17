// JavaScript file for creating a GoJS diagram on "Trattamento dei Dati nell'IA"

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
        { key: "Introduzione al trattamento dei dati nell'IA", level: 0 },
        { key: "Tipologie di dati utilizzati dall'IA", level: 1 },
        { key: "Dati strutturati: Database relazionali (es. dati finanziari, transazioni)", level: 2 },
        { key: "Dati non strutturati: Immagini, video, testo", level: 2 },
        { key: "Dati sensibili: Dati sanitari, biometrici", level: 2 },
        { key: "Sicurezza dei dati nell'IA", level: 1 },
        { key: "Rischi di attacco: Accesso non autorizzato, data poisoning, violazione della privacy", level: 2 },
        { key: "Crittografia: Protezione durante archiviazione e trasferimento", level: 2 },
        { key: "Anonimizzazione e pseudonimizzazione: Protezione dell'identità degli utenti", level: 2 },
        { key: "AI robusta e resiliente: Capacità di gestire attacchi", level: 2 },
        { key: "Privacy by Design e Privacy by Default", level: 1 },
        { key: "Privacy by Design: Protezione integrata nella progettazione del sistema", level: 2 },
        { key: "Privacy by Default: Raccolta minima dei dati necessari", level: 2 },
        { key: "Conclusione", level: 0 },
        { key: "Il trattamento e la sicurezza dei dati sono essenziali per sistemi IA affidabili", level: 1 }
    ], [
        { from: "Introduzione al trattamento dei dati nell'IA", to: "Tipologie di dati utilizzati dall'IA" },
        { from: "Tipologie di dati utilizzati dall'IA", to: "Dati strutturati: Database relazionali (es. dati finanziari, transazioni)" },
        { from: "Tipologie di dati utilizzati dall'IA", to: "Dati non strutturati: Immagini, video, testo" },
        { from: "Tipologie di dati utilizzati dall'IA", to: "Dati sensibili: Dati sanitari, biometrici" },
        { from: "Introduzione al trattamento dei dati nell'IA", to: "Sicurezza dei dati nell'IA" },
        { from: "Sicurezza dei dati nell'IA", to: "Rischi di attacco: Accesso non autorizzato, data poisoning, violazione della privacy" },
        { from: "Sicurezza dei dati nell'IA", to: "Crittografia: Protezione durante archiviazione e trasferimento" },
        { from: "Sicurezza dei dati nell'IA", to: "Anonimizzazione e pseudonimizzazione: Protezione dell'identità degli utenti" },
        { from: "Sicurezza dei dati nell'IA", to: "AI robusta e resiliente: Capacità di gestire attacchi" },
        { from: "Introduzione al trattamento dei dati nell'IA", to: "Privacy by Design e Privacy by Default" },
        { from: "Privacy by Design e Privacy by Default", to: "Privacy by Design: Protezione integrata nella progettazione del sistema" },
        { from: "Privacy by Design e Privacy by Default", to: "Privacy by Default: Raccolta minima dei dati necessari" },
        { from: "Introduzione al trattamento dei dati nell'IA", to: "Conclusione" },
        { from: "Conclusione", to: "Il trattamento e la sicurezza dei dati sono essenziali per sistemi IA affidabili" }
    ]);
}

window.addEventListener("DOMContentLoaded", init);
