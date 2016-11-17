package org.tosca.docs.model.impl;

import org.tosca.docs.model.CapabilityType;

import java.util.ArrayList;
import java.util.List;

public class CapabilityTypeImpl extends AbstractModelEntityImpl<CapabilityTypeImpl> implements CapabilityType<CapabilityTypeImpl>, Comparable<CapabilityType> {

    private List<String> validSourceTypes = new ArrayList<>(0);

    @Override
    public String getId() {
        return getTypeUri();
    }

    @Override
    public int compareTo(CapabilityType o) {
        return getId().compareTo(o.getId());
    }

    /**
     * @return {@link #validSourceTypes}
     */
    public List<String> getValidSourceTypes() {
        return validSourceTypes;
    }

    /**
     * @param validSourceTypes {@link #validSourceTypes}
     */
    public CapabilityTypeImpl setValidSourceTypes(List<String> validSourceTypes) {
        this.validSourceTypes = validSourceTypes;
        return this;
    }


}
