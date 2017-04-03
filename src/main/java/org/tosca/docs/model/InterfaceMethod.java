package org.tosca.docs.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.tosca.docs.model.impl.InterfaceMethodImpl;

/**
 * Created by prabvenu on 3/31/17.
 */
@JsonDeserialize(as = InterfaceMethodImpl.class)
public interface InterfaceMethod extends  Comparable<InterfaceMethod>{


    String getName();

    InterfaceMethod  setName(String name);

    String getDescription();

    InterfaceMethod  setDescription(String description);

    String getImplementation();

    InterfaceMethod  setImplementation(String implementation);






}
