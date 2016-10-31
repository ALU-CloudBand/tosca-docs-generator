package org.tosca.docs.model.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.tosca.docs.model.Attribute;

public class AttributeImpl extends AbstractModelImpl<Attribute> implements Attribute {
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
    public Attribute setName(String name) {
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
    public Attribute setRequired(boolean required) {
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
    public Attribute setType(String type) {
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
    public Attribute setDefaultValue(Object defaultValue) {
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
    public Attribute setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public String getId() {
        return getName();
    }

    @Override
    public int compareTo(Attribute o) {
        return getName().compareTo(o.getName());
    }
}
