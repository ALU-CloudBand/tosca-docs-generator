package org.tosca.docs.generators.html;

import org.tosca.docs.model.CapabilityType;

public class HtmlCapabilityTypeGenerator extends HtmlGenerator {
    public HtmlCapabilityTypeGenerator(HtmlGenerator htmlGenerator) {
        super(htmlGenerator);
    }

    protected void add(CapabilityType capabilityType) {

        String indexId = getIndexId(capabilityType);

        html.h2()
                .id(indexId)
                .text(messages.getMessage("headers.capability_type", capabilityType.getId()))
                .end();

        addHierarchyTree(capabilityType);

        String description = localize(capabilityType.getId() + ".description", capabilityType.getDescription());
        if (description != null) {
            html
                    .div()
                    .classAttr("capability_type description")
                    .text(description)
                    .br()
                    .br()
                    .end();
        }

        addIdentifiersTable(capabilityType);

        addPropertiesTable(capabilityType);

        html.hr();
    }
}
