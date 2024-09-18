// Aggiungi dinamicamente lo stile al documento
const style = document.createElement('style');
style.innerHTML = `
    .node {
        stroke: #fff;
        stroke-width: 1.5px;
        cursor: pointer;
        transition: fill 0.2s;
    }
    .node:hover {
        fill: #1abc9c;
    }
    .link {
        stroke: #999;
        stroke-opacity: 0.6;
    }
    text {
        font-family: Montserrat;
        fill: #2c3e50;
    }
`;
document.head.appendChild(style);

// Codice per la creazione della visualizzazione D3
const svg = d3.select("svg"),
    width = +svg.attr("width"),
    height = +svg.attr("height");

const color = d3.scaleOrdinal(d3.schemeCategory10);

const simulation = d3.forceSimulation()
    .force("link", d3.forceLink().id(d => d.id).distance(80))
    .force("charge", d3.forceManyBody().strength(-300))
    .force("center", d3.forceCenter(width / 2, height / 2))
    .force("collision", d3.forceCollide().radius(20))
    .force("radial", d3.forceRadial(d => d.level * 100, width / 2, height / 2).strength(0.1));

// Inserisci il JSON direttamente nel codice
const graph = {
    "nodes": [
        {"id": "Cittadinanza Digitale"},
        {"id": "Accesso e Utilizzo delle Tecnologie"},
        {"id": "Competenze Digitali di Base"},
        {"id": "Competenze Informative e Critiche"},
        {"id": "Competenze di Comunicazione e Collaborazione"},
        {"id": "Competenze Civiche e Sociali"},
        {"id": "Competenze Avanzate e Professionali"},
        {"id": "Competenze di Problem Solving e Innovazione"},
        {"id": "Accesso alla Tecnologia"},
        {"id": "Utilizzo delle Tecnologie"},
        {"id": "Alfabetizzazione Digitale"},
        {"id": "Sicurezza Informatica"},
        {"id": "Ricerca di Informazioni"},
        {"id": "Gestione delle Informazioni"},
        {"id": "Comunicazione Online"},
        {"id": "Collaborazione Digitale"},
        {"id": "Partecipazione Civica Digitale"},
        {"id": "Comportamento Etico Online"},
        {"id": "Sviluppo e Programmazione"},
        {"id": "Analisi dei Dati"},
        {"id": "Problem Solving Digitale"},
        {"id": "Innovazione Digitale"}
    ],
    "links": [
        {"source": "Cittadinanza Digitale", "target": "Accesso e Utilizzo delle Tecnologie"},
        {"source": "Cittadinanza Digitale", "target": "Competenze Digitali di Base"},
        {"source": "Cittadinanza Digitale", "target": "Competenze Informative e Critiche"},
        {"source": "Cittadinanza Digitale", "target": "Competenze di Comunicazione e Collaborazione"},
        {"source": "Cittadinanza Digitale", "target": "Competenze Civiche e Sociali"},
        {"source": "Cittadinanza Digitale", "target": "Competenze Avanzate e Professionali"},
        {"source": "Cittadinanza Digitale", "target": "Competenze di Problem Solving e Innovazione"},
        {"source": "Accesso e Utilizzo delle Tecnologie", "target": "Accesso alla Tecnologia"},
        {"source": "Accesso e Utilizzo delle Tecnologie", "target": "Utilizzo delle Tecnologie"},
        {"source": "Competenze Digitali di Base", "target": "Alfabetizzazione Digitale"},
        {"source": "Competenze Digitali di Base", "target": "Sicurezza Informatica"},
        {"source": "Competenze Informative e Critiche", "target": "Ricerca di Informazioni"},
        {"source": "Competenze Informative e Critiche", "target": "Gestione delle Informazioni"},
        {"source": "Competenze di Comunicazione e Collaborazione", "target": "Comunicazione Online"},
        {"source": "Competenze di Comunicazione e Collaborazione", "target": "Collaborazione Digitale"},
        {"source": "Competenze Civiche e Sociali", "target": "Partecipazione Civica Digitale"},
        {"source": "Competenze Civiche e Sociali", "target": "Comportamento Etico Online"},
        {"source": "Competenze Avanzate e Professionali", "target": "Sviluppo e Programmazione"},
        {"source": "Competenze Avanzate e Professionali", "target": "Analisi dei Dati"},
        {"source": "Competenze di Problem Solving e Innovazione", "target": "Problem Solving Digitale"},
        {"source": "Competenze di Problem Solving e Innovazione", "target": "Innovazione Digitale"}
    ]
};

// Il resto del codice rimane invariato
const colorMap = {};

graph.nodes.forEach(node => {
    if (node.id === "Cittadinanza Digitale") {
        node.level = 1;
    } else {
        const parentLink = graph.links.find(link => link.target === node.id);
        if (parentLink && parentLink.source === "Cittadinanza Digitale") {
            node.level = 2;
            colorMap[node.id] = color(node.id);
        } else if (parentLink) {
            node.level = 3;
            node.parent = parentLink.source;
        }
    }
});

const link = svg.append("g")
    .attr("class", "links")
    .selectAll("line")
    .data(graph.links)
    .enter().append("line")
    .attr("class", "link");

const node = svg.append("g")
    .attr("class", "nodes")
    .selectAll("circle")
    .data(graph.nodes)
    .enter().append("circle")
    .attr("class", "node")
    .attr("r", d => d.level === 1 ? 20 : 10)
    .attr("fill", d => {
        if (d.level === 1) return "#ff5733";
        if (d.level === 2) return colorMap[d.id];
        if (d.level === 3) {
            const parentColor = d3.color(colorMap[d.parent]);
            return parentColor ? parentColor.brighter(1.5) : "#ccc";
        }
    })
    .call(d3.drag()
        .on("start", dragstarted)
        .on("drag", dragged)
        .on("end", dragended))
    .on("click", (event, d) => {
        const fileName = d.id.replace(/\s+/g, '_') + ".html";
        window.location.href = fileName;
    });

node.append("title").text(d => d.id);

const text = svg.append("g")
    .attr("class", "texts")
    .selectAll("text")
    .data(graph.nodes)
    .enter().append("text")
    .attr("dy", 3)
    .attr("x", 12)
    .style("font-size", d => d.level === 1 ? "16px" : "12px")
    .text(d => d.id);

simulation.nodes(graph.nodes).on("tick", ticked);
simulation.force("link").links(graph.links);

function ticked() {
    link
        .attr("x1", d => d.source.x)
        .attr("y1", d => d.source.y)
        .attr("x2", d => d.target.x)
        .attr("y2", d => d.target.y);

    node
        .attr("cx", d => d.x)
        .attr("cy", d => d.y);

    text
        .attr("x", d => d.x)
        .attr("y", d => d.y);
}

function dragstarted(event, d) {
    if (!event.active) simulation.alphaTarget(0.3).restart();
    d.fx = d.x;
    d.fy = d.y;
}

function dragged(event, d) {
    d.fx = event.x;
    d.fy = event.y;
}

function dragended(event, d) {
    if (!event.active) simulation.alphaTarget(0);
    d.fx = null;
    d.fy = null;
}
