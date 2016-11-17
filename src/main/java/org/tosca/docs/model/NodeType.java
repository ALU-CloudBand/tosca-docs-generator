package org.tosca.docs.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.tosca.docs.model.impl.NodeTypeImpl;

import java.util.List;

@JsonDeserialize(as = NodeTypeImpl.class)
public interface NodeType<T extends AbstractModelEntity> extends AbstractModelEntity<T>, CapabilitiesContainer {

    NodeType setAttributes(List<? extends Attribute> attributes);

    NodeType setCapabilities(List<Capability> capabilities);
}
