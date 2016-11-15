package org.tosca.docs.generators.html;

import com.googlecode.jatl.Html;
import org.apache.commons.io.IOUtils;
import org.tosca.docs.model.*;
import org.tosca.docs.utils.Messages;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.*;

public class HtmlGenerator {
    public static final String STYLE_SYSTEM_PROPERTY = "org.tosca.docs.generators.html.style";
    public static final String HEADERS_CAPABILITY_TYPES = "headers.capability_types";
    public static final String CAPABILITY_TYPES_INDEX = "capability_types_index";
    public static final String RELATIONSHIP_TYPES_INDEX = "relationship_types_index";
    public static final String NODE_TYPES_INDEX = "node_types_index";
    public static final String HEADERS_RELATIONSHIP_TYPES = "headers.relationship_types";
    public static final String HEADERS_NODE_TYPES = "headers.node_types";
    public static final String NODE_TYPES_ID = "node_types";
    public static final String RELATIONSHIP_TYPES_ID = "relationship_types";
    public static final String CAPABILITY_TYPES_ID = "capability_types";

    protected final ToscaSpec spec;
    protected Messages messages;
    protected Html html;

    /**
     *
     * Locale can be set using the <source>-Duser.language=es</source> JVM property
     *
     * @param spec The specification to generate the docs upon
     */
    public HtmlGenerator(ToscaSpec spec) {
        this.spec = spec;
        this.messages = new Messages();
    }

    public HtmlGenerator(HtmlGenerator htmlGenerator) {
        this.spec = htmlGenerator.spec;
        this.html = htmlGenerator.html;
        this.messages = htmlGenerator.messages;
    }

    public void write(Writer writer) throws IOException {

        html = new Html5(writer);

        html.raw("<!DOCTYPE html>");
        html.html().lang(messages.getMessage("spec.lang"));

        addHead();

        html.body();

        html.div().id("content");
        html.div().id("left");

        addIndex();

        html.end(); // div #left

        html.div().id("right");

        html.hr();
        html.h1().id(NODE_TYPES_ID).classAttr("entities_header").text(localize(HEADERS_NODE_TYPES)).end();
        html.hr();

        HtmlNodeTypeGenerator nodeTypeGenerator = createNodeTypeGenerator();
        for (NodeType nodeType : spec.getNodeTypes()) {
            nodeTypeGenerator.add(nodeType);
        }

        html.h1().id(RELATIONSHIP_TYPES_ID).classAttr("entities_header").text(localize(HEADERS_RELATIONSHIP_TYPES)).end();
        html.hr();

        HtmlRelationshipTypeGenerator relationshipTypeGenerator = createRelationshipTypeGenerator();
        for (RelationshipType relationshipType : spec.getRelationshipTypes()) {
            relationshipTypeGenerator.add(relationshipType);
        }

        html.h1().id(CAPABILITY_TYPES_ID).classAttr("entities_header").text(localize(HEADERS_CAPABILITY_TYPES)).end();
        html.hr();

        HtmlCapabilityTypeGenerator capabiltyTypeGenerator = createCapabilityTypeGenerator();
        for (CapabilityType capabilityType : spec.getCapabilityTypes()) {
            capabiltyTypeGenerator.add(capabilityType);
        }

        html.endAll();
    }

    protected HtmlRelationshipTypeGenerator createRelationshipTypeGenerator() {
        return new HtmlRelationshipTypeGenerator(this);
    }

    protected HtmlCapabilityTypeGenerator createCapabilityTypeGenerator() {
        return new HtmlCapabilityTypeGenerator(this);
    }

    protected HtmlNodeTypeGenerator createNodeTypeGenerator() {
        return new HtmlNodeTypeGenerator(this);
    }

    protected void addIndex() {

        html.div().id("index");

        addIndexHeadersLinks();

        html.h2().id(NODE_TYPES_INDEX).text(localize(HEADERS_NODE_TYPES)).end();

        for (NodeType nodeType : spec.getNodeTypes()) {
            html
                    .a()
                    .href("#" + getIndexId(nodeType))
                    .title(nodeType.getDescription())
                    .text(nodeType.getTypeUri())
                    .end();
        }

        html.hr();

        html.h2().id(RELATIONSHIP_TYPES_INDEX).text(localize(HEADERS_RELATIONSHIP_TYPES)).end();

        for (RelationshipType relationshipType : spec.getRelationshipTypes()) {
            html
                    .a()
                    .href("#" + getIndexId(relationshipType))
                    .title(relationshipType.getDescription())
                    .text(relationshipType.getTypeUri())
                    .end();
        }

        html.hr();

        html.h2().id(CAPABILITY_TYPES_INDEX).text(localize(HEADERS_CAPABILITY_TYPES)).end();

        for (CapabilityType capabilityType : spec.getCapabilityTypes()) {
            html
                    .a()
                    .href("#" + getIndexId(capabilityType))
                    .title(capabilityType.getDescription())
                    .text(capabilityType.getTypeUri())
                    .end();
        }

        html.end(); // div
    }

