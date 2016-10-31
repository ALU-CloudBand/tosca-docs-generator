package org.tosca.docs.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.tosca.docs.model.impl.ConstraintImpl;

@JsonDeserialize(as = ConstraintImpl.class)
public interface Constraint {

    Object getValue();

    Constraint setValue(Object value);

    String getOperator();

    Constraint setOperator(String operator);

    enum Operator {
        EQUAL,
        GREATER_THAN,
        GREATER_OR_EQUAL,
        LESS_THAN,
        LESS_OR_EQUAL,
        IN_RANGE,
        VALID_VALUES,
        LENGTH,
        MIN_LENGTH,
        MAX_LENGTH,
        PATTERN;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }
}


