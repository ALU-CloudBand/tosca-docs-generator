package org.tosca.docs.generators.html;

import org.tosca.docs.model.CapabilityType;
import org.tosca.docs.model.NodeType;
import org.tosca.docs.model.impl.NodeTypeImpl;

import java.util.List;

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

        addAttributesTable(capabilityType);

        addValidSourceTypes(capabilityType);

        html.hr();
    }

    @SuppressWarnings("unchecked")
    protected void addValidSourceTypes(CapabilityType capabilityType) {
        List<String> validSourceTypes = capabilityType.getValidSourceTypes();

        if (!validSourceTypes.isEmpty()) {

            html
                    .h3()
                    .id(capabilityType.getId() + "_validSourceTypes")
                    .text(localize("headers.validSourceTypes"))
                    .end();

            html.ul();
            for (String sourceTypeName : validSourceTypes) {
                html.li();

                NodeType sourceType = spec.getTypeByClassAndName(NodeTypeImpl.class, sourceTypeName);

                if (sourceType == null) {
                    html.text(sourceTypeName);
                } else {
                    html.a()
                            .href("#" + getIndexId(sourceType))
                            .title(sourceType.getDescription())
                            .text(sourceType.getId())
                            .end(); // a
                }

                html.end(); // li
            }

            html.end(); // ul
        }
    }
}