    protected void addIndexHeadersLinks() {
        html
                .a()
                .href("#" + NODE_TYPES_INDEX)
                .text(localize(HEADERS_NODE_TYPES))
                .end();

        html
                .a()
                .href("#" + RELATIONSHIP_TYPES_INDEX)
                .text(localize(HEADERS_RELATIONSHIP_TYPES))
                .end();

        html
                .a()
                .href("#" + CAPABILITY_TYPES_INDEX)
                .text(localize(HEADERS_CAPABILITY_TYPES))
                .end();
    }

    protected void addHead() throws IOException {

        html.head();

        String title = spec.getTitle();
        if (title == null) {
            title = localize("spec.title");
        }

        if (title != null) {
            html.title().text(title).end();
        }

        String charset = messages.getMessage("spec.charset");
        if (charset != null) {
            html.meta().charset(charset);
        }

        html.style().type("text/css");

        String styleFile = System.getProperty(STYLE_SYSTEM_PROPERTY, "/org/tosca/docs/default.css");
        try (InputStream resourceAsStream = Messages.class.getResourceAsStream(styleFile)) {
            String style = IOUtils.toString(resourceAsStream, Charset.defaultCharset());
            html.text(style);
        }

        html.end(2); // style head
    }

    protected void addCapabilitiesTable(CapabilitiesContainer container) {
        if (container.getCapabilities().isEmpty()) {
            return;
        }

        String tableId = container.getId() + "_capabilities";
        html.h3().id(tableId).text(localize("headers.capabilities")).end();

        html
                .table()
                .thead();

        addTableRow();

        addTableHeader("feature.name");
        addTableHeader("feature.type");
        addTableHeader("feature.derivedFrom");

        html
                .end() // tr
                .end() // thead
                .tbody();

        // Creating a list of capabilities from all parents
        List<Feature<Capability>> capabilities = new ArrayList<>();
        CapabilitiesContainer current = container;
        do {
            for (Capability capability : current.getCapabilities()) {
                capabilities.add(new Feature<>(
                        capability,
                        current == container ? null : current.getId()
                ));
            }
            current = spec.getParent(current);
        } while (current != null);
        Collections.sort(capabilities);

        for (Feature<Capability> feature : capabilities) {

            Capability capability = feature.object;
            addTableRow(tableId + "_" + capability.getName());

            addTableData(capability.getName());
            addTableData(capability.getType());
            addTableData(feature.derivedFrom);

            html.end(); // tr
        }

        html
                .end() // tbody
                .end(); // table
    }

    protected void addPropertiesTable(PropertiesContainer container) {

        // Creating a list of properties from all parents
        List<Feature<Property>> properties = new ArrayList<>();
        PropertiesContainer current = container;
        do {
            for (Property property : current.getProperties()) {
                properties.add(new Feature<>(
                        property,
                        current == container ? null : current.getId()
                ));
            }
            current = spec.getParent(current);
        } while (current != null);

        if (properties.isEmpty()) {
            return;
        }

        String tableId = container.getId() + "_properties";
        html.h3().id(tableId).text(localize("headers.properties")).end();

        html
                .table()
                .thead();

        addTableRow();

        addTableHeader("feature.name");
        addTableHeader("feature.required");
        addTableHeader("feature.type");
        addTableHeader("feature.status");
        addTableHeader("feature.default");
        addTableHeader("feature.description");
        addTableHeader("feature.constraints");
        addTableHeader("feature.derivedFrom");

        html
                .end()
                .end()
                .tbody();

        Collections.sort(properties);
        for (Feature<Property> feature : properties) {

            Property property = feature.object;
            addTableRow(tableId + "_" + property.getName());

            addTableData(property.getName());
            addTableData(property.isRequired());
            addTableData(property.getType());
            addTableData(property.getStatus());
            addTableData(property.getDefaultValue());
            addTableData(property.getDescription());

            html.td().classAttr("table.data");
            if (!property.getConstraints().isEmpty()) {
                html.ul();
                for (Constraint constraint : property.getConstraints()) {
                    html.li();

                    if (constraint.getValue() instanceof Iterable) {
                        html.text(constraint.getOperator());
                        html.ul();
                        for (Object value : (Iterable) constraint.getValue()) {
                            html.li().text(String.valueOf(value)).end();
                        }
                        html.end(); // ul
                    } else {
                        html.text(constraint.getOperator() + " " + constraint.getValue());
                    }

                    html.end(); // li
                }
                html.end(); // ul
            }
            html.end(); // td

            addTableData(feature.derivedFrom);

            html.end(); // tr
        }

        html
                .end() // tbody
                .end(); // table
    }

