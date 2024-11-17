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
        { key: "Benefici dell'IA in diversi settori", level: 0 },
        { key: "Settore sanitario", level: 1 },
        { key: "Diagnosi lente e complesse", level: 2 },
        { key: "Algoritmi di apprendimento automatico analizzano immagini mediche, dati clinici", level: 3 },
        { key: "Diagnosi più rapide e accurate, con miglioramenti per malattie come il cancro", level: 3 },
        { key: "Accesso limitato alle cure", level: 2 },
        { key: "Assistenti virtuali e chatbot IA per la telemedicina", level: 3 },
        { key: "Più persone possono accedere a diagnosi e consulenze mediche remote", level: 3 },
        { key: "Ricerca di farmaci lunga e costosa", level: 2 },
        { key: "IA per simulare reazioni chimiche e scoprire nuovi farmaci", level: 3 },
        { key: "Riduzione dei tempi e dei costi di ricerca di nuovi medicinali", level: 3 },
        { key: "Settore ambientale", level: 1 },
        { key: "Cambiamenti climatici", level: 2 },
        { key: "Algoritmi che analizzano modelli climatici", level: 3 },
        { key: "Miglioramento della previsione dei cambiamenti climatici", level: 3 },
        { key: "Disastri naturali", level: 2 },
        { key: "Sistemi IA per il monitoraggio e l'analisi di dati atmosferici", level: 3 },
        { key: "Previsioni migliori e sistemi di allerta precoce", level: 3 },
        { key: "Inquinamento", level: 2 },
        { key: "Sistemi di IA per ottimizzare l'uso delle risorse", level: 3 },
        { key: "Riduzione delle emissioni e ottimizzazione dell'energia", level: 3 },
        { key: "Settore dell'istruzione", level: 1 },
        { key: "Accesso disomogeneo all'istruzione", level: 2 },
        { key: "IA per creare contenuti educativi personalizzati e adattivi", level: 3 },
        { key: "Maggiore accesso all'educazione di qualità in tutto il mondo", level: 3 },
        { key: "Mancanza di feedback immediato", level: 2 },
        { key: "Sistemi di IA che valutano gli studenti in tempo reale", level: 3 },
        { key: "Migliore monitoraggio del progresso individuale", level: 3 },
        { key: "Come l'IA risolve problemi complessi", level: 0 },
        { key: "Combattere le pandemie e malattie", level: 1 },
        { key: "COVID-19", level: 2 },
        { key: "IA utilizzata per accelerare lo sviluppo di vaccini e analizzare dati sanitari in tempo reale", level: 3 },
        { key: "Malattie rare", level: 2 },
        { key: "IA analizza dati medici complessi per identificare correlazioni", level: 3 },
        { key: "Ridurre la fame nel mondo", level: 1 },
        { key: "Previsioni climatiche migliorate", level: 2 },
        { key: "Consentono agli agricoltori di pianificare meglio le colture", level: 3 },
        { key: "Sistemi di irrigazione ottimizzati", level: 2 },
        { key: "Utilizzano sensori e IA per ridurre lo spreco d'acqua e aumentare la resa", level: 3 },
        { key: "Monitoraggio del suolo", level: 2 },
        { key: "IA analizza la salute del suolo e suggerisce interventi correttivi", level: 3 },
        { key: "Prevenire i conflitti", level: 1 },
        { key: "Previsione di tensioni sociali", level: 2 },
        { key: "IA analizza social media e notizie per identificare segnali di conflitti sociali", level: 3 },
        { key: "Diplomazia basata sui dati", level: 2 },
        { key: "IA suggerisce strategie diplomatiche basate su modelli storici", level: 3 },
        { key: "Conclusione: L'IA come Strumento, non come Minaccia", level: 0 },
        { key: "L'IA non è intrinsecamente 'cattiva'", level: 1 },
        { key: "I suoi effetti dipendono da come viene progettata e regolamentata", level: 2 },
        { key: "Ruolo nel risolvere problemi globali", level: 1 },
        { key: "Salute, ambiente, istruzione e sicurezza beneficiano dell'IA", level: 2 },
        { key: "Bilanciare rischi e benefici", level: 1 },
        { key: "Con una regolamentazione adeguata, l'IA può portare un futuro più equo", level: 2 }
    ], [
        { from: "Benefici dell'IA in diversi settori", to: "Settore sanitario" },
        { from: "Settore sanitario", to: "Diagnosi lente e complesse" },
        { from: "Diagnosi lente e complesse", to: "Algoritmi di apprendimento automatico analizzano immagini mediche, dati clinici" },
        { from: "Diagnosi lente e complesse", to: "Diagnosi più rapide e accurate, con miglioramenti per malattie come il cancro" },
        { from: "Settore sanitario", to: "Accesso limitato alle cure" },
        { from: "Accesso limitato alle cure", to: "Assistenti virtuali e chatbot IA per la telemedicina" },
        { from: "Accesso limitato alle cure", to: "Più persone possono accedere a diagnosi e consulenze mediche remote" },
        { from: "Settore sanitario", to: "Ricerca di farmaci lunga e costosa" },
        { from: "Ricerca di farmaci lunga e costosa", to: "IA per simulare reazioni chimiche e scoprire nuovi farmaci" },
        { from: "Ricerca di farmaci lunga e costosa", to: "Riduzione dei tempi e dei costi di ricerca di nuovi medicinali" },
        { from: "Benefici dell'IA in diversi settori", to: "Settore ambientale" },
        { from: "Settore ambientale", to: "Cambiamenti climatici" },
        { from: "Cambiamenti climatici", to: "Algoritmi che analizzano modelli climatici" },
        { from: "Cambiamenti climatici", to: "Miglioramento della previsione dei cambiamenti climatici" },
        { from: "Settore ambientale", to: "Disastri naturali" },
        { from: "Disastri naturali", to: "Sistemi IA per il monitoraggio e l'analisi di dati atmosferici" },
        { from: "Disastri naturali", to: "Previsioni migliori e sistemi di allerta precoce" },
        { from: "Settore ambientale", to: "Inquinamento" },
        { from: "Inquinamento", to: "Sistemi di IA per ottimizzare l'uso delle risorse" },
        { from: "Inquinamento", to: "Riduzione delle emissioni e ottimizzazione dell'energia" },
        { from: "Benefici dell'IA in diversi settori", to: "Settore dell'istruzione" },
        { from: "Settore dell'istruzione", to: "Accesso disomogeneo all'istruzione" },
        { from: "Accesso disomogeneo all'istruzione", to: "IA per creare contenuti educativi personalizzati e adattivi" },
        { from: "Accesso disomogeneo all'istruzione", to: "Maggiore accesso all'educazione di qualità in tutto il mondo" },
        { from: "Settore dell'istruzione", to: "Mancanza di feedback immediato" },
        { from: "Mancanza di feedback immediato", to: "Sistemi di IA che valutano gli studenti in tempo reale" },
        { from: "Mancanza di feedback immediato", to: "Migliore monitoraggio del progresso individuale" },
        { from: "Benefici dell'IA in diversi settori", to: "Come l'IA risolve problemi complessi" },
        { from: "Come l'IA risolve problemi complessi", to: "Combattere le pandemie e malattie" },
        { from: "Combattere le pandemie e malattie", to: "COVID-19" },
        { from: "COVID-19", to: "IA utilizzata per accelerare lo sviluppo di vaccini e analizzare dati sanitari in tempo reale" },
        { from: "Combattere le pandemie e malattie", to: "Malattie rare" },
        { from: "Malattie rare", to: "IA analizza dati medici complessi per identificare correlazioni" },
        { from: "Come l'IA risolve problemi complessi", to: "Ridurre la fame nel mondo" },
        { from: "Ridurre la fame nel mondo", to: "Previsioni climatiche migliorate" },
        { from: "Previsioni climatiche migliorate", to: "Consentono agli agricoltori di pianificare meglio le colture" },
        { from: "Ridurre la fame nel mondo", to: "Sistemi di irrigazione ottimizzati" },
        { from: "Sistemi di irrigazione ottimizzati", to: "Utilizzano sensori e IA per ridurre lo spreco d'acqua e aumentare la resa" },
        { from: "Ridurre la fame nel mondo", to: "Monitoraggio del suolo" },
        { from: "Monitoraggio del suolo", to: "IA analizza la salute del suolo e suggerisce interventi correttivi" },
        { from: "Come l'IA risolve problemi complessi", to: "Prevenire i conflitti" },
        { from: "Prevenire i conflitti", to: "Previsione di tensioni sociali" },
        { from: "Previsione di tensioni sociali", to: "IA analizza social media e notizie per identificare segnali di conflitti sociali" },
        { from: "Prevenire i conflitti", to: "Diplomazia basata sui dati" },
        { from: "Diplomazia basata sui dati", to: "IA suggerisce strategie diplomatiche basate su modelli storici" },
        { from: "Benefici dell'IA in diversi settori", to: "Conclusione: L'IA come Strumento, non come Minaccia" },
        { from: "Conclusione: L'IA come Strumento, non come Minaccia", to: "L'IA non è intrinsecamente 'cattiva'" },
        { from: "L'IA non è intrinsecamente 'cattiva'", to: "I suoi effetti dipendono da come viene progettata e regolamentata" },
        { from: "Conclusione: L'IA come Strumento, non come Minaccia", to: "Ruolo nel risolvere problemi globali" },
        { from: "Ruolo nel risolvere problemi globali", to: "Salute, ambiente, istruzione e sicurezza beneficiano dell'IA" },
        { from: "Conclusione: L'IA come Strumento, non come Minaccia", to: "Bilanciare rischi e benefici" },
        { from: "Bilanciare rischi e benefici", to: "Con una regolamentazione adeguata, l'IA può portare un futuro più equo" }
    ]);
}

window.addEventListener("DOMContentLoaded", init);
