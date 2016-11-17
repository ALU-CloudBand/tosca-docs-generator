package org.tosca.docs.model.impl;

import org.tosca.docs.model.Capability;
import org.tosca.docs.model.NodeType;

import java.util.ArrayList;
import java.util.List;

public class NodeTypeImpl extends AbstractModelEntityImpl<NodeTypeImpl> implements NodeType<NodeTypeImpl>, Comparable<NodeType> {

    private List<Capability> capabilities = new ArrayList<>(0);

    public static String getId(NodeType nodeType) {
        return nodeType.getTypeUri();
    }

    @Override
    public String getId() {
        return NodeTypeImpl.getId(this);
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
    public int compareTo(NodeType o) {
        return getId().compareTo(o.getId());
    }
}
