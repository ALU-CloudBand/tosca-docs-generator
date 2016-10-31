package org.tosca.docs.model.impl;

import org.tosca.docs.model.AbstractModel;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractModelImpl<T extends AbstractModel> implements AbstractModel<T> {
    private Map<String, Object> extensions = new HashMap<>();

    /**
     * @return {@link #extensions}
     */
    @Override
    public Map<String, Object> getExtensions() {
        return extensions;
    }

    /**
     * @param extensions {@link #extensions}
     */
    @Override
    @SuppressWarnings("unchecked")
    public T setExtensions(Map<String, Object> extensions) {
        this.extensions = extensions;
        return (T) this;
    }
}
