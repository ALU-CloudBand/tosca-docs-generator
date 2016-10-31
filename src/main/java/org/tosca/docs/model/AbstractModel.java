package org.tosca.docs.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Map;

public interface AbstractModel<T extends AbstractModel> {

    @JsonIgnore
    String getId();

    Map<String, Object> getExtensions();

    T setExtensions(Map<String, Object> extensions);
}
