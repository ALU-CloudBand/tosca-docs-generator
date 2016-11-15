package org.tosca.docs.model.impl;

import org.tosca.docs.model.CapabilityType;

public class CapabilityTypeImpl extends AbstractModelEntityImpl<CapabilityTypeImpl> implements CapabilityType<CapabilityTypeImpl>, Comparable<CapabilityTypeImpl> {
    @Override
    public String getId() {
        return getTypeUri();
    }

    @Override
    public int compareTo(CapabilityTypeImpl o) {
        return getId().compareTo(o.getId());
    }
}
