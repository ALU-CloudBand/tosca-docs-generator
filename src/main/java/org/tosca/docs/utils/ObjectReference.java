package org.tosca.docs.utils;

/**
 * A simple wrapper class that holds a value.
 * This class is useful when null is a valid value
 * and you need to distinguish between a non-existing value and an existing value,
 *
 * @param <T> The type of the value
 */
public class ObjectReference<T> {

    private T value;

    public ObjectReference(T value) {
        this.value = value;
    }

    /**
     * @return {@link #value}
     */
    public T getValue() {
        return value;
    }

    /**
     * @param value {@link #value}
     */
    public ObjectReference setValue(T value) {
        this.value = value;
        return this;
    }

    @Override
    public String toString() {
        return "ObjectReference{" +
                "value=" + value +
                '}';
    }
}
