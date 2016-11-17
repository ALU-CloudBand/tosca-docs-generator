package org.tosca.docs.model.impl;

import org.tosca.docs.model.RelationshipType;

public class RelationshipTypeImpl extends AbstractModelEntityImpl<RelationshipTypeImpl> implements RelationshipType<RelationshipTypeImpl>, Comparable<RelationshipType> {

    public static String getId(RelationshipType relationshipType) {
        return relationshipType.getTypeUri();
    }

    @Override
    public String getId() {
        return RelationshipTypeImpl.getId(this);
    }

    @Override
    public int compareTo(RelationshipType o) {
        return getId().compareTo(o.getId());
    }
}
