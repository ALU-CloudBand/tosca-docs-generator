package org.tosca.docs.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.tosca.docs.model.impl.CapabilityTypeImpl;

@JsonDeserialize(as = CapabilityTypeImpl.class)
public interface CapabilityType<T extends AbstractModelEntity> extends AbstractModelEntity<T> {
}