package org.tosca.docs.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.tosca.docs.model.impl.ToscaSpecImpl;

import java.util.Set;

@JsonDeserialize(as = ToscaSpecImpl.class)
public interface ToscaSpec {

    String getTitle();

    ToscaSpecImpl setTitle(String title);

    Set<NodeType> getNodeTypes();

    ToscaSpec setNodeTypes(Set<NodeType> nodeTypes);

    Set<RelationshipType> getRelationshipTypes();

    ToscaSpec setRelationshipTypes(Set<RelationshipType> nodeTypes);

    Set<CapabilityType> getCapabilityTypes();

    ToscaSpec setCapabilityTypes(Set<CapabilityType> capabilityTypes);

    <T> T getParent(T child);

    <ET> ET getTypeByClassAndName(Class<ET> typeClass, String typeName);
}
