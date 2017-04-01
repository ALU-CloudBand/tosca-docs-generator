package org.tosca.docs.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.tosca.docs.model.impl.InterfaceImpl;
import org.tosca.docs.model.impl.NodeTypeImpl;

import java.util.List;
import java.util.Map;

/**
 * Created by prabvenu on 3/31/17.
 */
@JsonDeserialize(as = InterfaceImpl.class)
public interface Interface extends Comparable<Interface> {


     String getName();
    Interface setName(String name);
    Map<String,InterfaceMethod> getMethods();
    Interface setMethods(Map<String,InterfaceMethod> methods);




}

