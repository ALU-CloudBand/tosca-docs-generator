package org.tosca.docs.model;

public enum Status {
    SUPPORTED,
    UNSUPPORTED,
    EXPERIMENTAL,
    DEPRECATED;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
