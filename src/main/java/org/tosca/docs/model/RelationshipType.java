package org.tosca.docs.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.tosca.docs.model.impl.RelationshipTypeImpl;

import java.util.List;

@JsonDeserialize(as = RelationshipTypeImpl.class)
public interface RelationshipType<T extends AbstractModelEntity> extends AbstractModelEntity<T>, AttributesContainer {

    @Override
    List<? extends Property> getProperties();

    @Override
    List<? extends Attribute> getAttributes();

    RelationshipType setAttributes(List<? extends Attribute> attributes);

}
