package org.tosca.docs.model.impl;

import org.tosca.docs.model.AbstractModelEntity;

public abstract class AbstractModelEntityImpl<T extends AbstractModelEntityImpl> extends AbstractModelImpl<T> implements AbstractModelEntity<T> {

    private String shorthandName;
    private String typeQualifiedName;
    private String typeUri;
    private String description;

    /**
     * @return {@link #shorthandName}
     */
    @Override
    public String getShorthandName() {
        return shorthandName;
    }

    /**
     * @param shorthandName {@link #shorthandName}
     * @return <source>this</source>
     */
    @Override
    public T setShorthandName(String shorthandName) {
        this.shorthandName = shorthandName;
        return (T) this;
    }

    /**
     * @return {@link #typeQualifiedName}
     */
    @Override
    public String getTypeQualifiedName() {
        return typeQualifiedName;
    }

    /**
     * @param typeQualifiedName {@link #typeQualifiedName}
     * @return <source>this</source>
     */
    @Override
    public T setTypeQualifiedName(String typeQualifiedName) {
        this.typeQualifiedName = typeQualifiedName;
        return (T) this;
    }

    /**
     * @return {@link #typeUri}
     */
    @Override
    public String getTypeUri() {
        return typeUri;
    }

    /**
     * @param typeUri {@link #typeUri}
     * @return <source>this</source>
     */
    @Override
    public T setTypeUri(String typeUri) {
        this.typeUri = typeUri;
        return (T) this;
    }

    /**
     * @return {@link #description}
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * @param description {@link #description}
     * @return <source>this</source>
     */
    @Override
    public T setDescription(String description) {
        this.description = description;
        return (T) this;
    }

    @Override
    public String getDerivedFrom() {
        return null;
    }
}
