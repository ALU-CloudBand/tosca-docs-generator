package org.tosca.docs.generators.html;

import org.tosca.docs.model.NodeType;

public class HtmlNodeTypeGenerator extends HtmlGenerator {
    public HtmlNodeTypeGenerator(HtmlGenerator htmlGenerator) {
        super(htmlGenerator);
    }

    protected void add(NodeType nodeType) {

        String indexId = getIndexId(nodeType);

        html.h2()
                .id(indexId)
                .text(messages.getMessage("headers.node_type", nodeType.getId()))
                .end();

        addHierarchyTree(nodeType);

        String description = localize(nodeType.getId() + ".description", nodeType.getDescription());
        if (description != null) {
            html
                    .div()
                    .classAttr("node_type description")
                    .text(description)
                    .br()
                    .br()
                    .end();
        }

        addIdentifiersTable(nodeType);

        addPropertiesTable(nodeType);

        addAttributesTable(nodeType);

        addCapabilitiesTable(nodeType);

        addInterfacesTable(nodeType);

        html.hr();
    }
}
