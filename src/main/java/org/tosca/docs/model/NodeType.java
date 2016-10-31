package org.tosca.docs.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.tosca.docs.model.impl.NodeTypeImpl;

import java.util.List;

@JsonDeserialize(as = NodeTypeImpl.class)
public interface NodeType<T extends AbstractModel> extends AbstractModelEntity<T>, AttributesContainer, PropertiesContainer, CapabilitiesContainer {
    String getShorthandName();

    NodeType setShorthandName(String shorthandName);

    String getTypeQualifiedName();

    NodeType setTypeQualifiedName(String typeQualifiedName);

    String getTypeUri();

    NodeType setTypeUri(String typeUri);

    @Override
    String getDescription();

    NodeType setDescription(String description);

    @Override
    List<? extends Property> getProperties();

    NodeType setProperties(List<? extends Property> properties);

    @Override
    List<? extends Attribute> getAttributes();

    NodeType setAttributes(List<? extends Attribute> attributes);

    @Override
    String getDerivedFrom();

    NodeType setDerivedFrom(String derivedFrom);

    List<Capability> getCapabilities();

    NodeType setCapabilities(List<Capability> capabilities);
}
