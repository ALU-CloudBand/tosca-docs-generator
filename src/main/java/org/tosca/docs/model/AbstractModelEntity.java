package org.tosca.docs.model;

import java.util.List;

public interface AbstractModelEntity<T extends AbstractModelEntity> extends AbstractModel<T>, PropertiesContainer, AttributesContainer,InterfacesContainer {

    String getShorthandName();

    AbstractModelEntity setShorthandName(String shorthandName);

    String getTypeQualifiedName();

    AbstractModelEntity setTypeQualifiedName(String typeQualifiedName);

    String getTypeUri();

    AbstractModelEntity setTypeUri(String typeUri);

    String getDerivedFrom();

    AbstractModelEntity setDerivedFrom(String derivedFrom);

    String getDescription();

    AbstractModelEntity setDescription(String description);

    AbstractModelEntity setProperties(List<? extends Property> properties);

    AbstractModelEntity setAttributes(List<? extends Attribute> attributes);

    AbstractModelEntity setInterfaces(List<? extends Interface> interfaces);
}
