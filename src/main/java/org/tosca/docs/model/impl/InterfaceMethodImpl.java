/*
 * (c) 2016 Nokia Proprietary - Nokia Internal Use
 */

package org.tosca.docs.model.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.tosca.docs.model.Interface;
import org.tosca.docs.model.InterfaceMethod;

import javax.annotation.Nullable;
import java.util.*;

/**
 * Provides the TOSCA parser a way to create a {@link InterfaceMethod}
 */
public class InterfaceMethodImpl implements InterfaceMethod {


    private String description;
    private String implementation;
    private String name;





    public InterfaceMethod setDescription(String description) {
        this.description = description;
        return this;
    }

    public InterfaceMethod setImplementation(String implementation) {
        this.implementation = implementation;
        return this;
    }




    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getImplementation() {
        return implementation;
    }



    @Override
    public InterfaceMethod setName(String name) {
        this.name=name;
        return this;
    }

    @Override
    public int compareTo(InterfaceMethod interfaceMethod) {
        return getName().compareTo(interfaceMethod.getName());
    }
}
