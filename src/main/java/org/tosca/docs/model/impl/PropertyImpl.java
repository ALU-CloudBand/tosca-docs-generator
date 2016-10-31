package org.tosca.docs.model.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.tosca.docs.model.Constraint;
import org.tosca.docs.model.Property;
import org.tosca.docs.model.Status;

import java.util.ArrayList;
import java.util.List;

public class PropertyImpl extends AbstractModelImpl<Property> implements Property {
    private String name;
    private boolean required;
    /**
     * Officially supported types are:
     * <ul>
     * <li>string - tag:yaml.org,2002:str (default)</li>
     * <li>integer - tag:yaml.org,2002:int</li>
     * <li>float -  tag:yaml.org,2002:float</li>
     * <li>boolean - tag:yaml.org,2002:bool (i.e., a value either ‘true’ or ‘false’)</li>
     * <li>timestamp - tag:yaml.org,2002:timestamp  [YAML-TS-1.1]</li>
     * </ul>
     */
    private String type;
    @JsonProperty("default")
    private Object defaultValue;
    private String description;
    private List<? extends Constraint> constraints = new ArrayList<>(0);
    /**
     * Officially supported statuses are:
     * <ul>
     * <li>supported - Indicates the property is supported.  This is the default value for all property definitions.</li>
     * <li>unsupported - Indicates the property is not supported.</li>
     * <li>experimental - Indicates the property is experimental and has no official standing.</li>
     * <li>deprecated - Indicates the property has been deprecated by a new specification version.</li>
     * </ul>
     */
    private String status = Status.SUPPORTED.toString();

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
    public PropertyImpl setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * @return {@link #required}
     */
    @Override
    public boolean isRequired() {
        return required;
    }

    /**
     * @param required {@link #required}
     * @return <source>this</source>
     */
    @Override
    public PropertyImpl setRequired(boolean required) {
        this.required = required;
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
    public PropertyImpl setType(String type) {
        this.type = type;
        return this;
    }

    /**
     * @return {@link #defaultValue}
     */
    @Override
    public Object getDefaultValue() {
        return defaultValue;
    }

    /**
     * @param defaultValue {@link #defaultValue}
     * @return <source>this</source>
     */
    @Override
    public PropertyImpl setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    /**
     * @return {@link #description}
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * @param description {@link #description}
     * @return <source>this</source>
     */
    @Override
    public PropertyImpl setDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * @return {@link #status}
     */
    @Override
    public String getStatus() {
        return status;
    }

    /**
     * @param status {@link #status}
     * @return <source>this</source>
     */
    @Override
    public PropertyImpl setStatus(String status) {
        this.status = status;
        return this;
    }

    /**
     * @return {@link #constraints}
     */
    @Override
    public List<? extends Constraint> getConstraints() {
        return constraints;
    }

    /**
     * @param constraints {@link #constraints}
     * @return <source>this</source>
     */
    @Override
    public PropertyImpl setConstraints(List<? extends Constraint> constraints) {
        this.constraints = constraints;
        return this;
    }

    @Override
    public String getId() {
        return getName();
    }

    @Override
    public int compareTo(Property o) {
        return getName().compareTo(o.getName());
    }
}
