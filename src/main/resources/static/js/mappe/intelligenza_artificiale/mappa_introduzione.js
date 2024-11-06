function init() {
    var $ = go.GraphObject.make;

    var myDiagram = $(go.Diagram, "mapDiv", {
        "undoManager.isEnabled": true,
        layout: $(go.TreeLayout, { angle: 90, layerSpacing: 30, nodeSpacing: 20 })  // Riduzione dello spazio tra i livelli e i nodi
    });

    // Funzione per ottenere un colore in base al livello del nodo
    function getColorForLayer(layer) {
        var colors = ["#f7b7a3", "#f7dda3", "#a3f7b7", "#a3d4f7", "#d0a3f7"];
        return colors[layer % colors.length];  // Usa un colore diverso in base al livello
    }

    // Definisci il template per i nodi
    myDiagram.nodeTemplate =
        $(go.Node, "Auto",
            $(go.Shape, "RoundedRectangle", { strokeWidth: 0, fill: "lightblue", width: 250, height: 50 }, // Ridimensionamento
                new go.Binding("fill", "color")),
            $(go.TextBlock,
                { margin: 5, font: "bold 8pt sans-serif", stroke: '#333', wrap: go.TextBlock.WrapFit, textAlign: "center" }, // Ridimensionamento del testo
                new go.Binding("text", "key"))
        );

    // Assegna i colori dinamicamente in base alla profondità (layer)
    myDiagram.addDiagramListener("InitialLayoutCompleted", function(e) {
        e.diagram.nodes.each(function(node) {
            var layer = node.findTreeLevel(); // Trova il livello del nodo nell'albero
            node.data.color = getColorForLayer(layer); // Assegna il colore in base al livello
        });
        myDiagram.updateAllTargetBindings(); // Aggiorna il diagramma per riflettere i colori
    });

    // Modello dati della mappa concettuale
    myDiagram.model = new go.TreeModel([
        { key: "Definizione di Intelligenza Artificiale", color: "lightcoral" },
        { key: "Introduzione all'IA", parent: "Definizione di Intelligenza Artificiale" },
        { key: "Definizione", parent: "Introduzione all'IA" },
        { key: "Apprendimento", parent: "Introduzione all'IA" },
        { key: "Tecnologia per attività umane", parent: "Definizione" },
        { key: "Insegnare alle macchine", parent: "Definizione" },
        { key: "Apprendimento dai dati", parent: "Apprendimento" },
        { key: "Miglioramento delle performance", parent: "Apprendimento" },
        { key: "Decisoni accurate", parent: "Apprendimento" },
        { key: "L'IA e l'intelligenza umana", parent: "Definizione di Intelligenza Artificiale" },
        { key: "Confronto", parent: "L'IA e l'intelligenza umana" },
        { key: "Osservazione: Alta vs Limitata", parent: "Confronto" },
        { key: "Apprendimento: Intuitivo vs Algoritmi", parent: "Confronto" },
        { key: "Adattamento: Flessibile vs Limitato", parent: "Confronto" },
        { key: "Tipi di Intelligenza Artificiale", parent: "Definizione di Intelligenza Artificiale" },
        { key: "IA Debole", parent: "Tipi di Intelligenza Artificiale" },
        { key: "Compiti specifici", parent: "IA Debole" },
        { key: "Esempi: riconoscimento facciale", parent: "IA Debole" },
        { key: "IA Forte", parent: "Tipi di Intelligenza Artificiale" },
        { key: "Concetto teorico", parent: "IA Forte" },
        { key: "Capacità cognitive umane", parent: "IA Forte" },
        { key: "Non realizzata", parent: "IA Forte" },
        { key: "IA Debole vs IA Forte", parent: "Definizione di Intelligenza Artificiale" },
        { key: "IA Debole: potente nei dati", parent: "IA Debole vs IA Forte" },
        { key: "IA Forte: versatilità non raggiunta", parent: "IA Debole vs IA Forte" },
        { key: "Limitazioni e futuro dell'IA", parent: "Definizione di Intelligenza Artificiale" },
        { key: "Limitazioni", parent: "Limitazioni e futuro dell'IA" },
        { key: "Mancanza di coscienza", parent: "Limitazioni" },
        { key: "Dipendenza dai dati", parent: "Limitazioni" },
        { key: "Futuro", parent: "Limitazioni e futuro dell'IA" },
        { key: "Deep Learning", parent: "Futuro" },
        { key: "Maggiore autonomia", parent: "Futuro" },
        { key: "Conclusione", parent: "Definizione di Intelligenza Artificiale" },
        { key: "Tecnologia trasformativa", parent: "Conclusione" },
        { key: "Potenziale immenso", parent: "Conclusione" },
        { key: "Importanza comprensione umana", parent: "Conclusione" }
    ]);
}

window.addEventListener('DOMContentLoaded', init);