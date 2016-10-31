package org.tosca.docs.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.tosca.docs.model.impl.AttributeImpl;

@JsonDeserialize(as = AttributeImpl.class)
public interface Attribute extends AbstractModel<Attribute>, Comparable<Attribute> {
    String getName();

    Attribute setName(String name);

    boolean isRequired();

    Attribute setRequired(boolean required);

    String getType();

    Attribute setType(String type);

    Object getDefaultValue();

    Attribute setDefaultValue(Object defaultValue);

    String getDescription();

    Attribute setDescription(String description);

}
