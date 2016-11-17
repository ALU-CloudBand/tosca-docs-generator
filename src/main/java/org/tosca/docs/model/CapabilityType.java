package org.tosca.docs.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.tosca.docs.model.impl.CapabilityTypeImpl;

import java.util.List;

@JsonDeserialize(as = CapabilityTypeImpl.class)
public interface CapabilityType<T extends AbstractModelEntity> extends AbstractModelEntity<T> {

    List<String> getValidSourceTypes();

    CapabilityTypeImpl setValidSourceTypes(List<String> validSourceTypes);
}