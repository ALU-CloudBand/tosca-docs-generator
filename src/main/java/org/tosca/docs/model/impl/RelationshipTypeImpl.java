package org.tosca.docs.model.impl;

import org.tosca.docs.model.Attribute;
import org.tosca.docs.model.Property;
import org.tosca.docs.model.RelationshipType;

import java.util.ArrayList;
import java.util.List;

public class RelationshipTypeImpl extends AbstractModelEntityImpl<RelationshipTypeImpl> implements RelationshipType<RelationshipTypeImpl>, Comparable<RelationshipTypeImpl> {

    private List<? extends Property> properties = new ArrayList<>(0);
    private List<? extends Attribute> attributes = new ArrayList<>(0);
    private String derivedFrom;

    public static String getId(RelationshipType relationshipType) {
        return relationshipType.getTypeUri();
    }

    @Override
    public String getId() {
        return RelationshipTypeImpl.getId(this);
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
    public RelationshipTypeImpl setProperties(List<? extends Property> properties) {
        this.properties = properties;
        return this;
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
    public RelationshipType setAttributes(List<? extends Attribute> attributes) {
        this.attributes = attributes;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RelationshipTypeImpl relationshipType = (RelationshipTypeImpl) o;

        return getId() != null ? getId().equals(relationshipType.getId()) : relationshipType.getId() == null;

    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }

    @Override
    public int compareTo(RelationshipTypeImpl o) {
        return getId().compareTo(o.getId());
    }
}
