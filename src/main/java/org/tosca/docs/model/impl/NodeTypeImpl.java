package org.tosca.docs.model.impl;

import org.tosca.docs.model.Attribute;
import org.tosca.docs.model.Capability;
import org.tosca.docs.model.NodeType;

import java.util.ArrayList;
import java.util.List;

public class NodeTypeImpl extends AbstractModelEntityImpl<NodeTypeImpl> implements NodeType<NodeTypeImpl>, Comparable<NodeTypeImpl> {

    private List<? extends Attribute> attributes = new ArrayList<>(0);
    private List<Capability> capabilities = new ArrayList<>(0);

    public static String getId(NodeType nodeType) {
        return nodeType.getTypeUri();
    }

    @Override
    public String getId() {
        return NodeTypeImpl.getId(this);
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
     * @return {@link #capabilities}
     */
    @Override
    public List<Capability> getCapabilities() {
        return capabilities;
    }

    /**
     * @param capabilities {@link #capabilities}
     * @return <source>this</source>
     */
    @Override
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
