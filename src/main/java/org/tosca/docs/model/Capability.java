package org.tosca.docs.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.tosca.docs.model.impl.CapabilityImpl;

@JsonDeserialize(as = CapabilityImpl.class)
public interface Capability extends Comparable<Capability> {
    String getName();

    Capability setName(String name);

    String getType();

    Capability setType(String type);
}
