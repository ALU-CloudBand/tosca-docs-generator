package org.tosca.docs.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.tosca.docs.model.impl.PropertyImpl;

import java.util.List;

@JsonDeserialize(as = PropertyImpl.class)
public interface Property extends AbstractModel<Property>, Comparable<Property> {
    String getName();

    Property setName(String name);

    boolean isRequired();

    Property setRequired(boolean required);

    String getType();

    Property setType(String type);

    Object getDefaultValue();

    Property setDefaultValue(Object defaultValue);

    String getDescription();

    Property setDescription(String description);

    String getStatus();

    Property setStatus(String status);

    List<? extends Constraint> getConstraints();

    Property setConstraints(List<? extends Constraint> constraints);
    // TODO entry_schema

}
