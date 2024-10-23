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

    const graph = {
        "nodes": [
            {"id": "Data Preparation"},
            {"id": "Data Collection"},
            {"id": "Data Cleaning"},
            {"id": "Data Transformation"},
            {"id": "Data Reduction"},
            {"id": "Data Splitting"},
            {"id": "Data Augmentation"}
        ],
        "links": [
            {"source": "Data Preparation", "target": "Data Collection"},
            {"source": "Data Preparation", "target": "Data Cleaning"},
            {"source": "Data Preparation", "target": "Data Transformation"},
            {"source": "Data Preparation", "target": "Data Reduction"},
            {"source": "Data Preparation", "target": "Data Splitting"},
            {"source": "Data Preparation", "target": "Data Augmentation"}
        ]
    };

    const simulation = d3.forceSimulation()
        .force("link", d3.forceLink().id(d => d.id).distance(100))
        .force("charge", d3.forceManyBody().strength(-200))
        .force("center", d3.forceCenter(width / 2, height / 2));

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

    // Posiziona il nodo centrale e i nodi attorno in modo circolare
    const radius = 150; // Raggio della circonferenza

    // Posiziona i nodi
    graph.nodes.forEach((node, index) => {
        if (node.id === "Data Preparation") {
            node.x = width / 2;
            node.y = height / 2;
        } else {
            const angle = (index / (graph.nodes.length - 1)) * 2 * Math.PI; // Calcola l'angolo per la disposizione circolare
            node.x = width / 2 + radius * Math.cos(angle);
            node.y = height / 2 + radius * Math.sin(angle);
        }
    });

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
