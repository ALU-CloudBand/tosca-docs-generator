package org.tosca.docs.model;

public interface AbstractModelEntity<T extends AbstractModel> extends AbstractModel<T> {

    String getShorthandName();

    AbstractModelEntity setShorthandName(String shorthandName);

    String getTypeQualifiedName();

    AbstractModelEntity setTypeQualifiedName(String typeQualifiedName);

    String getTypeUri();

    AbstractModelEntity setTypeUri(String typeUri);

    String getDerivedFrom();

    String getDescription();

    AbstractModelEntity setDescription(String description);

}
