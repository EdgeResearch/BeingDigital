document.addEventListener('DOMContentLoaded', function() {
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

    const svg = d3.select("svg"),
        width = +svg.attr("width"),
        height = +svg.attr("height");

    const color = d3.scaleOrdinal(d3.schemeCategory10);

    // Funzione per calcolare il livello gerarchico di ciascun nodo
    const hierarchyLevels = {
        "Intelligenza Artificiale": 0,
        "Machine Learning": 1,
        "Sistemi Esperti, ecc.": 1,
        "Deep Learning": 2,
        "Altri tipi di ML": 2
    };

    const simulation = d3.forceSimulation()
        .force("link", d3.forceLink().id(d => d.id).distance(100))
        .force("charge", d3.forceManyBody().strength(-200))
        .force("center", d3.forceCenter(width / 2, height / 2))
        .force("y", d3.forceY(d => hierarchyLevels[d.id] * 100).strength(1)); // Mantiene i nodi allineati verticalmente

    const graph = {
        "nodes": [
            {"id": "Intelligenza Artificiale"},
            {"id": "Machine Learning"},
            {"id": "Sistemi Esperti, ecc."},
            {"id": "Deep Learning"},
            {"id": "Altri tipi di ML"}
        ],
        "links": [
            {"source": "Intelligenza Artificiale", "target": "Machine Learning"},
            {"source": "Intelligenza Artificiale", "target": "Sistemi Esperti, ecc."},
            {"source": "Machine Learning", "target": "Deep Learning"},
            {"source": "Machine Learning", "target": "Altri tipi di ML"}
        ]
    };

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
        .attr("r", 15)
        .attr("fill", (d, i) => color(i))
        .call(d3.drag()
            .on("start", dragstarted)
            .on("drag", dragged)
            .on("end", dragended));

    node.append("title").text(d => d.id);

    const text = svg.append("g")
        .attr("class", "texts")
        .selectAll("text")
        .data(graph.nodes)
        .enter().append("text")
        .attr("dy", 3)
        .attr("x", 12)
        .style("font-size", "12px")
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
});
