package org.tosca.docs.model.impl;

import org.tosca.docs.model.Capability;


public class CapabilityImpl implements Capability {
    private String name;
    private String type;

    public CapabilityImpl() {
    }

    public CapabilityImpl(String name, String type) {
        this.name = name;
        this.type = type;
    }

    /**
     * @return {@link #name}
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * @param name {@link #name}
     * @return <source>this</source>
     */
    @Override
    public CapabilityImpl setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * @return {@link #type}
     */
    @Override
    public String getType() {
        return type;
    }

    /**
     * @param type {@link #type}
     * @return <source>this</source>
     */
    @Override
    public CapabilityImpl setType(String type) {
        this.type = type;
        return this;
    }

    @Override
    public int compareTo(Capability o) {
        return getName().compareTo(o.getName());
    }
}
