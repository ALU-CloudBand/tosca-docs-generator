package org.tosca.docs.model;

import java.util.List;

public interface PropertiesContainer {

    String getId();

    List<? extends Property> getProperties();
}