    protected <T extends AbstractModelEntity> void addHierarchyTree(T modelEntity) {

        html.div().classAttr("derived_from_hierarchy_tree");

        LinkedList<T> parents = new LinkedList<>();
        T parent = spec.getParent(modelEntity);

        if (parent != null) {
            while (parent != null) {
                parents.add(parent);
                parent = spec.getParent(parent);
            }

            Iterator<T> descendingIterator = parents.descendingIterator();
            while (descendingIterator.hasNext()) {
                parent = descendingIterator.next();
                html.ul();
                html.li();
                html.a()
                        .href("#" + getIndexId(parent))
                        .title(parent.getDescription())
                        .text(parent.getId())
                        .end(); // a
            }
            html.end(); // li

            html.li();
            html.ul();
            html.li();
            html.text(modelEntity.getId());
            html.end(3); // li ul li

            html.end(parents.size() * 2 - 1); //  li ul

            html.br().end();
        }

        html.end(); // div
    }

    protected void addIdentifiersTable(AbstractModelEntity entity) {

        if (entity.getShorthandName() != null || entity.getTypeQualifiedName() != null) {
            html
                    .table()
                    .tbody();

            addIdentifierRow("identifiers.shorthand_name", entity.getShorthandName());
            addIdentifierRow("identifiers.type_qualified_name", entity.getTypeQualifiedName());
            addIdentifierRow("identifiers.type_uri", entity.getTypeUri());

            html.end(2);
        } // else only TypeUri is available so no need to generate the table as it is already displayed in the header
    }

    protected void addIdentifierRow(String messageKey, String identifier) {
        if (identifier != null) {
            html.tr()
                    .td()
                    .classAttr("table_data table_data_header")
                    .text(localize(messageKey))
                    .end()
                    .td()
                    .text(identifier)
                    .end()
                    .end();
        }
    }

    protected String getIndexId(AbstractModel abstractModel) {
        return abstractModel.getId();
    }

    protected void addAttributesTable(AttributesContainer container) {

        // Creating a list of attributes from all parents
        List<Feature<Attribute>> attributes = new ArrayList<>();
        AttributesContainer current = container;
        do {
            for (Attribute attribute : current.getAttributes()) {
                attributes.add(new Feature<>(
                        attribute,
                        current == container ? null : current.getId()
                ));
            }
            current = spec.getParent(current);
        } while (current != null);

        if (attributes.isEmpty()) {
            return;
        }

        String tableId = container.getId() + "_attributes";
        html.h3().id(tableId).text(localize("headers.attributes")).end();

        html
                .table()
                .thead();

        addTableRow();

        addTableHeader("feature.name");
        addTableHeader("feature.required");
        addTableHeader("feature.type");
        addTableHeader("feature.default");
        addTableHeader("feature.description");
        addTableHeader("feature.derivedFrom");

        html
                .end() // tr
                .end() // thead
                .tbody();

        Collections.sort(attributes);
        for (Feature<Attribute> feature : attributes) {

            Attribute attribute = feature.object;
            addTableRow(tableId + "_" + attribute.getName());

            addTableData(attribute.getName());
            addTableData(attribute.isRequired());
            addTableData(attribute.getType());
            addTableData(attribute.getDefaultValue());
            addTableData(attribute.getDescription());
            addTableData(feature.derivedFrom);

            html.end(); // tr
        }

        html
                .end() // tbody
                .end(); // table
    }

    protected void addTableRow() {
        addTableRow(null);
    }

    protected void addTableRow(@Nullable String rowId) {
        html
                .tr()
                .classAttr("table_row")
                .id(rowId);
    }

    protected void addTableData(Object value) {
        html.td();

        String classes = "table_data";
        if (value == null) {
            classes += " null_value";
        }

        html.classAttr(classes);

        if (value == null) {
            html.title("null");
        } else {
            html.text(String.valueOf(value));
        }
        html.end(); // td
    }

    protected void addTableHeader(String messageKey) {
        html
                .th()
                .classAttr("table_header")
                .text(localize(messageKey))
                .end();
    }

    protected class Feature<T extends Comparable<T>> implements Comparable<Feature<T>> {
        private T object;
        private String derivedFrom;

        public Feature(T object, String derivedFrom) {
            this.object = object;
            this.derivedFrom = derivedFrom;
        }

        @Override
        public int compareTo(Feature<T> o) {
            return object.compareTo(o.object);
        }
    }

    public String localize(String messageKey) {
        return localize(messageKey, null);
    }

    public String localize(String messageKey, String defaultMessage) {
        return getMessage(messageKey, defaultMessage);
    }

    protected String getMessage(String messageKey, String defaultMessage) {
        String message = messages.getMessage(messageKey);
        if (message == null) {
            message = defaultMessage;
        }
        return message;
    }
}
