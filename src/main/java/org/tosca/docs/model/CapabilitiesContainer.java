package org.tosca.docs.model;

import java.util.List;

public interface CapabilitiesContainer {

    String getId();

    List<? extends Capability> getCapabilities();
}
