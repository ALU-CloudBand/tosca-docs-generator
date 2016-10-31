package org.tosca.docs.model.impl;

import org.tosca.docs.model.AbstractModelEntity;

public abstract class AbstractModelEntityImpl<T extends AbstractModelEntityImpl> extends AbstractModelImpl<T> implements AbstractModelEntity<T> {
    @Override
    public String getDerivedFrom() {
        return null;
    }
}
