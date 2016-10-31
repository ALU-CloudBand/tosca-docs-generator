package org.tosca.docs.model;

public enum Type {
    STRING,
    INTEGER,
    FLOAT,
    BOOLEAN,
    TIMESTAMP,
    MAP;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
