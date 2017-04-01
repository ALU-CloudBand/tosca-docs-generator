/*
 * (c) 2016 Nokia Proprietary - Nokia Internal Use
 */

package org.tosca.docs.model.impl;

/**
 * Created by prabvenu on .
 */

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableMap;

import org.tosca.docs.model.Interface;
import org.tosca.docs.model.InterfaceMethod;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Provides the TOSCA parser a way to create a {@link Interface}
 */
public class InterfaceImpl implements Interface {


    private String name;

    private Map<String,InterfaceMethod> methods= new HashMap<String,InterfaceMethod>(0);

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Interface setName(String name) {
        this.name=name;
        return this;
    }

    @Override
    public Map<String,InterfaceMethod> getMethods() {
       return methods;

    }

    @Override
    public Interface setMethods(Map<String,InterfaceMethod> methods) {
        this.methods=methods;
        return this;
    }


    @Override
    public int compareTo(Interface anInterface) {
        return getName().compareTo(anInterface.getName());
    }
}
