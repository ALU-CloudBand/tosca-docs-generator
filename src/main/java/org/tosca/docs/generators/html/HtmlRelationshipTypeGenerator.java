package org.tosca.docs.generators.html;

import org.tosca.docs.model.RelationshipType;

public class HtmlRelationshipTypeGenerator extends HtmlGenerator {
    public HtmlRelationshipTypeGenerator(HtmlGenerator htmlGenerator) {
        super(htmlGenerator);
    }

    protected void add(RelationshipType relationshipType) {

        String indexId = getIndexId(relationshipType);

        html.h2()
                .id(indexId)
                .text(messages.getMessage("headers.relationship_type", relationshipType.getId()))
                .end();

        addHierarchyTree(relationshipType);

        String description = localize(relationshipType.getId() + ".description", relationshipType.getDescription());
        if (description != null) {
            html
                    .div()
                    .classAttr("relationship_type description")
                    .text(description)
                    .br()
                    .br()
                    .end();
        }

        addIdentifiersTable(relationshipType);

        addPropertiesTable(relationshipType);

        addAttributesTable(relationshipType);

        html.hr();
    }
}
