package org.tosca.docs.model.impl;

import org.tosca.docs.model.AbstractModelEntity;
import org.tosca.docs.model.Attribute;
import org.tosca.docs.model.Property;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public abstract class AbstractModelEntityImpl<T extends AbstractModelEntityImpl> extends AbstractModelImpl<T> implements AbstractModelEntity<T> {

    private String shorthandName;
    private String typeQualifiedName;
    private String typeUri;
    private String description;
    private List<? extends Property> properties = new ArrayList<>(0);
    private List<? extends Attribute> attributes = new ArrayList<>(0);
    private String derivedFrom;

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

    /**
     * @return {@link #properties}
     */
    @Override
    public List<? extends Property> getProperties() {
        return properties;
    }

    /**
     * @param properties {@link #properties}
     * @return <source>this</source>
     */
    @Override
    public T setProperties(List<? extends Property> properties) {
        this.properties = properties;
        return (T) this;
    }

    /**
     * @return {@link #attributes}
     */
    @Override
    public List<? extends Attribute> getAttributes() {
        return attributes;
    }

    /**
     * @param attributes {@link #attributes}
     * @return <source>this</source>
     */
    @Override
    public T setAttributes(List<? extends Attribute> attributes) {
        this.attributes = attributes;
        return (T) this;
    }

    /**
     * @return {@link #derivedFrom}
     */
    @Override
    public String getDerivedFrom() {
        return derivedFrom;
    }

    /**
     * @param derivedFrom {@link #derivedFrom}
     * @return <source>this</source>
     */
    @Override
    public T setDerivedFrom(String derivedFrom) {
        this.derivedFrom = derivedFrom;
        return (T) this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractModelEntityImpl modelEntity = (AbstractModelEntityImpl) o;

        return getId() != null ? getId().equals(modelEntity.getId()) : modelEntity.getId() == null;

    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}
