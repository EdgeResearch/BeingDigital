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
        { key: "Dati, algoritmi e modelli: Il cuore dell'Intelligenza Artificiale", level: 0 },
        { key: "Cos'è un dato?", level: 1 },
        { key: "Informazione raccolta, archiviata ed elaborata", level: 2 },
        { key: "Tipi di dati utilizzati dall'IA", level: 2 },
        { key: "Numerici: Temperature, prezzi, misurazioni", level: 3 },
        { key: "Testuali: Recensioni di prodotti, post social", level: 3 },
        { key: "Visivi: Immagini, video per riconoscimento", level: 3 },
        { key: "Audio: Suoni o voci per riconoscimento vocale", level: 3 },
        { key: "Dataset e sue componenti principali", level: 2 },
        { key: "Features (Caratteristiche): Attributi dei dati", level: 3 },
        { key: "Labels (Etichette): Risposte associate ai dati", level: 3 },
        { key: "L'Importanza dei dati", level: 1 },
        { key: "Dati di alta qualità", level: 2 },
        { key: "Completi e accurati: Privilegi di errori e incoerenze", level: 3 },
        { key: "Bilanciati: Rappresentativi di tutti gli scenari", level: 3 },
        { key: "Dati di scarsa qualità", level: 2 },
        { key: "Rumore e anomalie: Dati distorti o incompleti", level: 3 },
        { key: "Trattamenti dei dati", level: 1 },
        { key: "Raccolta dei dati: Fonti come database, API", level: 2 },
        { key: "Pulizia dei dati: Rimozione di duplicati e errori", level: 2 },
        { key: "Trasformazione dei dati: Normalizzazione, scaling", level: 2 },
        { key: "Riduzione dei dati: Selezione delle feature importanti", level: 2 },
        { key: "Divisione dei dati: Training, validation, test set", level: 2 },
        { key: "Aumento dei dati: Varianti per migliorare il modello", level: 2 },
        { key: "Cos'è un Algoritmo?", level: 1 },
        { key: "Sequenza di istruzioni per risolvere un problema", level: 2 },
        { key: "Caratteristiche di un algoritmo", level: 2 },
        { key: "Determinato: Ogni passaggio è chiaro e definito", level: 3 },
        { key: "Finito: Deve terminare in un tempo finito", level: 3 },
        { key: "Esempi di algoritmi comuni", level: 2 },
        { key: "Algoritmi di ricerca: Trovano informazioni nei dati", level: 3 },
        { key: "Algoritmi di ordinamento: Organizzano i dati", level: 3 },
        { key: "Cos'è un Modello?", level: 1 },
        { key: "Risultato finale dell'apprendimento di un algoritmo", level: 2 },
        { key: "Esempi di modelli", level: 2 },
        { key: "Riconoscimento facciale: Identifica persone", level: 3 },
        { key: "Previsione del tempo: Stima condizioni meteorologiche", level: 3 },
        { key: "Vantaggi di un buon modello", level: 2 },
        { key: "Accuratezza: Previsioni precise", level: 3 },
        { key: "Generalizzazione: Funziona bene con nuovi dati", level: 3 },
        { key: "Efficienza: Gestione rapida di grandi quantità di dati", level: 3 },
        { key: "Addestramento di un modello", level: 1 },
        { key: "Dataset: Dati utilizzati per imparare", level: 2 },
        { key: "Funzione di perdita: Valuta gli errori del modello", level: 2 },
        { key: "Ottimizzazione: Correzione degli errori del modello", level: 2 },
        { key: "Iterazioni ed epoche: Passaggi ripetuti per migliorare", level: 2 },
        { key: "Sotto-addestramento e sovra-addestramento", level: 2 },
        { key: "Sotto-addestramento: Modello non ha appreso abbastanza", level: 3 },
        { key: "Sovra-addestramento: Modello troppo specifico", level: 3 }
    ], [
        { from: "Dati, algoritmi e modelli: Il cuore dell'Intelligenza Artificiale", to: "Cos'è un dato?" },
        { from: "Cos'è un dato?", to: "Informazione raccolta, archiviata ed elaborata" },
        { from: "Cos'è un dato?", to: "Tipi di dati utilizzati dall'IA" },
        { from: "Tipi di dati utilizzati dall'IA", to: "Numerici: Temperature, prezzi, misurazioni" },
        { from: "Tipi di dati utilizzati dall'IA", to: "Testuali: Recensioni di prodotti, post social" },
        { from: "Tipi di dati utilizzati dall'IA", to: "Visivi: Immagini, video per riconoscimento" },
        { from: "Tipi di dati utilizzati dall'IA", to: "Audio: Suoni o voci per riconoscimento vocale" },
        { from: "Cos'è un dato?", to: "Dataset e sue componenti principali" },
        { from: "Dataset e sue componenti principali", to: "Features (Caratteristiche): Attributi dei dati" },
        { from: "Dataset e sue componenti principali", to: "Labels (Etichette): Risposte associate ai dati" },
        { from: "Dati, algoritmi e modelli: Il cuore dell'Intelligenza Artificiale", to: "L'Importanza dei dati" },
        { from: "L'Importanza dei dati", to: "Dati di alta qualità" },
        { from: "Dati di alta qualità", to: "Completi e accurati: Privilegi di errori e incoerenze" },
        { from: "Dati di alta qualità", to: "Bilanciati: Rappresentativi di tutti gli scenari" },
        { from: "L'Importanza dei dati", to: "Dati di scarsa qualità" },
        { from: "Dati di scarsa qualità", to: "Rumore e anomalie: Dati distorti o incompleti" },
        { from: "Dati, algoritmi e modelli: Il cuore dell'Intelligenza Artificiale", to: "Trattamenti dei dati" },
        { from: "Trattamenti dei dati", to: "Raccolta dei dati: Fonti come database, API" },
        { from: "Trattamenti dei dati", to: "Pulizia dei dati: Rimozione di duplicati e errori" },
        { from: "Trattamenti dei dati", to: "Trasformazione dei dati: Normalizzazione, scaling" },
        { from: "Trattamenti dei dati", to: "Riduzione dei dati: Selezione delle feature importanti" },
        { from: "Trattamenti dei dati", to: "Divisione dei dati: Training, validation, test set" },
        { from: "Trattamenti dei dati", to: "Aumento dei dati: Varianti per migliorare il modello" },
        { from: "Dati, algoritmi e modelli: Il cuore dell'Intelligenza Artificiale", to: "Cos'è un Algoritmo?" },
        { from: "Cos'è un Algoritmo?", to: "Sequenza di istruzioni per risolvere un problema" },
        { from: "Cos'è un Algoritmo?", to: "Caratteristiche di un algoritmo" },
        { from: "Caratteristiche di un algoritmo", to: "Determinato: Ogni passaggio è chiaro e definito" },
        { from: "Caratteristiche di un algoritmo", to: "Finito: Deve terminare in un tempo finito" },
        { from: "Cos'è un Algoritmo?", to: "Esempi di algoritmi comuni" },
        { from: "Esempi di algoritmi comuni", to: "Algoritmi di ricerca: Trovano informazioni nei dati" },
        { from: "Esempi di algoritmi comuni", to: "Algoritmi di ordinamento: Organizzano i dati" },
        { from: "Dati, algoritmi e modelli: Il cuore dell'Intelligenza Artificiale", to: "Cos'è un Modello?" },
        { from: "Cos'è un Modello?", to: "Risultato finale dell'apprendimento di un algoritmo" },
        { from: "Cos'è un Modello?", to: "Esempi di modelli" },
        { from: "Esempi di modelli", to: "Riconoscimento facciale: Identifica persone" },
        { from: "Esempi di modelli", to: "Previsione del tempo: Stima condizioni meteorologiche" },
        { from: "Cos'è un Modello?", to: "Vantaggi di un buon modello" },
        { from: "Vantaggi di un buon modello", to: "Accuratezza: Previsioni precise" },
        { from: "Vantaggi di un buon modello", to: "Generalizzazione: Funziona bene con nuovi dati" },
        { from: "Vantaggi di un buon modello", to: "Efficienza: Gestione rapida di grandi quantità di dati" },
        { from: "Dati, algoritmi e modelli: Il cuore dell'Intelligenza Artificiale", to: "Addestramento di un modello" },
        { from: "Addestramento di un modello", to: "Dataset: Dati utilizzati per imparare" },
        { from: "Addestramento di un modello", to: "Funzione di perdita: Valuta gli errori del modello" },
        { from: "Addestramento di un modello", to: "Ottimizzazione: Correzione degli errori del modello" },
        { from: "Addestramento di un modello", to: "Iterazioni ed epoche: Passaggi ripetuti per migliorare" },
        { from: "Addestramento di un modello", to: "Sotto-addestramento e sovra-addestramento" },
        { from: "Sotto-addestramento e sovra-addestramento", to: "Sotto-addestramento: Modello non ha appreso abbastanza" },
        { from: "Sotto-addestramento e sovra-addestramento", to: "Sovra-addestramento: Modello troppo specifico" }
    ]);
}

window.addEventListener("DOMContentLoaded", init);
