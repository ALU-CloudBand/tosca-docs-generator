package org.tosca.docs.model;

import java.util.List;

/**
 * Created by prabvenu on 3/31/17.
 */
public interface InterfacesContainer {
    String getId();

    List<? extends Interface> getInterfaces();
}
