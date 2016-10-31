package org.tosca.docs.model;

public interface AbstractModelEntity<T extends AbstractModel> extends AbstractModel<T> {

    String getDerivedFrom();

    String getDescription();
}
