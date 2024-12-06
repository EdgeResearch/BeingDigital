function init() {
  const mt8 = new go.Margin(8, 0, 0, 0);
  const mr8 = new go.Margin(0, 8, 0, 0);
  const ml8 = new go.Margin(0, 0, 0, 8);
  const roundedRectangleParams = {
    parameter1: 2,
    spot1: go.Spot.TopLeft,
    spot2: go.Spot.BottomRight
  };

  myDiagram = new go.Diagram('myDiagramDiv', {
    initialDocumentSpot: go.Spot.Top,
    initialViewportSpot: go.Spot.Top,
    layout: new go.TreeLayout({
      isOngoing: false,
      treeStyle: go.TreeStyle.LastParents,
      angle: 90,
      layerSpacing: 80
    })
  });

  function textStyle(field) {
    return function (node) {
      node.set({
        font: '12px Roboto, sans-serif',
        stroke: 'rgba(0, 0, 0, .60)',
        visible: false
      });
      node.bind('visible', field, (val) => val !== undefined);
    }
  }

  function theNationFlagConverter(nation) {
    return 'https://nwoods.com/images/emojiflags/' + nation + '.png';
  }

  myDiagram.nodeTemplate = new go.Node('Auto',
      {
    locationSpot: go.Spot.Top,
    isShadowed: true,
    shadowBlur: 1,
    shadowOffset: new go.Point(0, 1),
    shadowColor: 'rgba(0, 0, 0, .14)',
    selectionAdornmentTemplate: new go.Adornment('Auto')
        .add(
            new go.Shape('RoundedRectangle', { fill: null, stroke: '#7986cb', strokeWidth: 3 }).set(roundedRectangleParams),
            new go.Placeholder()
        )
  })
      .add(
          new go.Shape('RoundedRectangle', { name: 'SHAPE', fill: '#ffffff', strokeWidth: 0 })
              .set(roundedRectangleParams)
              .bindObject('fill', 'isHighlighted', (h) => (h ? 'gold' : '#ffffff')),
          new go.Panel('Vertical')
              .add(
                  new go.Panel('Horizontal', { margin: 8 })
                      .add(
                          new go.TextBlock({ row: 0, alignment: go.Spot.Left, font: '16px Roboto, sans-serif', stroke: 'rgba(0, 0, 0, .87)', maxSize: new go.Size(160, NaN) })
                              .bind('text', 'name'),
                          new go.TextBlock({ row: 1, alignment: go.Spot.Left, maxSize: new go.Size(160, NaN) })
                              .apply(textStyle('title'))
                              .bind('text', 'title'),
                          go.GraphObject.build('PanelExpanderButton', { row: 0, column: 1, rowSpan: 2, margin: ml8 }, 'INFO')
                      ),
                  new go.Shape('LineH', { stroke: 'rgba(0, 0, 0, .60)', strokeWidth: 1, height: 1, stretch: go.Stretch.Horizontal })
                      .bindObject('visible', undefined, null, null, 'INFO'),
                  new go.Panel('Vertical', { name: 'INFO', stretch: go.Stretch.Horizontal, margin: 8, defaultAlignment: go.Spot.Left })
                      .add(
                          new go.TextBlock().apply(textStyle('headOf')).bind('text', 'headOf', (head) => 'Head of: ' + head),
                          new go.TextBlock().apply(textStyle('boss')).bind('margin', 'headOf', (head) => mt8).bind('text', 'boss', (boss) => {
                            var boss = myDiagram.model.findNodeDataForKey(boss);
                            if (boss !== null) {
                              return 'Reporting to: ' + boss.name;
                            }
                            return '';
                          })
                      )
              )
      );

  myDiagram.linkTemplate = new go.Link({
    routing: go.Routing.Orthogonal,
    corner: 5,
    selectable: false
  }).add(new go.Shape({ strokeWidth: 3, stroke: '#424242' }));

  var nodeDataArray = [
    { key: 0, name: 'Lunghezza della Password', title: 'Importanza della lunghezza e suggerimenti' },
    { key: 1, boss: 0, name: 'Caratteri Usati', title: 'Uso di numeri, lettere, simboli' },
    { key: 2, boss: 0, name: 'Significato della Password', title: 'Evitare parole comuni e usare caratteri casuali' }
  ];

  myDiagram.model = new go.TreeModel({
    nodeParentKeyProperty: 'boss',
    nodeDataArray: nodeDataArray
  });

  myOverview = new go.Overview('myOverviewDiv', {
    observed: myDiagram,
    contentAlignment: go.Spot.Center
  });
}
function searchDiagram() {
  var input = document.getElementById('mySearch');
  if (!input) return;
  myDiagram.focus();

  myDiagram.startTransaction('highlight search');

  if (input.value) {
    var safe = input.value.replace(/[.*+?^${}()|[\]\\]/g, '\\$&');
    var regex = new RegExp(safe, 'i');
    var results = myDiagram.findNodesByExample(
        { name: regex },
        { title: regex }
    );
    myDiagram.highlightCollection(results);
    if (results.count > 0) myDiagram.centerRect(results.first().actualBounds);
  } else {
    myDiagram.clearHighlighteds();
  }

  myDiagram.commitTransaction('highlight search');
}
window.addEventListener('DOMContentLoaded', init);
