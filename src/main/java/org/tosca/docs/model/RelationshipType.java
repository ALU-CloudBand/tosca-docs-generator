package org.tosca.docs.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.tosca.docs.model.impl.RelationshipTypeImpl;

import java.util.List;

@JsonDeserialize(as = RelationshipTypeImpl.class)
public interface RelationshipType<T extends AbstractModel> extends AbstractModelEntity<T>, AttributesContainer, PropertiesContainer {

    @Override
    List<? extends Property> getProperties();

    RelationshipType setProperties(List<? extends Property> properties);

    @Override
    List<? extends Attribute> getAttributes();

    RelationshipType setAttributes(List<? extends Attribute> attributes);

    @Override
    String getDerivedFrom();

    RelationshipType setDerivedFrom(String derivedFrom);

}
