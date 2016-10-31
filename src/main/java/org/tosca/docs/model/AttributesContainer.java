package org.tosca.docs.model;

import java.util.List;

public interface AttributesContainer {

    String getId();

    List<? extends Attribute> getAttributes();
}
