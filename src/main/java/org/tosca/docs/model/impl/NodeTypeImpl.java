package org.tosca.docs.model.impl;

import org.tosca.docs.model.Attribute;
import org.tosca.docs.model.Capability;
import org.tosca.docs.model.NodeType;
import org.tosca.docs.model.Property;

import java.util.ArrayList;
import java.util.List;

public class NodeTypeImpl extends AbstractModelEntityImpl<NodeTypeImpl> implements NodeType<NodeTypeImpl>, Comparable<NodeTypeImpl> {

    private List<? extends Property> properties = new ArrayList<>(0);
    private List<? extends Attribute> attributes = new ArrayList<>(0);
    private String derivedFrom;
    private List<Capability> capabilities = new ArrayList<>(0);

    public static String getId(NodeType nodeType) {
        return nodeType.getTypeUri();
    }

    @Override
    public String getId() {
        return NodeTypeImpl.getId(this);
    }

    /**
     * @return {@link #properties}
     */
    @Override
    public List<? extends Property> getProperties() {
        return properties;
    }

    /**
     * @param properties {@link #properties}
     * @return <source>this</source>
     */
    @Override
    public NodeTypeImpl setProperties(List<? extends Property> properties) {
        this.properties = properties;
        return this;
    }

    /**
     * @return {@link #attributes}
     */
    @Override
    public List<? extends Attribute> getAttributes() {
        return attributes;
    }

    /**
     * @param attributes {@link #attributes}
     * @return <source>this</source>
     */
    @Override
    public NodeType setAttributes(List<? extends Attribute> attributes) {
        this.attributes = attributes;
        return this;
    }

    /**
     * @return {@link #derivedFrom}
     */
    @Override
    public String getDerivedFrom() {
        return derivedFrom;
    }

    /**
     * @param derivedFrom {@link #derivedFrom}
     * @return <source>this</source>
     */
    @Override
    public NodeType setDerivedFrom(String derivedFrom) {
        this.derivedFrom = derivedFrom;
        return this;
    }

    /**
     * @return {@link #capabilities}
     */
    public List<Capability> getCapabilities() {
        return capabilities;
    }

    /**
     * @param capabilities {@link #capabilities}
     * @return <source>this</source>
     */
    public NodeType setCapabilities(List<Capability> capabilities) {
        this.capabilities = capabilities;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NodeTypeImpl nodeType = (NodeTypeImpl) o;

        return getId() != null ? getId().equals(nodeType.getId()) : nodeType.getId() == null;

    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }

    @Override
    public int compareTo(NodeTypeImpl o) {
        return getId().compareTo(o.getId());
    }
}
