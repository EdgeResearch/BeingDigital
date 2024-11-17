// JavaScript file for creating a GoJS diagram on "Esempi di Bias nell'IA"

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
        { key: "Introduzione agli esempi di bias nell'IA", level: 0 },
        { key: "Amazon e il sistema di reclutamento", level: 1 },
        { key: "Contesto (Amazon)", level: 2 },
        { key: "Amazon ha sviluppato un algoritmo di reclutamento per automatizzare il processo di selezione dei candidati", level: 3 },
        { key: "Errore di Bias (Amazon)", level: 2 },
        { key: "Nel 2018, l'algoritmo era discriminatorio nei confronti delle donne, penalizzando le candidature femminili", level: 3 },
        { key: "Risultato (Amazon)", level: 2 },
        { key: "Amazon ha abbandonato il sistema a causa dei bias sistematici", level: 3 },
        { key: "Google e il riconoscimento delle foto", level: 1 },
        { key: "Contesto (Google)", level: 2 },
        { key: "Il sistema di riconoscimento di Google Photos identificava le persone nelle foto", level: 3 },
        { key: "Errore di Bias (Google)", level: 2 },
        { key: "Nel 2015, il sistema etichettava erroneamente le immagini di persone di colore come gorilla", level: 3 },
        { key: "Risultato (Google)", level: 2 },
        { key: "Google ha rimosso la categorizzazione offensiva e migliorato il sistema", level: 3 },
        { key: "Apple Card e differenze di credito", level: 1 },
        { key: "Contesto (Apple)", level: 2 },
        { key: "Apple Card ha utilizzato algoritmi per determinare il limite di credito degli utenti", level: 3 },
        { key: "Errore di Bias (Apple)", level: 2 },
        { key: "Nel 2019, l'algoritmo favoriva gli uomini rispetto alle donne, con limiti di credito inferiori per le donne", level: 3 },
        { key: "Risultato (Apple)", level: 2 },
        { key: "Preoccupazioni per bias di genere e richieste di maggiore trasparenza", level: 3 },
        { key: "Microsoft e Tay, il Chatbot", level: 1 },
        { key: "Contesto (Microsoft)", level: 2 },
        { key: "Microsoft ha lanciato Tay, un chatbot progettato per interagire con gli utenti su Twitter", level: 3 },
        { key: "Errore di Bias (Microsoft)", level: 2 },
        { key: "Tay è stato influenzato da tweet offensivi, iniziando a twittare commenti razzisti e sessisti", level: 3 },
        { key: "Risultato (Microsoft)", level: 2 },
        { key: "Microsoft ha disattivato Tay dopo solo 16 ore dal lancio e ha rivisto la strategia di apprendimento", level: 3 },
        { key: "Facebook e il targeting degli annunci", level: 1 },
        { key: "Contesto (Facebook)", level: 2 },
        { key: "Facebook utilizza algoritmi per il targeting degli annunci pubblicitari", level: 3 },
        { key: "Errore di Bias (Facebook)", level: 2 },
        { key: "Nel 2019, consentiva agli inserzionisti di escludere specifici gruppi demografici dalle campagne pubblicitarie", level: 3 },
        { key: "Risultato (Facebook)", level: 2 },
        { key: "Facebook ha implementato misure per migliorare la trasparenza e l'equità nel targeting", level: 3 },
        { key: "Conclusione", level: 1 },
        { key: "Il bias può manifestarsi in vari contesti e deve essere affrontato proattivamente dalle aziende tecnologiche", level: 2 }
    ], [
        { from: "Introduzione agli esempi di bias nell'IA", to: "Amazon e il sistema di reclutamento" },
        { from: "Amazon e il sistema di reclutamento", to: "Contesto (Amazon)" },
        { from: "Contesto (Amazon)", to: "Amazon ha sviluppato un algoritmo di reclutamento per automatizzare il processo di selezione dei candidati" },
        { from: "Amazon e il sistema di reclutamento", to: "Errore di Bias (Amazon)" },
        { from: "Errore di Bias (Amazon)", to: "Nel 2018, l'algoritmo era discriminatorio nei confronti delle donne, penalizzando le candidature femminili" },
        { from: "Amazon e il sistema di reclutamento", to: "Risultato (Amazon)" },
        { from: "Risultato (Amazon)", to: "Amazon ha abbandonato il sistema a causa dei bias sistematici" },
        { from: "Introduzione agli esempi di bias nell'IA", to: "Google e il riconoscimento delle foto" },
        { from: "Google e il riconoscimento delle foto", to: "Contesto (Google)" },
        { from: "Contesto (Google)", to: "Il sistema di riconoscimento di Google Photos identificava le persone nelle foto" },
        { from: "Google e il riconoscimento delle foto", to: "Errore di Bias (Google)" },
        { from: "Errore di Bias (Google)", to: "Nel 2015, il sistema etichettava erroneamente le immagini di persone di colore come gorilla" },
        { from: "Google e il riconoscimento delle foto", to: "Risultato (Google)" },
        { from: "Risultato (Google)", to: "Google ha rimosso la categorizzazione offensiva e migliorato il sistema" },
        { from: "Introduzione agli esempi di bias nell'IA", to: "Apple Card e differenze di credito" },
        { from: "Apple Card e differenze di credito", to: "Contesto (Apple)" },
        { from: "Contesto (Apple)", to: "Apple Card ha utilizzato algoritmi per determinare il limite di credito degli utenti" },
        { from: "Apple Card e differenze di credito", to: "Errore di Bias (Apple)" },
        { from: "Errore di Bias (Apple)", to: "Nel 2019, l'algoritmo favoriva gli uomini rispetto alle donne, con limiti di credito inferiori per le donne" },
        { from: "Apple Card e differenze di credito", to: "Risultato (Apple)" },
        { from: "Risultato (Apple)", to: "Preoccupazioni per bias di genere e richieste di maggiore trasparenza" },
        { from: "Introduzione agli esempi di bias nell'IA", to: "Microsoft e Tay, il Chatbot" },
        { from: "Microsoft e Tay, il Chatbot", to: "Contesto (Microsoft)" },
        { from: "Contesto (Microsoft)", to: "Microsoft ha lanciato Tay, un chatbot progettato per interagire con gli utenti su Twitter" },
        { from: "Microsoft e Tay, il Chatbot", to: "Errore di Bias (Microsoft)" },
        { from: "Errore di Bias (Microsoft)", to: "Tay è stato influenzato da tweet offensivi, iniziando a twittare commenti razzisti e sessisti" },
        { from: "Microsoft e Tay, il Chatbot", to: "Risultato (Microsoft)" },
        { from: "Risultato (Microsoft)", to: "Microsoft ha disattivato Tay dopo solo 16 ore dal lancio e ha rivisto la strategia di apprendimento" },
        { from: "Introduzione agli esempi di bias nell'IA", to: "Facebook e il targeting degli annunci" },
        { from: "Facebook e il targeting degli annunci", to: "Contesto (Facebook)" },
        { from: "Contesto (Facebook)", to: "Facebook utilizza algoritmi per il targeting degli annunci pubblicitari" },
        { from: "Facebook e il targeting degli annunci", to: "Errore di Bias (Facebook)" },
        { from: "Errore di Bias (Facebook)", to: "Nel 2019, consentiva agli inserzionisti di escludere specifici gruppi demografici dalle campagne pubblicitarie" },
        { from: "Facebook e il targeting degli annunci", to: "Risultato (Facebook)" },
        { from: "Risultato (Facebook)", to: "Facebook ha implementato misure per migliorare la trasparenza e l'equità nel targeting" },
        { from: "Introduzione agli esempi di bias nell'IA", to: "Conclusione" },
        { from: "Conclusione", to: "Il bias può manifestarsi in vari contesti e deve essere affrontato proattivamente dalle aziende tecnologiche" }
    ]);
}

window.addEventListener("DOMContentLoaded", init);