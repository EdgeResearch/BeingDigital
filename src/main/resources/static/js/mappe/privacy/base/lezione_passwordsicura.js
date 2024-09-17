function init() {
    // some constants that will be reused within templates
    const mt8 = new go.Margin(8, 0, 0, 0);
    const mr8 = new go.Margin(0, 8, 0, 0);
    const ml8 = new go.Margin(0, 0, 0, 8);
    const roundedRectangleParams = {
      parameter1: 2, // set the rounded corner
      spot1: go.Spot.TopLeft,
      spot2: go.Spot.BottomRight // make content go all the way to inside edges of rounded corners
    };

    myDiagram = new go.Diagram('myDiagramDiv', {
      // Put the diagram contents at the top center of the viewport
      initialDocumentSpot: go.Spot.Top,
      initialViewportSpot: go.Spot.Top,
      // OR: Scroll to show a particular node, once the layout has determined where that node is
      // "InitialLayoutCompleted": e => {
      //  var node = e.diagram.findNodeForKey(28);
      //  if (node !== null) e.diagram.commandHandler.scrollToPart(node);
      // },
      layout: new go.TreeLayout({
        // use a TreeLayout to position all of the nodes
        isOngoing: false, // don't relayout when expanding/collapsing panels
        treeStyle: go.TreeStyle.LastParents,
        // properties for most of the tree:
        angle: 90,
        layerSpacing: 80,
        // properties for the "last parents":
        alternateAngle: 0,
        alternateAlignment: go.TreeAlignment.Start,
        alternateNodeIndent: 15,
        alternateNodeIndentPastParent: 1,
        alternateNodeSpacing: 15,
        alternateLayerSpacing: 40,
        alternateLayerSpacingParentOverlap: 1,
        alternatePortSpot: new go.Spot(0.001, 1, 20, 0),
        alternateChildPortSpot: go.Spot.Left
      })
    });

    // This function provides a common style for most of the TextBlocks.
    function textStyle(field) {
      return function (node) {
        node.set({
          font: '12px Roboto, sans-serif',
          stroke: 'rgba(0, 0, 0, .60)',
          visible: false // only show textblocks when there is corresponding data for them
        });
        node.bind('visible', field, (val) => val !== undefined);
      }
    }

    // define Converters to be used for Bindings
    function theNationFlagConverter(nation) {
      return 'https://nwoods.com/images/emojiflags/' + nation + '.png';
    }

    // define the Node template
    myDiagram.nodeTemplate = new go.Node('Auto', {
      locationSpot: go.Spot.Top,
      isShadowed: true,
      shadowBlur: 1,
      shadowOffset: new go.Point(0, 1),
      shadowColor: 'rgba(0, 0, 0, .14)',
      // selection adornment to match shape of nodes
      selectionAdornmentTemplate: new go.Adornment('Auto')
        .add(
          new go.Shape('RoundedRectangle', { fill: null, stroke: '#7986cb', strokeWidth: 3 }).set(roundedRectangleParams),
          new go.Placeholder()
        ) // end Adornment
    })
      .add(
        new go.Shape('RoundedRectangle', { name: 'SHAPE', fill: '#ffffff', strokeWidth: 0 })
          .set(roundedRectangleParams)
          // gold if highlighted, white otherwise
          .bindObject('fill', 'isHighlighted', (h) => (h ? 'gold' : '#ffffff')),
        new go.Panel('Vertical')
          .add(
            new go.Panel('Horizontal', { margin: 8 })
              .add(
                new go.Picture({ // flag image, only visible if a nation is specified
                  margin: mr8,
                  visible: false,
                  desiredSize: new go.Size(50, 50)
                })
                  .bind('source', 'nation', theNationFlagConverter)
                  .bind('visible', 'nation', (nat) => nat !== undefined),
                new go.Panel('Table')
                  .add(
                    new go.TextBlock({
                      row: 0,
                      alignment: go.Spot.Left,
                      font: '16px Roboto, sans-serif',
                      stroke: 'rgba(0, 0, 0, .87)',
                      maxSize: new go.Size(160, NaN)
                    })
                      .bind('text', 'name'),
                    new go.TextBlock({
                      row: 1,
                      alignment: go.Spot.Left,
                      maxSize: new go.Size(160, NaN)
                    })
                      .apply(textStyle('title'))
                      .bind('text', 'title'),
                    go.GraphObject.build('PanelExpanderButton', { row: 0, column: 1, rowSpan: 2, margin: ml8 }, 'INFO')
                  )
              ),
            new go.Shape('LineH', {
              stroke: 'rgba(0, 0, 0, .60)',
              strokeWidth: 1,
              height: 1,
              stretch: go.Stretch.Horizontal
            })
              .bindObject('visible', undefined, null, null, 'INFO'), // only visible when info is expanded
            new go.Panel('Vertical', {
              name: 'INFO', // identify to the PanelExpanderButton
              stretch: go.Stretch.Horizontal, // take up whole available width
              margin: 8,
              defaultAlignment: go.Spot.Left // thus no need to specify alignment on each element
            })
              .add(
                new go.TextBlock()
                  .apply(textStyle('headOf'))
                  .bind('text', 'headOf', (head) => 'Head of: ' + head),
                new go.TextBlock()
                  .apply(textStyle('boss'))
                  .bind('margin', 'headOf', (head) => mt8) // some space above if there is also a headOf value
                  .bind('text', 'boss', (boss) => {
                    var boss = myDiagram.model.findNodeDataForKey(boss);
                    if (boss !== null) {
                      return 'Reporting to: ' + boss.name;
                    }
                    return '';
                  })
              )
          )
      );

    // define the Link template, a simple orthogonal line
    myDiagram.linkTemplate = new go.Link({
      routing: go.Routing.Orthogonal,
      corner: 5,
      selectable: false
    }).add(new go.Shape({ strokeWidth: 3, stroke: '#424242' })); // dark gray, rounded corner links

    // set up the nodeDataArray, describing each person/position
    var nodeDataArray = [
      {
        key: 0,
        name: 'Ban Ki-moon 반기문',
        nation: 'SouthKorea',
        title: 'Secretary-General of the United Nations',
        headOf: 'Secretariat'
      },
      {
        key: 1,
        boss: 0,
        name: "Patricia O'Brien",
        nation: 'Ireland',
        title: 'Under-Secretary-General for Legal Affairs and United Nations Legal Counsel',
        headOf: 'Office of Legal Affairs'
      },
      {
        key: 3,
        boss: 1,
        name: 'Peter Taksøe-Jensen',
        nation: 'Denmark',
        title: 'Assistant Secretary-General for Legal Affairs'
      },
      { key: 9, boss: 3, name: 'Other Employees' },
      {
        key: 4,
        boss: 1,
        name: 'Maria R. Vicien - Milburn',
        nation: 'Argentina',
        title: 'General Legal Division Director',
        headOf: 'General Legal Division'
      },
      { key: 10, boss: 4, name: 'Other Employees' },
      {
        key: 5,
        boss: 1,
        name: 'Václav Mikulka',
        nation: 'CzechRepublic',
        title: 'Codification Division Director',
        headOf: 'Codification Division'
      },
      { key: 11, boss: 5, name: 'Other Employees' },
      {
        key: 6,
        boss: 1,
        name: 'Sergei Tarassenko',
        nation: 'Russia',
        title: 'Division for Ocean Affairs and the Law of the Sea Director',
        headOf: 'Division for Ocean Affairs and the Law of the Sea'
      },
      {
        key: 12,
        boss: 6,
        name: 'Alexandre Tagore Medeiros de Albuquerque',
        nation: 'Brazil',
        title: 'Chairman of the Commission on the Limits of the Continental Shelf',
        headOf: 'The Commission on the Limits of the Continental Shelf'
      },
      {
        key: 17,
        boss: 12,
        name: 'Peter F. Croker',
        nation: 'Ireland',
        title: 'Chairman of the Committee on Confidentiality',
        headOf: 'The Committee on Confidentiality'
      },
      {
        key: 31,
        boss: 17,
        name: 'Michael Anselme Marc Rosette',
        nation: 'Seychelles',
        title: 'Vice Chairman of the Committee on Confidentiality'
      },
      {
        key: 32,
        boss: 17,
        name: 'Kensaku Tamaki',
        nation: 'Japan',
        title: 'Vice Chairman of the Committee on Confidentiality'
      },
      {
        key: 33,
        boss: 17,
        name: 'Osvaldo Pedro Astiz',
        nation: 'Argentina',
        title: 'Member of the Committee on Confidentiality'
      },
      {
        key: 34,
        boss: 17,
        name: 'Yuri Borisovitch Kazmin',
        nation: 'Russia',
        title: 'Member of the Committee on Confidentiality'
      },
      {
        key: 18,
        boss: 12,
        name: 'Philip Alexander Symonds',
        nation: 'Australia',
        title:
          'Chairman of the Committee on provision of scientific and technical advice to coastal States',
        headOf: 'Committee on provision of scientific and technical advice to coastal States'
      },
      {
        key: 35,
        boss: 18,
        name: 'Emmanuel Kalngui',
        nation: 'Cameroon',
        title:
          'Vice Chairman of the Committee on provision of scientific and technical advice to coastal States'
      },
      {
        key: 36,
        boss: 18,
        name: 'Sivaramakrishnan Rajan',
        nation: 'India',
        title:
          'Vice Chairman of the Committee on provision of scientific and technical advice to coastal States'
      },
      {
        key: 37,
        boss: 18,
        name: 'Francis L. Charles',
        nation: 'TrinidadAndTobago',
        title:
          'Member of the Committee on provision of scientific and technical advice to costal States'
      },
      {
        key: 38,
        boss: 18,
        name: 'Mihai Silviu German',
        nation: 'Romania',
        title:
          'Member of the Committee on provision of scientific and technical advice to costal States'
      },
      {
        key: 19,
        boss: 12,
        name: 'Lawrence Folajimi Awosika',
        nation: 'Nigeria',
        title: 'Vice Chairman of the Commission on the Limits of the Continental Shelf'
      },
      {
        key: 20,
        boss: 12,
        name: 'Harald Brekke',
        nation: 'Norway',
        title: 'Vice Chairman of the Commission on the Limits of the Continental Shelf'
      },
      {
        key: 21,
        boss: 12,
        name: 'Yong-Ahn Park',
        nation: 'SouthKorea',
        title: 'Vice Chairman of the Commission on the Limits of the Continental Shelf'
      },
      {
        key: 22,
        boss: 12,
        name: 'Abu Bakar Jaafar',
        nation: 'Malaysia',
        title: 'Chairman of the Editorial Committee',
        headOf: 'Editorial Committee'
      },
      {
        key: 23,
        boss: 12,
        name: 'Galo Carrera Hurtado',
        nation: 'Mexico',
        title: 'Chairman of the Training Committee',
        headOf: 'Training Committee'
      },
      {
        key: 24,
        boss: 12,
        name: 'Indurlall Fagoonee',
        nation: 'Mauritius',
        title: 'Member of the Commission on the Limits of the Continental Shelf'
      },
      {
        key: 25,
        boss: 12,
        name: 'George Jaoshvili',
        nation: 'Georgia',
        title: 'Member of the Commission on the Limits of the Continental Shelf'
      },
      {
        key: 26,
        boss: 12,
        name: 'Wenzhang Lu',
        nation: 'China',
        title: 'Member of the Commission on the Limits of the Continental Shelf'
      },
      {
        key: 27,
        boss: 12,
        name: 'Isaac Owusu Orudo',
        nation: 'Ghana',
        title: 'Member of the Commission on the Limits of the Continental Shelf'
      },
      {
        key: 28,
        boss: 12,
        name: 'Fernando Manuel Maia Pimentel',
        nation: 'Portugal',
        title: 'Member of the Commission on the Limits of the Continental Shelf'
      },
      {
        key: 7,
        boss: 1,
        name: 'Renaud Sorieul',
        nation: 'France',
        title: 'International Trade Law Division Director',
        headOf: 'International Trade Law Division'
      },
      { key: 13, boss: 7, name: 'Other Employees' },
      {
        key: 8,
        boss: 1,
        name: 'Annebeth Rosenboom',
        nation: 'Netherlands',
        title: 'Treaty Section Chief',
        headOf: 'Treaty Section'
      },
      {
        key: 14,
        boss: 8,
        name: 'Bradford Smith',
        nation: 'UnitedStates',
        title: 'Substantive Legal Issues Head',
        headOf: 'Substantive Legal Issues'
      },
      { key: 29, boss: 14, name: 'Other Employees' },
      {
        key: 15,
        boss: 8,
        name: 'Andrei Kolomoets',
        nation: 'Russia',
        title: 'Technical/Legal Issues Head',
        headOf: 'Technical/Legal Issues'
      },
      { key: 30, boss: 15, name: 'Other Employees' },
      { key: 16, boss: 8, name: 'Other Employees' },
      { key: 2, boss: 0, name: 'Heads of Other Offices/Departments' }
    ];

    // create the Model with data for the tree, and assign to the Diagram
    myDiagram.model = new go.TreeModel({
      nodeParentKeyProperty: 'boss', // this property refers to the parent node data
      nodeDataArray: nodeDataArray
    });

    // Overview
    myOverview = new go.Overview('myOverviewDiv', {
      observed: myDiagram, // tell it which Diagram to show and pan
      contentAlignment: go.Spot.Center
    });
  }

  // the Search functionality highlights all of the nodes that have at least one data property match a RegExp
  function searchDiagram() {
    // called by button
    var input = document.getElementById('mySearch');
    if (!input) return;
    myDiagram.focus();

    myDiagram.startTransaction('highlight search');

    if (input.value) {
      // search four different data properties for the string, any of which may match for success
      // create a case insensitive RegExp from what the user typed
      var safe = input.value.replace(/[.*+?^${}()|[\]\\]/g, '\\$&');
      var regex = new RegExp(safe, 'i');
      var results = myDiagram.findNodesByExample(
        { name: regex },
        { nation: regex },
        { title: regex },
        { headOf: regex }
      );
      myDiagram.highlightCollection(results);
      // try to center the diagram at the first node that was found
      if (results.count > 0) myDiagram.centerRect(results.first().actualBounds);
    } else {
      // empty string only clears highlighteds collection
      myDiagram.clearHighlighteds();
    }

    myDiagram.commitTransaction('highlight search');
  }
  window.addEventListener('DOMContentLoaded', init);