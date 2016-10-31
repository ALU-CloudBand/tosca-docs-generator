package org.tosca.docs.model.impl;

import org.tosca.docs.model.Constraint;

public class ConstraintImpl implements Constraint {

    private Object value;
    private String operator;

    public ConstraintImpl() {
    }

    public ConstraintImpl(String operator, Object value) {
        this.value = value;
        this.operator = operator;
    }

    /**
     * @return {@link #value}
     */
    @Override
    public Object getValue() {
        return value;
    }

    /**
     * @param value {@link #value}
     * @return <source>this</source>
     */
    @Override
    public ConstraintImpl setValue(Object value) {
        this.value = value;
        return this;
    }

    /**
     * @return {@link #operator}
     */
    @Override
    public String getOperator() {
        return operator;
    }

    /**
     * @param operator {@link #operator}
     * @return <source>this</source>
     */
    @Override
    public ConstraintImpl setOperator(String operator) {
        this.operator = operator;
        return this;
    }

}
